package com.zis.shop.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.repository.BookInfoDao;
import com.zis.bookinfo.service.BookService;
import com.zis.bookinfo.util.BookMetadata;
import com.zis.common.capture.DefaultBookMetadataCaptureHandler;
import com.zis.common.mail.MailSenderFactory;
import com.zis.common.mail.SimpleMailSender;
import com.zis.common.util.ZisUtils;
import com.zis.shop.api.ApiTransfer;
import com.zis.shop.api.ApiTransfer.NotItem;
import com.zis.shop.api.impl.ApiTransferFactory;
import com.zis.shop.bean.DownloadItemLog;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingSystemStatus;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingUpdateStatus;
import com.zis.shop.bean.UpdateItemLog;
import com.zis.shop.bean.UpdateItemLog.UpdateItemLogStatus;
import com.zis.shop.bo.ShopAddItemsBo;
import com.zis.shop.bo.ShopDownloadBo;
import com.zis.shop.dto.ApiAddItemDto;
import com.zis.shop.dto.ApiUpdateItemDto;
import com.zis.shop.dto.ShopDownloadInterfaceDto;
import com.zis.shop.repository.DownloadItemLogDao;
import com.zis.shop.repository.ShopItemMappingDao;
import com.zis.shop.repository.UpdateItemLogDao;

@Component
public class ShopBoImpl {

	private static Logger logger = LoggerFactory.getLogger(ShopBoImpl.class);

	private final String STATUS_BOOK_ID = "bookId";
	private final String STATUS_ISBN = "isbn";

	@Autowired
	private BookInfoDao bookInfoDao;

	@Autowired
	private ShopItemMappingDao shopItemMappingDao;

	@Autowired
	private BookService bookService;

	@Autowired
	private DownloadItemLogDao downloadItemLogDao;

	@Autowired
	private DefaultBookMetadataCaptureHandler bookMetadataCapture;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ShopDownloadFactoryBo dowloadFactory;

	@Autowired
	private ShopAddItemsFactoryBo addFactory;

	@Autowired
	private ApiTransferFactory apiFactory;

	@Autowired
	private UpdateItemLogDao updateItemLogDao;

	private final String NOT_AMOUNT = "notAmount";

	private final String shaoweiEmail[] = { "to_shaowei@163.com" };

	private SimpleMailSender mailSender = MailSenderFactory.getSender();

	/**
	 * 库存变动更新或者添加网店商品
	 * 
	 * @param mapping
	 * @param shop
	 * @param amount
	 */
	public void stockChange2UpdateOrAddItems(ShopItemMapping mapping, ShopInfo shop, Integer amount) {
		// 如果是发布成功数据
		if (ShopItemMappingSystemStatus.SUCCESS.getValue().equals(mapping.getSystemStatus())) {
			updateItems(mapping, shop, amount);
			// 失败无数量的商品 进行发布处理
		} else if (ShopItemMappingSystemStatus.FAIL.getValue().equals(mapping.getSystemStatus())
				&& NOT_AMOUNT.equals(mapping.getFailReason())) {
			addItem(mapping, shop, amount);
		} else {
			// 其他状态不做处理
		}
	}

	/**
	 * 更新网店库存
	 * 
	 * @param mapping
	 * @param shop
	 * @param amount
	 */
	private void updateItems(ShopItemMapping mapping, ShopInfo shop, Integer amount) {
		String apiMsg = apiUpdateItem(mapping, shop, amount);
		saveUpdateItemLog(mapping, amount, apiMsg);
		boolean youzanDelete = NotItem.YOU_ZAN.getValue().equals(apiMsg);
		boolean taobaoDelete = NotItem.TAO_BAO.getValue().equals(apiMsg);
		if (StringUtils.isBlank(apiMsg)) {
			mapping.setUpdateStatus(ShopItemMappingUpdateStatus.SUCCESS.getValue());
			mapping.setUpdateTime(new Date());
			mapping.setUploadTime(new Date());
			this.shopItemMappingDao.save(mapping);
		} else if (youzanDelete || taobaoDelete) {
			mapping.setSystemStatus(ShopItemMappingSystemStatus.DELETE.getValue());
			mapping.setUpdateTime(new Date());
			this.shopItemMappingDao.save(mapping);
		} else {
			mapping.setUpdateStatus(ShopItemMappingUpdateStatus.FAIL.getValue());
			mapping.setUpdateTime(new Date());
			this.shopItemMappingDao.save(mapping);
		}
	}

