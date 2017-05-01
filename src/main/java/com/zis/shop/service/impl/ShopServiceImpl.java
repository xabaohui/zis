package com.zis.shop.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;
import com.zis.common.mail.MailSenderFactory;
import com.zis.common.mail.SimpleMailSender;
import com.zis.common.util.TextClearUtils;
import com.zis.common.util.ZisUtils;
import com.zis.shop.bean.Company;
import com.zis.shop.bean.DownloadItemLog;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingSystemStatus;
import com.zis.shop.bo.ShopBo;
import com.zis.shop.dto.CheckOutIdDTO;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.dto.SaveOrUpdateShopDto;
import com.zis.shop.dto.ShopDownLoadFailDTO;
import com.zis.shop.dto.TaoBaoImportExcelDto;
import com.zis.shop.repository.CompanyDao;
import com.zis.shop.repository.DownloadItemLogDao;
import com.zis.shop.repository.ShopInfoDao;
import com.zis.shop.repository.ShopItemMappingDao;
import com.zis.shop.service.ShopService;
import com.zis.shop.util.ShopUtil;

@Service
public class ShopServiceImpl implements ShopService {

	private final String MAPPING_SUCCESS = ShopItemMappingSystemStatus.SUCCESS.getValue();
	private final String MAPPING_FAIL = ShopItemMappingSystemStatus.FAIL.getValue();
	private final String MAPPING_WAIT = ShopItemMappingSystemStatus.WAIT.getValue();
	private final String MAPPING_DELETE = ShopItemMappingSystemStatus.DELETE.getValue();
	private final String MAPPING_PROCESSING = ShopItemMappingSystemStatus.PROCESSING.getValue();
	private final String[] shopItemMappingStatus = { MAPPING_SUCCESS, MAPPING_FAIL, MAPPING_WAIT, MAPPING_DELETE,
			MAPPING_PROCESSING };

	final String NORMAL = "normal";
	final String DELETE = "delete";
	final Long PAGE_SIZE = 50L;
	final String FOR_SHELVED = "for_shelved";// 下架
	final String SOLD_OUT = "sold_out";// 售罄

	private static Logger logger = Logger.getLogger(ShopServiceImpl.class);

	private final String shaoweiEmail[] = { "to_shaowei@163.com" };

	@Autowired
	private ShopInfoDao shopInfoDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ShopBo ShopBo;

	@Autowired
	private ShopItemMappingDao shopItemMappingDao;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	// @Autowired
	// private ApiTransferFactory factory;

	@Autowired
	private DownloadItemLogDao downloadItemLogDao;

	@Autowired
	private BookService bookService;

	private SimpleMailSender mailSender = MailSenderFactory.getSender();

	/**
	 * 库存变动更新商品（单个更新）
	 * 
	 * @param shop
	 * @param bookId
	 * @param amount
	 */
	// TODO 库存变动使用
	@Override
	public void stockChangeToShopUPdateItem(Integer companyId, Integer bookId, Integer amount) {
		List<ShopInfo> shopList = this.shopInfoDao.findByCompanyId(companyId);
		for (ShopInfo shop : shopList) {
			ShopItemMapping mapping = this.shopItemMappingDao.findByShopIdAndBookId(shop.getShopId(), bookId);
			if (mapping != null) {
				this.ShopBo.stockChangeToUpdateOrAddItems(mapping, shop, amount);
			} else {
				Bookinfo book = this.bookService.findNormalBookById(bookId);
				saveShopMappingStatusWait(book, shop);
			}
		}
	}

