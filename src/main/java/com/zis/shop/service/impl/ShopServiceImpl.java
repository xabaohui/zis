package com.zis.shop.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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
import com.zis.common.util.TextClearUtils;
import com.zis.shop.api.ApiTransfer;
import com.zis.shop.api.impl.ApiTransferFactory;
import com.zis.shop.bean.Company;
import com.zis.shop.bean.DownloadItemLog;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingSystemStatus;
import com.zis.shop.bo.impl.ShopBoImpl;
import com.zis.shop.dto.ApiQueryItemsDto;
import com.zis.shop.dto.SaveOrUpdateCompanyDto;
import com.zis.shop.dto.SaveOrUpdateShopDto;
import com.zis.shop.dto.ShopDownloadInterfaceDto;
import com.zis.shop.dto.ShopDownloadJiShiBaoDto;
import com.zis.shop.dto.ShopDownloadYouZanDto;
import com.zis.shop.dto.TaoBaoImportExcelDto;
import com.zis.shop.repository.CompanyDao;
import com.zis.shop.repository.DownloadItemLogDao;
import com.zis.shop.repository.ShopInfoDao;
import com.zis.shop.repository.ShopItemMappingDao;
import com.zis.shop.util.ShopUtil;

@Service
public class ShopServiceImpl {

	private final String MAPPING_SUCCESS = ShopItemMappingSystemStatus.SUCCESS.getValue();
	private final String MAPPING_FAIL = ShopItemMappingSystemStatus.FAIL.getValue();
	private final String MAPPING_WAIT = ShopItemMappingSystemStatus.WAIT.getValue();
	private final String MAPPING_WAIT_DOWNLOAD = ShopItemMappingSystemStatus.WAIT_DOWNLOAD.getValue();
	private final String MAPPING_DELETE = ShopItemMappingSystemStatus.DELETE.getValue();
	private final String MAPPING_PROCESSING = ShopItemMappingSystemStatus.PROCESSING.getValue();
	private final String[] shopItemMappingStatus = { MAPPING_SUCCESS, MAPPING_FAIL, MAPPING_WAIT,
			MAPPING_WAIT_DOWNLOAD, MAPPING_DELETE, MAPPING_PROCESSING };

	final String NORMAL = "normal";
	final String DELETE = "delete";
	final Long PAGE_SIZE = 50L;
	final String FOR_SHELVED = "for_shelved";// 下架
	final String SOLD_OUT = "sold_out";// 售罄

	private static Logger logger = Logger.getLogger(ShopServiceImpl.class);

	@Autowired
	private ShopInfoDao shopInfoDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ShopBoImpl ShopBoImpl;

	@Autowired
	private ShopItemMappingDao shopItemMappingDao;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private ApiTransferFactory factory;

	@Autowired
	private DownloadItemLogDao downloadItemLogDao;

	@Autowired
	private BookService bookService;