	/**
	 * 添加更新日志
	 * 
	 * @param mapping
	 * @param amount
	 * @param msg
	 */
	private void saveUpdateItemLog(ShopItemMapping mapping, Integer amount, String msg) {
		UpdateItemLog log = new UpdateItemLog();
		log.setAmount(amount);
		log.setDescription(msg);
		log.setBookId(mapping.getBookId());
		log.setShopId(mapping.getShopId());
		log.setCreateTime(new Date());
		log.setMappingId(mapping.getId());
		log.setUpdateTime(new Date());
		if (StringUtils.isBlank(msg)) {
			log.setStatus(UpdateItemLogStatus.SUCCESS.getValue());
		} else {
			log.setStatus(UpdateItemLogStatus.FAIL.getValue());
		}
		this.updateItemLogDao.save(log);
	}

	/**
	 * api更新商品封装
	 * 
	 * @param mapping
	 * @param shop
	 * @param amount
	 * @return
	 */
	private String apiUpdateItem(ShopItemMapping mapping, ShopInfo shop, Integer amount) {
		ApiUpdateItemDto dto = new ApiUpdateItemDto();
		BeanUtils.copyProperties(mapping, dto);
		dto.setAmount(amount);
		ApiTransfer api = apiFactory.getInstance(shop.getpName());
		try {
			api.updateItem(dto, shop);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return e.getMessage();
		}
		return "";
	}

	/**
	 * 异步全部发布商品
	 * 
	 * @param mappingIds
	 * @param shop
	 */
	public void abstractAddAllProcessingItems(final ShopInfo shop) {
		Thread task = new Thread(new Runnable() {
			public void run() {
				addAllProcessingItems(shop);
			}
		});
		taskExecutor.execute(task);
	}

	/**
	 * 批量发布商品
	 * 
	 * @param mappingIds
	 * @param shop
	 */
	public void addProcessingItems(List<Integer> mappingIds, ShopInfo shop) {
		List<ShopItemMapping> list = this.shopItemMappingDao.findByShopIdAndSystemStatusAndIdIn(shop.getShopId(),
				ShopItemMappingSystemStatus.PROCESSING.getValue(), mappingIds);
		List<ApiAddItemDto> apiList = queryApiAddItemDtos(list, shop);
		ShopAddItemsBo bo = addFactory.getInstance(shop.getpName());
		bo.addItems2Shop(apiList, shop);
	}

	/**
	 * 发布单个商品
	 * 
	 * @param mapping
	 * @param shop
	 */
	private void addItem(ShopItemMapping mapping, ShopInfo shop, Integer amount) {
		List<ApiAddItemDto> apiList = new ArrayList<ApiAddItemDto>();
		ApiAddItemDto dto = queryApiAddItemDto(mapping, shop, amount);
		apiList.add(dto);
		ShopAddItemsBo bo = addFactory.getInstance(shop.getpName());
		bo.addItems2Shop(apiList, shop);
	}

	/**
	 * 补全信息(传入库存量)
	 * 
	 * @param mapping
	 * @param shop
	 * @return
	 */
	private ApiAddItemDto queryApiAddItemDto(ShopItemMapping mapping, ShopInfo shop, Integer amount) {
		ApiAddItemDto dto = getDto2DBorNet(mapping, shop);
		dto.setStockBalance(amount);
		return dto;
	}

	/**
	 * 全部发布商品，500一批次
	 * 
	 * @param shop
	 */
	private void addAllProcessingItems(ShopInfo shop) {
		Pageable page = new PageRequest(0, 500);
		Page<ShopItemMapping> pageList = null;
		do {
			pageList = this.shopItemMappingDao.findByShopIdAndSystemStatus(shop.getShopId(),
					ShopItemMappingSystemStatus.PROCESSING.getValue(), page);
			List<ShopItemMapping> bookList = pageList.getContent();
			List<ApiAddItemDto> apiList = queryApiAddItemDtos(bookList, shop);
			ShopAddItemsBo bo = addFactory.getInstance(shop.getpName());
			bo.addItems2Shop(apiList, shop);
			page = pageList.nextPageable();
		} while (pageList.hasNext());
	}