	/**
	 * 分页查找shopItemMapping
	 * 
	 * @param shopId
	 * @param status
	 * @param isbn
	 * @param page
	 * @return
	 */
	@Override
	public Page<ShopItemMapping> queryShopItemMapping(Integer shopId, String status, String isbn, Pageable page) {
		List<String> statusList = getStatusList();
		if (!statusList.contains(status)) {
			status = MAPPING_WAIT;
		}
		Page<ShopItemMapping> pageList = null;
		if (StringUtils.isNotBlank(isbn)) {
			List<Bookinfo> list = this.bookService.findBookByISBN(isbn);
			List<Integer> bookIds = new ArrayList<Integer>();
			for (Bookinfo b : list) {
				bookIds.add(b.getId());
			}
			pageList = this.shopItemMappingDao.findByShopIdAndSystemStatusAndBookIdIn(shopId, status, bookIds, page);
		} else {
			pageList = this.shopItemMappingDao.findByShopIdAndSystemStatus(shopId, status, page);
		}

		return pageList;
	}

	/**
	 * 获取所有shopItemMapping所有状态
	 * 
	 * @return
	 */
	@Override
	public List<String> getStatusList() {
		List<String> list = Arrays.asList(shopItemMappingStatus);
		return list;
	}

	/**
	 * 初始化店铺数据
	 * 
	 * @param bookId
	 * @param shop
	 */
	// TODO 库存初始化数据使用
	@Override
	public void initShopMapping(Integer bookId, ShopInfo shop) {
		ShopItemMapping mapping = this.shopItemMappingDao.findByShopIdAndBookId(shop.getShopId(), bookId);
		if (mapping == null) {
			Bookinfo book = this.bookService.findNormalBookById(bookId);
			saveShopMappingStatusWait(book, shop);
		}
	}

	/**
	 * mapping添加一条wait记录
	 * 
	 * @param book
	 * @param shop
	 */
	private void saveShopMappingStatusWait(Bookinfo book, ShopInfo shop) {
		ShopItemMapping newMapping = new ShopItemMapping();
		newMapping.setBookId(book.getId());
		newMapping.setCreateTime(new Date());
		newMapping.setItemOutNum(String.format("%s-%s", book.getIsbn(), book.getId()));
		newMapping.setShopId(shop.getShopId());
		newMapping.setSystemStatus(ShopItemMappingSystemStatus.WAIT.getValue());
		newMapping.setTitle(TextClearUtils.buildTitleFormType(shop.getpName(), book));
		newMapping.setUpdateTime(new Date());
		this.shopItemMappingDao.save(newMapping);
	}

	/**
	 * 全部发布商品
	 * 
	 * @param shop
	 */
	@Override
	@Transactional
	public Integer addItem2Shop(ShopInfo shop) {
		Integer amount = this.shopItemMappingDao.updateShopStatusWaitToProcessing(shop.getShopId());
		abstractAddAllProcessingItems(shop);
		return amount;
	}

	/**
	 * 失败全部发布商品
	 * 
	 * @param shop
	 */
	@Override
	@Transactional
	public Integer failAddItem2Shop(ShopInfo shop) {
		Integer amount = this.shopItemMappingDao.updateShopStatusFailToProcessing(shop.getShopId());
		abstractAddAllProcessingItems(shop);
		return amount;
	}

	/**
	 * 批量发布商品
	 * 
	 * @param shop
	 */
	@Override
	@Transactional
	public Integer addItem2Shop(List<Integer> mappingIds, ShopInfo shop) {
		List<ShopItemMapping> list = this.shopItemMappingDao.findByShopIdAndSystemStatusAndIdIn(shop.getShopId(),
				ShopItemMappingSystemStatus.WAIT.getValue(), mappingIds);
		for (ShopItemMapping s : list) {
			s.setSystemStatus(ShopItemMappingSystemStatus.PROCESSING.getValue());
		}
		this.shopItemMappingDao.save(list);
		addProcessingItems(mappingIds, shop);
		return mappingIds.size();
	}

	/**
	 * 失败批量发布商品
	 * 
	 * @param shop
	 */
	@Override
	@Transactional
	public Integer failAddItem2Shop(List<Integer> mappingIds, ShopInfo shop) {
		List<ShopItemMapping> list = this.shopItemMappingDao.findByShopIdAndSystemStatusAndIdIn(shop.getShopId(),
				ShopItemMappingSystemStatus.FAIL.getValue(), mappingIds);
		for (ShopItemMapping s : list) {
			s.setSystemStatus(ShopItemMappingSystemStatus.PROCESSING.getValue());
		}
		this.shopItemMappingDao.save(list);
		addProcessingItems(mappingIds, shop);
		return mappingIds.size();
	}