	/**
	 * 库存变动更新商品（单个更新）
	 * 
	 * @param shop
	 * @param bookId
	 * @param amount
	 */
	// TODO 库存变动使用
	public void stockChange2ShopUPdateItem(ShopInfo shop, Integer bookId, Integer amount) {
		ShopItemMapping mapping = this.shopItemMappingDao.findByShopIdAndBookId(shop.getShopId(), bookId);
		if (mapping != null) {
			this.ShopBoImpl.stockChange2UpdateOrAddItems(mapping, shop, amount);
		} else {
			Bookinfo book = this.bookService.findNormalBookById(bookId);
			saveShopMappingStatusWait(book, shop);
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
	public List<String> getStatusList() {
		List<String> list = Arrays.asList(shopItemMappingStatus);
		return list;
	}

	/**
	 * 获取所有店铺
	 * 
	 * @return
	 */
	// TODO 库存初始化数据使用
	public List<ShopInfo> findAllShop() {
		return (List<ShopInfo>) this.shopInfoDao.findAll();
	}

	/**
	 * 初始化店铺数据
	 * 
	 * @param bookId
	 * @param shop
	 */
	// TODO 库存初始化数据使用
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
	@Transactional
	public Integer addItem2Shop(ShopInfo shop) {
		Integer amount = this.shopItemMappingDao.updateShopStatusWaitToProcessing(shop.getShopId());
		abstractAddAllProcessingItems(shop);
		return amount;
	}

	/**
	 * 批量发布商品
	 * 
	 * @param shop
	 */
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
	 * 发布单个商品
	 * 
	 * @param mappingId
	 * @param shop
	 * @return
	 */
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
	 * 异步发布全部商品
	 * 
	 * @param shop
	 */
	private void abstractAddAllProcessingItems(ShopInfo shop) {
		this.ShopBoImpl.abstractAddAllProcessingItems(shop);
	}

	/**
	 * 发布单个或批量
	 * 
	 * @param shop
	 */
	private void addProcessingItems(List<Integer> mappingIds, ShopInfo shop) {
		this.ShopBoImpl.addProcessingItems(mappingIds, shop);
	}

	/**
	 * 异步下载有赞店铺信息，进行更新
	 * 
	 * @param shop
	 */
	public void asynchronousDownloadYouZan2ShopItemMapping(final ShopInfo shop) {
		final ApiTransfer api = factory.getInstance(shop.getpName());
		Thread task = new Thread(new Runnable() {
			public void run() {
				queryItemsOnsale(api, shop);
				queryItemsInventory(api, shop, FOR_SHELVED);
				queryItemsInventory(api, shop, SOLD_OUT);
			}
		});
		taskExecutor.execute(task);
	}

	/**
	 * 查询所有在售商品 下载
	 * 
	 * @param api
	 * @param shop
	 */
	private void queryItemsOnsale(ApiTransfer api, ShopInfo shop) {
		Long page = 1L;
		Long totalResults = null;
		do {
			ApiQueryItemsDto dto = null;
			try {
				dto = api.queryItemsOnsale(shop, page);
			} catch (Exception e) {
				addFailDownLoadLog(shop.getShopId(), e.getMessage());
				continue;
			}
			ShopDownloadYouZanDto youZanDto = new ShopDownloadYouZanDto();
			youZanDto.setApiQueryItemsDto(dto);
			this.ShopBoImpl.downloadItems2Mapping(youZanDto, shop);
			totalResults = dto.getTotalResults();
			page++;
		} while (hasNextPage(page, totalResults));
	}

	/**
	 * 查询所有下架或售罄商品 下载
	 * 
	 * @param api
	 * @param shop
	 */
	public void queryItemsInventory(ApiTransfer api, ShopInfo shop, String type) {
		Long page = 1L;
		Long totalResults = null;
		do {
			ApiQueryItemsDto dto = null;
			try {
				dto = api.queryItemsInventory(shop, type, page);
			} catch (Exception e) {
				addFailDownLoadLog(shop.getShopId(), e.getMessage());
				continue;
			}
			ShopDownloadYouZanDto youZanDto = new ShopDownloadYouZanDto();
			youZanDto.setApiQueryItemsDto(dto);
			this.ShopBoImpl.downloadItems2Mapping(youZanDto, shop);
			totalResults = dto.getTotalResults();
			page++;
		} while (hasNextPage(page, totalResults));
	}

	/**
	 * 判断是否 有下一页
	 * 
	 * @param page
	 * @param totalResults
	 * @return
	 */
	private boolean hasNextPage(Long page, Long totalResults) {
		if (page * PAGE_SIZE <= totalResults) {
			return true;
		} else {
			Long max = page * PAGE_SIZE;
			Long result = max - totalResults;
			if (result < PAGE_SIZE) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 异步下载淘宝数据
	 * 
	 * @param input
	 * @param shop
	 */
	public String asynchronousDownloadTaoBao2ShopItemMapping(InputStream input, final ShopInfo shop) {
		byte[] by = null;
		try {
			by = IOUtils.toByteArray(input);
		} catch (IOException e) {
			return "inputStream 转换数组失败";
		}
		ByteArrayInputStream checkInput = new ByteArrayInputStream(by);
		final ByteArrayInputStream in = new ByteArrayInputStream(by);
		String msg = checkInputStream(checkInput);
		// 有返回值说明检查出错
		if (StringUtils.isNotBlank(msg)) {
			return msg;
		}
		Thread task = new Thread(new Runnable() {
			public void run() {
				List<ShopDownloadInterfaceDto> list = new ArrayList<ShopDownloadInterfaceDto>();
				ShopDownloadJiShiBaoDto dto = new ShopDownloadJiShiBaoDto();
				dto.setInputStream(in);
				list.add(dto);
				downloadItems2Mapping(dto, shop);
			}
		});
		taskExecutor.execute(task);
		return null;
	}

	private void downloadItems2Mapping(ShopDownloadInterfaceDto dto, ShopInfo shop) {
		this.ShopBoImpl.downloadItems2Mapping(dto, shop);
	}

	/**
	 * 检查淘宝上传文件是否符合规范
	 * 
	 * @param input
	 * @param shop
	 * @return
	 */
	private String checkInputStream(InputStream input) {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 1;
		try {
			// 初始化导入器
			FileImporter<TaoBaoImportExcelDto> im = new ExcelImporter<TaoBaoImportExcelDto>(input, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				return "导入文件非法";
			}
			String subCheck = subCheckFileFormat(im.loadFactHeader());
			if (StringUtils.isNotBlank(subCheck)) {
				return subCheck;
			}

			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			TaoBaoImportExcelDto instance = new TaoBaoImportExcelDto();
			List<TaoBaoImportExcelDto> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				return "导入失败，文件为空";
			}
			return null;
		} catch (Exception e) {
			return e.getMessage();
		}
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
	public Company findCompanyOne(Integer companyId) {
		return this.companyDao.findByCompanyId(companyId);
	}

	/**
	 * 新增公司
	 * 
	 * @param dto
	 */
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
	public List<ShopInfo> findCompanyShop(Integer companyId) {
		List<ShopInfo> list = this.shopInfoDao.findByCompanyId(companyId);
		return list;
	}

	/**
	 * 新增店铺
	 * 
	 * @param dto
	 */
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
	public ShopInfo findShopByShopIdAndCompanyId(Integer companyId, Integer shopId) {
		return this.shopInfoDao.findByCompanyIdAndShopId(companyId, shopId);
	}

	/**
	 * 检查shopId
	 * 
	 * @param shopId
	 * @return 当前店铺或者公司的第一个店铺
	 */
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