	/**
	 * 补全信息（需查询库存量）
	 * 
	 * @param mappings
	 * @param shop
	 * @return
	 */
	private List<ApiAddItemDto> queryApiAddItemDtos(List<ShopItemMapping> mappings, ShopInfo shop) {
		List<ApiAddItemDto> resultList = new ArrayList<ApiAddItemDto>();

		for (ShopItemMapping s : mappings) {
			ApiAddItemDto dto = getDto2DBorNet(s, shop);
			if (dto == null) {
				continue;
			}
			// 查询库存量
			Integer stockBalance = queryStockBalance(s.getBookId());
			if (stockBalance > 0) {
				dto.setStockBalance(stockBalance);
			} else {
				// 无库存量
				saveFailShopItemMapping(s, NOT_AMOUNT);
				continue;
			}
			resultList.add(dto);
		}
		return resultList;
	}

	/**
	 * 补全信息（数据库查询或者从NET查询）
	 * 
	 * @param mapping
	 * @param shop
	 * @return
	 */
	private ApiAddItemDto getDto2DBorNet(ShopItemMapping mapping, ShopInfo shop) {
		ApiAddItemDto dto = new ApiAddItemDto();
		dto.setShopItemMapping(mapping);
		dto.setDeliveryTemplateId(shop.getDeliveryTemplateId());
		Bookinfo book = this.bookService.findBookById(mapping.getBookId());
		BookinfoDetail detail = bookService.findBookInfoDetailByBookId(book.getId());
		// 如果没有图书详情，则从网上采集
		if (detail == null) {
			try {
				detail = bookService.captureBookInfoDetailFromNet(book.getId());
			} catch (Exception e) {
				// 单条错误不能影响全部记录
				String errorMsg = "[数据采集] 采集过程中发生错误，bookId=" + book.getId();
				logger.error(errorMsg, e);
				saveFailShopItemMapping(mapping, errorMsg);
			}
		}
		// 如果没有采集到图书详情，则跳过此条记录
		if (detail == null) {
			return null;
		}
		BeanUtils.copyProperties(book, dto);
		BeanUtils.copyProperties(detail, dto);
		return dto;
	}

	/**
	 * 查询库存量
	 * 
	 * @param bookId
	 * @return
	 */
	private Integer queryStockBalance(Integer bookId) {
		// TODO 查询库存量
		return 1;
	}

	private void saveFailShopItemMapping(ShopItemMapping mapping, String errorMsg) {
		mapping.setSystemStatus(ShopItemMappingSystemStatus.FAIL.getValue());
		mapping.setUpdateTime(new Date());
		mapping.setFailReason(errorMsg);
		this.shopItemMappingDao.save(mapping);
	}

	/**
	 * 商品下载bo
	 * 
	 * @param list
	 * @param shop
	 */
	public void downloadItems2Mapping(ShopDownloadInterfaceDto dto, ShopInfo shop) {
		ShopDownloadBo bo = this.dowloadFactory.getInstance(shop.getpName());
		List<ShopItemMapping> mappingList = bo.downloadItems(dto, shop);
		checkOutId(mappingList, shop);
	}