	/**
	 * 发布单个商品
	 * 
	 * @param mappingId
	 * @param shop
	 * @return
	 */
	@Override
	@Transactional
	public Integer addItem2Shop(Integer mappingId, ShopInfo shop) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(mappingId);
		List<ShopItemMapping> mapping = this.shopItemMappingDao.findByShopIdAndSystemStatusAndIdIn(shop.getShopId(),
				ShopItemMappingSystemStatus.WAIT.getValue(), list);
		for (ShopItemMapping s : mapping) {
			s.setSystemStatus(ShopItemMappingSystemStatus.PROCESSING.getValue());
		}
		this.shopItemMappingDao.save(mapping);
		addProcessingItems(list, shop);
		return 1;
	}

	/**
	 * 失败发布单个商品
	 * 
	 * @param mappingId
	 * @param shop
	 * @return
	 */
	@Override
	@Transactional
	public Integer failAddItem2Shop(Integer mappingId, ShopInfo shop) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(mappingId);
		List<ShopItemMapping> mapping = this.shopItemMappingDao.findByShopIdAndSystemStatusAndIdIn(shop.getShopId(),
				ShopItemMappingSystemStatus.FAIL.getValue(), list);
		for (ShopItemMapping s : mapping) {
			s.setSystemStatus(ShopItemMappingSystemStatus.PROCESSING.getValue());
		}
		this.shopItemMappingDao.save(mapping);
		addProcessingItems(list, shop);
		return 1;
	}

	/**
	 * 异步发布全部商品
	 * 
	 * @param shop
	 */
	private void abstractAddAllProcessingItems(ShopInfo shop) {
		this.ShopBo.abstractAddAllProcessingItems(shop);
	}

	/**
	 * 发布单个或批量
	 * 
	 * @param shop
	 */
	private void addProcessingItems(List<Integer> mappingIds, ShopInfo shop) {
		this.ShopBo.addProcessingItems(mappingIds, shop);
	}

	/**
	 * 异步处理下载数据及发送错误邮件
	 * 
	 * @param mappingList
	 * @param shop
	 */
	@Override
	public void asynchronousPrcessDownLoadMappingDataAndSendEmail(final List<ShopItemMapping> mappingList,
			final ShopInfo shop) {
		Thread task = new Thread(new Runnable() {
			public void run() {
				List<ShopDownLoadFailDTO> failList = prcessDownLoadMappingData(mappingList, shop);
				if (!CollectionUtils.isEmpty(failList)) {
					StringBuilder sb = new StringBuilder();
					sb.append("<b>" + shop.getShopName() + "</b><p/>\n");
					for (ShopDownLoadFailDTO dto : failList) {
						String str = String.format("%s  %s  %s <p/>\n", dto.getTitle(), dto.getOuterId(),
								dto.getFailReason());
						sb.append(str);
					}
					String[] email = { shop.getEmails() };
					sendFailEmail(email, sb.toString(), shop);
				}
			}
		});
		taskExecutor.execute(task);
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
	 * 处理下载数据至mapping
	 * 
	 * @param mappingList
	 * @return
	 */
	@Override
	public List<ShopDownLoadFailDTO> prcessDownLoadMappingData(List<ShopItemMapping> mappingList, ShopInfo shop) {
		List<ShopDownLoadFailDTO> failList = new ArrayList<ShopDownLoadFailDTO>();
		for (ShopItemMapping s : mappingList) {
			// 检查
			CheckOutIdDTO dto = checkOutId(s);
			if (dto.getIsSuccess()) {
				// 保存
				saveDownloadMapping(s, dto.getBook());
			} else {
				ShopDownLoadFailDTO failDto = new ShopDownLoadFailDTO();
				failDto.setOuterId(s.getItemOutNum());
				failDto.setTitle(s.getTitle());
				failDto.setFailReason(dto.getFailMsg());
				failList.add(failDto);
			}
		}
		return failList;
	}

	/**
	 * 保存下载的映射表数据
	 * 
	 * @param mapping
	 * @param book
	 */
	private void saveDownloadMapping(ShopItemMapping mapping, Bookinfo book) {
		mapping.setBookId(book.getId());
		// 查询是否有映射
		ShopItemMapping shopMapping = this.shopItemMappingDao.findByShopIdAndBookId(mapping.getShopId(),
				mapping.getBookId());
		if (shopMapping == null) {
			mapping.setSystemStatus(ShopItemMappingSystemStatus.WAIT.getValue());
			mapping.setUpdateTime(new Date());
			mapping.setCreateTime(new Date());
			mapping.setItemOutNum(mapping.getItemOutNum());
			this.shopItemMappingDao.save(mapping);
		} else {
			if (shopMapping.getpItemId() == null
					&& ShopItemMappingSystemStatus.SUCCESS.getValue().equals(shopMapping.getSystemStatus())) {
				shopMapping.setpItemId(mapping.getpItemId());
				this.shopItemMappingDao.save(shopMapping);
			}
//			
//			//商家编码更新
//			if (ShopItemMappingSystemStatus.SUCCESS.getValue().equals(shopMapping.getSystemStatus())
//					&& !shopMapping.getItemOutNum().equals(mapping.getItemOutNum())) {
//				shopMapping.setItemOutNum(mapping.getItemOutNum());
//				this.shopItemMappingDao.save(shopMapping);
//			}
		}
	}

	/**
	 * 检查商家编码是否符合规范，并上传
	 * 
	 * @param list
	 */
	private CheckOutIdDTO checkOutId(ShopItemMapping mapping) {
		CheckOutIdDTO dto = new CheckOutIdDTO();
		String msg = "";
		String[] splitOuterId = splitOuterId(mapping.getItemOutNum());
		// 无商家编码
		if (splitOuterId.length == 0 || splitOuterId.length > 2) {
			msg = "商家编码格式有误或不存在";
			logger.error(msg);
			dto.setFailMsg(msg);
			dto.setIsSuccess(false);
			return dto;
		}
		// 商家编码符合规范，进行检查是否数据库中有对应数据，确保商家编码非乱填
		if (splitOuterId.length == 2) {
			String bookId = splitOuterId[1];
			String isbn = splitOuterId[0];
			// 如果bookId不为数字加入错误文件
			if (!StringUtils.isNumeric(bookId)) {
				msg = "bookId只能为数字" + bookId;
				logger.error(msg);
				dto.setFailMsg(msg);
				dto.setIsSuccess(false);
				return dto;
			}
			Bookinfo book = this.bookService.findByIdAndIsbn(Integer.parseInt(bookId), isbn);
			// 对应商家编码在数据库中无数据，证明商家编码为乱填，加入错误待生成邮件内容中
			if (book == null) {
				msg = "商家编码有误";
				logger.error(msg);
				dto.setFailMsg(msg);
				dto.setIsSuccess(false);
				return dto;
			}
			dto.setIsSuccess(true);
			dto.setBook(book);
			return dto;
		}
		// 只有外部编码只有isbn的情况
		String isbn = splitOuterId[0];
		List<Bookinfo> list1 = this.bookService.findBookByISBN(isbn);
		if (CollectionUtils.isEmpty(list1)) {
			msg = "无此图书";
			logger.error(msg);
			dto.setFailMsg(msg);
			dto.setIsSuccess(false);
			return dto;
		} else if (list1.size() > 1) {
			msg = "一码多书";
			logger.error(msg);
			dto.setFailMsg(msg);
			dto.setIsSuccess(false);
			return dto;
		} else {
			dto.setIsSuccess(true);
			dto.setBook(list1.get(0));
			return dto;
		}
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

	// /**
	// * 异步下载有赞店铺信息，进行更新
	// *
	// * @param shop
	// */
	// //TODO 暂时不考虑有赞的店铺
	// public void downloadYouZanToShopItemMapping(ShopInfo shop) {
	// ApiTransfer api = factory.getInstance(shop.getpName());
	// queryItemsOnsale(api, shop);
	// queryItemsInventory(api, shop, FOR_SHELVED);
	// queryItemsInventory(api, shop, SOLD_OUT);
	//
	// }
	//
	// /**
	// * 查询所有在售商品 下载
	// *
	// * @param api
	// * @param shop
	// */
	// //TODO 暂时不考虑有赞的店铺
	// private void queryItemsOnsale(ApiTransfer api, ShopInfo shop) {
	// Long page = 1L;
	// Long totalResults = null;
	// do {
	// ApiQueryItemsDto dto = null;
	// try {
	// dto = api.queryItemsOnsale(shop, page);
	// } catch (Exception e) {
	// addFailDownLoadLog(shop.getShopId(), e.getMessage());
	// continue;
	// }
	// ShopDownloadYouZanDto youZanDto = new ShopDownloadYouZanDto();
	// youZanDto.setApiQueryItemsDto(dto);
	// this.ShopBoImpl.downloadItems2Mapping(youZanDto, shop);
	// totalResults = dto.getTotalResults();
	// page++;
	// } while (hasNextPage(page, totalResults));
	// }

	// /**
	// * 查询所有下架或售罄商品 下载
	// *
	// * @param api
	// * @param shop
	// */
	// //TODO 暂时不考虑有赞的店铺
	// public void queryItemsInventory(ApiTransfer api, ShopInfo shop, String
	// type) {
	// Long page = 1L;
	// Long totalResults = null;
	// do {
	// ApiQueryItemsDto dto = null;
	// try {
	// dto = api.queryItemsInventory(shop, type, page);
	// } catch (Exception e) {
	// addFailDownLoadLog(shop.getShopId(), e.getMessage());
	// continue;
	// }
	// ShopDownloadYouZanDto youZanDto = new ShopDownloadYouZanDto();
	// youZanDto.setApiQueryItemsDto(dto);
	// this.ShopBoImpl.downloadItems2Mapping(youZanDto, shop);
	// totalResults = dto.getTotalResults();
	// page++;
	// } while (hasNextPage(page, totalResults));
	// }
	// /**
	// * 判断是否 有下一页
	// *
	// * @param page
	// * @param totalResults
	// * @return
	// */
	// //TODO 有赞暂时不做
	// private boolean hasNextPage(Long page, Long totalResults) {
	// if (page * PAGE_SIZE <= totalResults) {
	// return true;
	// } else {
	// Long max = page * PAGE_SIZE;
	// Long result = max - totalResults;
	// if (result < PAGE_SIZE) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	// }

	/**
	 * 将淘宝xls 转化成mapping
	 * 
	 * @param input
	 * @param shop
	 * @return
	 */
	@Override
	public List<ShopItemMapping> taobaoExeclToMapping(InputStream input, ShopInfo shop) {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 1;
		try {
			// 初始化导入器
			FileImporter<TaoBaoImportExcelDto> im = new ExcelImporter<TaoBaoImportExcelDto>(input, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				logger.error(errMsg);
				addErrorDownLoadLog(shop.getShopId(), errMsg);
				throw new RuntimeException("导入文件非法");
			}
			String subCheck = subCheckFileFormat(im.loadFactHeader());
			if (StringUtils.isNotBlank(subCheck)) {
				logger.error(errMsg);
				addErrorDownLoadLog(shop.getShopId(), errMsg);
				throw new RuntimeException(subCheck);
			}

			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			TaoBaoImportExcelDto instance = new TaoBaoImportExcelDto();
			List<TaoBaoImportExcelDto> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				logger.error(errMsg);
				addErrorDownLoadLog(shop.getShopId(), errMsg);
				throw new RuntimeException("导入失败，文件为空");
			}
			List<ShopItemMapping> mappingList = new ArrayList<ShopItemMapping>();
			StringBuilder sb = new StringBuilder();
			for (TaoBaoImportExcelDto t : list) {
				ShopItemMapping s = null;
				try {
					s = getShopItemMapping(t, shop);
					mappingList.add(s);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					addFailDownLoadLog(shop.getShopId(), e.getMessage());
					String msg = String.format("%s  宝贝ID：%s  %s <p/>\n", t.getTitle(), t.getNumIid(), "宝贝信息有误");
					sb.append(msg);
				}
			}
			String[] mail = { shop.getEmails() };
			if (StringUtils.isNotBlank(sb.toString())) {
				sendFailEmail(mail, sb.toString(), shop);
			}
			return mappingList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorDownLoadLog(shop.getShopId(), e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private ShopItemMapping getShopItemMapping(TaoBaoImportExcelDto dto, ShopInfo shop) {
		ShopItemMapping s = new ShopItemMapping();
		s.setItemOutNum(dto.getOuterId());
		s.setpItemId(Long.parseLong(dto.getNumIid().trim()));
		s.setTitle(dto.getTitle());
		s.setShopId(shop.getShopId());
		s.setCreateTime(new Date());
		s.setUpdateTime(new Date());
		return s;
	}

	/**
	 * 检查列开头是否符合规范
	 * 
	 * @param factHeader
	 * @return
	 */
	private String subCheckFileFormat(List<String> factHeader) {
		String NUM_IID = "宝贝ID";
		String TITLE = "商品标题";
		String OUTER_ID = "商家编码";
		if (!factHeader.get(0).equals(NUM_IID)) {
			return "格式错误，第一列必须是:" + NUM_IID;
		}
		if (!factHeader.get(1).equals(TITLE)) {
			return "格式错误，第二列必须是:" + TITLE;
		}
		if (!factHeader.get(2).equals(OUTER_ID)) {
			return "格式错误，第三列必须是:" + OUTER_ID;
		}
		return null;
	}

	/**
	 * 初始化对象的映射文件
	 * 
	 * @return
	 */
	private Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("numIid", 0);
		map.put("title", 1);
		map.put("outerId", 2);
		return map;
	}

	/**
	 * 查询公司
	 * 
	 * @param companyName
	 * @param contacts
	 * @param page
	 * @return
	 */
	@Override
	public Page<Company> queryCompany(String companyName, String contacts, Pageable page) {
		if (!StringUtils.isBlank(companyName)) {
			return this.companyDao.findByLikeCompanyName(companyName.trim(), page);
		} else if (!StringUtils.isBlank(contacts)) {
			return this.companyDao.findByContacts(contacts.trim(), page);
		} else {
			return this.companyDao.findAllCompany(page);
		}
	}

	/**
	 * 查询单个公司
	 * 
	 * @param companyId
	 * @return
	 */
	@Override
	public Company findCompanyOne(Integer companyId) {
		return this.companyDao.findByCompanyId(companyId);
	}

	/**
	 * 新增公司
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void saveCompany(SaveOrUpdateCompanyDto dto) {
		if (dto.getCompanyId() != null) {
			throw new RuntimeException("新增公司，companyId应为空");
		}
		Company verifyCompany = this.companyDao.findByCompanyName(dto.getCompanyName());
		// 如果存在记录，防止重复提交
		if (verifyCompany != null) {
			return;
		}
		Company company = new Company();
		company.setCreateTime(new Date());
		company.setStatus(NORMAL);
		BeanUtils.copyProperties(dto, company);
		company.setAddress(dto.getAddress());
		company.setUpdateTime(new Date());
		this.companyDao.save(company);
		ShopUtil.clearAllCached();
	}

	/**
	 * 修改公司
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void updateCompany(SaveOrUpdateCompanyDto dto) {
		if (dto.getCompanyId() == null) {
			throw new RuntimeException("companyId为空");
		}
		Company company = this.companyDao.findByCompanyId(dto.getCompanyId());
		if (company != null) {
			BeanUtils.copyProperties(company, dto);
			company.setUpdateTime(new Date());
			this.companyDao.save(company);
			ShopUtil.clearAllCached();
		} else {
			String message = "修改用户出错，请联系管理员" + "修改的公司为:[" + company + "]";
			logger.error(message);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 查找 公司下的店铺
	 * 
	 * @param companyId
	 * @return
	 */
	@Override
	public List<ShopInfo> findCompanyShop(Integer companyId) {
		List<ShopInfo> list = this.shopInfoDao.findByCompanyId(companyId);
		return list;
	}

	/**
	 * 新增店铺
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void saveShop(SaveOrUpdateShopDto dto) {
		if (dto.getShopId() != null) {
			throw new RuntimeException("新增店铺，shopId应为空");
		}
		ShopInfo verifyShop = this.shopInfoDao.findByShopNameAndPNameAndCompanyId(dto.getShopName(), dto.getpName(),
				dto.getCompanyId());
		// 如果存在记录，防止重复提交
		if (verifyShop != null) {
			return;
		}
		ShopInfo shop = new ShopInfo();
		BeanUtils.copyProperties(dto, shop);
		shop.setDiscount(Double.parseDouble(dto.getDiscount()));
		shop.setUpdateTime(new Date());
		shop.setStatus(NORMAL);
		shop.setCreateTime(new Date());
		this.shopInfoDao.save(shop);
	}

	/**
	 * 修改店铺
	 * 
	 * @param dto
	 */
	@Override
	@Transactional
	public void updateShop(SaveOrUpdateShopDto dto) {
		if (dto.getShopId() == null) {
			throw new RuntimeException("shopId为空");
		}
		ShopInfo shop = this.shopInfoDao.findByCompanyIdAndShopId(dto.getCompanyId(), dto.getShopId());
		if (shop == null) {
			throw new RuntimeException("错误的shopId " + dto.getShopId());
		}
		BeanUtils.copyProperties(dto, shop);
		Double double1 = Double.parseDouble(dto.getDiscount());
		shop.setDiscount(double1);
		shop.setUpdateTime(new Date());
		this.shopInfoDao.save(shop);
	}

	/**
	 * 删除店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	@Override
	public String deleteShop(Integer companyId, Integer shopId) {
		ShopInfo shop = this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
		if (shop != null) {
			shop.setStatus(DELETE);
			this.shopInfoDao.save(shop);
			return shop.getShopName();
		} else {
			throw new RuntimeException("您需要删除的店铺不存在，请联系管理员 店铺ID：" + shopId);
		}
	}

	/**
	 * 根据公司ID和店铺ID查询店铺
	 * 
	 * @param companyId
	 * @param shopId
	 * @return
	 */
	@Override
	public ShopInfo findShopByShopIdAndCompanyId(Integer companyId, Integer shopId) {
		return this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
	}

	/**
	 * 检查shopId
	 * 
	 * @param shopId
	 * @return 当前店铺或者公司的第一个店铺
	 */
	@Override
	public ShopInfo verifyShopId(Integer shopId) {
		// 无店铺
		if (shopId == null) {
			List<ShopInfo> shopList = this.findCompanyShop(ShopUtil.getCompanyId());
			// 如果没有shopId 默认选择第一个店铺
			if (shopList.size() > 0) {
				return shopList.get(0);
			} else {
				throw new RuntimeException("您没有店铺,请新建店铺");
			}
		} else {
			// 有店铺
			ShopInfo shop = this.findShopByShopIdAndCompanyId(ShopUtil.getCompanyId(), shopId);
			if (shop == null) {
				throw new RuntimeException("店铺信息异常，请联系管理员");
			}
			return shop;
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
}