	/**
	 * 检查商家编码是否符合规范，并上传
	 * 
	 * @param list
	 */
	private void checkOutId(List<ShopItemMapping> list, ShopInfo shop) {
		StringBuilder sb = new StringBuilder();
		for (ShopItemMapping s : list) {
			String[] splitOuterId = splitOuterId(s.getItemOutNum());
			// 无商家编码
			if (splitOuterId.length == 0) {
				String msg = String.format("%s %s <p/>\n", s.getTitle(), "商家编码缺失");
				sb.append(msg);
				logger.error(msg);
				continue;
			}
			//商家编码符合规范，进行检查是否数据库中有对应数据，确保商家编码非乱填
			if (splitOuterId.length == 2) {
				String bookId = splitOuterId[1];
				String isbn = splitOuterId[0];
				//如果bookId不为数字加入错误文件
				if (!StringUtils.isNumeric(bookId)) {
					String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(), "bookId只能为数字"
							+ bookId);
					sb.append(msg);
					logger.error(msg);
					continue;
				}
				Bookinfo book = this.bookService.findByIdAndIsbn(Integer.parseInt(bookId), isbn);
				//对应商家编码在数据库中无数据，证明商家编码为乱填，加入错误待生成邮件内容中
				if (book == null) {
					String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(), "商家编码有误");
					sb.append(msg);
					logger.error(msg);
					continue;
				}
				hasWaitDownload(s, book, STATUS_BOOK_ID);
				continue;
			}
			//商家编码不为1，加入错误内容
			if(splitOuterId.length != 1){
				String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(), "商家编码有误");
				sb.append(msg);
				logger.error(msg);
				continue;
			}
			
			// 判断是否在zis有数据
			List<Bookinfo> bookList = hasBookInfoList(s.getItemOutNum());

			// 无数据 从网站抓取
			if (bookList.isEmpty()) {
				try {
					Bookinfo book = getNetBookInfo(splitOuterId[0]);
					// 没有找到商品
					if (book == null) {
						String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(),
								"无法找到对应的商品,可能为一码多书");
						sb.append(msg);
						addFailDownLoadLog(s.getShopId(), msg);
						logger.error(msg);
						continue;
					}
					// 检查图书是否为wait_download状态，并做相应保存
					hasWaitDownload(s, book, STATUS_ISBN);
					continue;
				} catch (Exception e) {
					// 抓取过程中出错
					String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(), e.getMessage());
					sb.append(msg);
					addFailDownLoadLog(s.getShopId(), msg);
					logger.error(e.getMessage(), e);
					continue;
				}
			}

			// 判断是否为一码多书，一码多书返回null
			Bookinfo book = uniqueBookInfo(bookList);
			if (book == null) {
				String msg = String.format("%s  %s  %s <p/>\n", s.getTitle(), s.getItemOutNum(), "一码多书");
				sb.append(msg);
				addFailDownLoadLog(s.getShopId(), msg);
				continue;
			}
			// 检查图书是否为wait_download状态,并做相应保存
			hasWaitDownload(s, book, STATUS_ISBN);
		}
		if (StringUtils.isNotBlank(sb.toString())) {
			String[] mail = { shop.getEmails() };
			// 发送错误邮件
			sendFailEmail(mail, sb.toString(), shop);
		}
	}

	/**
	 * 检查图书是否为wait_download状态,并做相应保存
	 * 
	 * @param mapping
	 * @param book
	 */
	private void hasWaitDownload(ShopItemMapping mapping, Bookinfo book, String status) {
		mapping.setBookId(book.getId());
		// 查询是否有映射
		ShopItemMapping shopMapping = this.shopItemMappingDao.findByShopIdAndBookId(mapping.getShopId(),
				mapping.getBookId());
		// 没有数据并且商家编码为ISBN-bookId格式，保存wait记录
		if (shopMapping == null && STATUS_BOOK_ID.equals(status)) {
			mapping.setSystemStatus(ShopItemMappingSystemStatus.WAIT.getValue());
			mapping.setUpdateTime(new Date());
			mapping.setCreateTime(new Date());
			mapping.setItemOutNum(book.getIsbn() + "-" + book.getId());
			this.shopItemMappingDao.save(mapping);
		// 没有数据并且商家编码为ISBN格式，保存wait记录
		} else if (shopMapping == null && STATUS_ISBN.equals(status)) {
			mapping.setSystemStatus(ShopItemMappingSystemStatus.WAIT.getValue());
			mapping.setUpdateTime(new Date());
			mapping.setCreateTime(new Date());
			mapping.setItemOutNum(book.getIsbn());
			this.shopItemMappingDao.save(mapping);
			// 有数据并未为Wait_download平台商品Id及状态为成功,其他状态跳过
		} else {
			if (ShopItemMappingSystemStatus.WAIT_DOWNLOAD.getValue().equals(shopMapping.getSystemStatus())) {
				shopMapping.setpItemId(mapping.getpItemId());
				shopMapping.setSystemStatus(ShopItemMappingSystemStatus.SUCCESS.getValue());
				this.shopItemMappingDao.save(shopMapping);
			}
		}
	}

	/**
	 * 发送错误邮件
	 * 
	 * @param mail
	 * @param msg
	 * @param shop
	 */
	private void sendFailEmail(String[] mail, String msg, ShopInfo shop) {
		try {
			mailSender.send(mail, "店铺错误数据" + ZisUtils.getDateString("yyyy年MM月dd天HH时mm分"), msg);
		} catch (AddressException e) {
			addErrorDownLoadLog(shop.getShopId(), e.getMessage());
			logger.error(e.getMessage(), e);
			try {
				mailSender.send(shaoweiEmail,
						shop.getShopId() + "店铺错误数据" + ZisUtils.getDateString("yyyy年MM月dd天HH时mm分"), msg);
			} catch (Exception e2) {
				addErrorDownLoadLog(shop.getShopId(), e2.getMessage());
				logger.error(e2.getMessage(), e2);
			}
		} catch (Exception e1) {
			addErrorDownLoadLog(shop.getShopId(), e1.getMessage());
			logger.error(e1.getMessage(), e1);
		}
	}

	/**
	 * 库存中唯一book
	 * 
	 * @param list
	 * @return
	 */
	private Bookinfo uniqueBookInfo(List<Bookinfo> list) {
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 设置错误参数
	 * 
	 * @param shopId
	 * @param desc
	 */
	private DownloadItemLog addDownLoadLog(Integer shopId, String desc) {
		DownloadItemLog log = new DownloadItemLog();
		log.setCreateTime(new Date());
		log.setUpdateTime(new Date());
		log.setDescription(desc);
		log.setShopId(shopId);
		return log;
	}

	/**
	 * 添加失败更新日志
	 * 
	 * @param shopId
	 * @param desc
	 */
	private void addFailDownLoadLog(Integer shopId, String desc) {
		DownloadItemLog log = addDownLoadLog(shopId, desc);
		log.setStatus(DownloadItemLog.DownloadItemLogStatus.FAIL.getValue());
		this.downloadItemLogDao.save(log);
	}

	/**
	 * 添加失败更新日志
	 * 
	 * @param shopId
	 * @param desc
	 */
	private void addErrorDownLoadLog(Integer shopId, String desc) {
		DownloadItemLog log = addDownLoadLog(shopId, desc);
		log.setStatus(DownloadItemLog.DownloadItemLogStatus.ERROR.getValue());
		this.downloadItemLogDao.save(log);
	}

	/**
	 * 判定商家编码是否在库存中有数据
	 * 
	 * @param outerId
	 * @return
	 */
	private List<Bookinfo> hasBookInfoList(String outerId) {
		String[] splitOuterId = splitOuterId(outerId);
		String isbn = "";
		switch (splitOuterId.length) {
		case 1:
			isbn = splitOuterId[0];
			List<Bookinfo> list1 = this.bookService.findBookByISBN(isbn);
			return list1;
		default:
			return null;
		}
	}

	/**
	 * 获取网络数据
	 * 
	 * @param isbn
	 * @return
	 */
	private Bookinfo getNetBookInfo(String isbn) {
		BookMetadata meta = getBookInfo(isbn);
		if (meta == null) {
			return null;
		}
		Bookinfo book = new Bookinfo();
		book = copyBookInfo(meta, book);
		if (book.getBookName().length() > 50) {
			String nameStr = book.getBookName().substring(0, 50);
			book.setBookName(nameStr);
		}
		if (StringUtils.isBlank(meta.getPublisher())) {
			book.setBookPublisher("暂无");
		} else {
			book.setBookPublisher(meta.getPublisher());
		}
		if (StringUtils.isBlank(meta.getAuthor())) {
			book.setBookAuthor("暂无");
		} else {
			book.setBookAuthor(meta.getAuthor());
		}
		book.setGmtCreate(new Date());
		book.setGmtModify(new Date());
		book.setBookStatus("正式");
		book.setIsNewEdition(true);
		book.setRelateId("0");
		// 添加至数据库
		this.bookInfoDao.save(book);
		return book;
	}

	/**
	 * 调用远程查找
	 * 
	 * @param isbn
	 * @return
	 */
	private BookMetadata getBookInfo(String isbn) {
		BookMetadata meta = bookMetadataCapture.captureListPage(isbn);
		return meta;
	}

	/**
	 * 将商家编码 以'-'分割为数组
	 * 
	 * @param outerId
	 * @return
	 */
	private String[] splitOuterId(String outerId) {
		String[] isbnAndBookId = outerId.split("-");
		return isbnAndBookId;
	}

	/**
	 * 将BookMetadata 转换为bookinfo
	 * 
	 * @param meta
	 * @param bookinfo
	 * @return
	 */
	private Bookinfo copyBookInfo(BookMetadata meta, Bookinfo bookinfo) {
		BeanUtils.copyProperties(meta, bookinfo);
		bookinfo.setBookAuthor(meta.getAuthor());
		bookinfo.setBookEdition(meta.getEdition());
		bookinfo.setBookName(meta.getName());
		bookinfo.setBookPrice(meta.getPrice());
		bookinfo.setIsbn(meta.getIsbnCode());
		return bookinfo;
	}
}
