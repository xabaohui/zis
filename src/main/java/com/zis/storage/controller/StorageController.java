package com.zis.storage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.controllertemplate.ViewTips;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.mvc.ext.Token;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.shop.util.ShopUtil;
import com.zis.storage.dto.CreateOrderDTO.CreateOrderDetail;
import com.zis.storage.dto.OrderDetailDto;
import com.zis.storage.dto.StockDTO;
import com.zis.storage.dto.StorageOrderDto;
import com.zis.storage.dto.StorageProductViewDTO;
import com.zis.storage.entity.StorageIoDetail;
import com.zis.storage.entity.StorageOrder;
import com.zis.storage.entity.StorageOrder.TradeStatus;
import com.zis.storage.entity.StorageProduct;
import com.zis.storage.entity.StorageRepoInfo;
import com.zis.storage.repository.StorageOrderDao;
import com.zis.storage.repository.StorageRepoInfoDao;
import com.zis.storage.service.StorageService;
import com.zis.storage.util.StorageUtil;

@Controller
@RequestMapping(value = "/storage")
public class StorageController implements ViewTips{

	@Autowired
	private StorageRepoInfoDao storageRepoInfoDao;

	@Autowired
	private StorageOrderDao storageOrderDao;
	
	@Autowired
	private BookService bookService;

	@Autowired
	private StorageService storageService;
	
	private final Integer DEFAULT_SIZE = 100;

	private final String[] allStatus = { TradeStatus.CREATED.getValue(), TradeStatus.PROCESSING.getValue(),
			TradeStatus.CANCEL.getValue(), TradeStatus.SENT.getValue() };

	/**
	 * 扫描入库跳转
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/gotoInwarehouse")
	public String gotoInwarehouse(ModelMap map) {
		return "storage/inwarehouse/inwarehouse";
	}

	/**
	 * 快速入库跳转
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/gotoFastInwarehouse")
	public String gotoFastInwarehouse(ModelMap map) {
		return "storage/inwarehouse/fast-inwarehouse";
	}

	/**
	 * 仓库订单查询
	 * 
	 * @param map
	 * @param outTradeNo
	 *            外部订单编号
	 * @param buyerName
	 *            收件人
	 * @param status
	 *            订单状态
	 * @return
	 */
	@RequestMapping(value = "/queryStorageOrder")
	public String queryStorageOrder(HttpServletRequest request, ModelMap map, String outTradeNo, String buyerName,
			String status) {
		List<String> sList = Arrays.asList(allStatus);
		if (!sList.contains(status)) {
			map.put("actionError", "错误的订单状态");
			return "error";
		}
		//创建分页查询
		pageInfo(request, map, outTradeNo, buyerName, status);
		map.put("sendStatus", status);
		return "";
	}

	/**
	 * 辅助跳转新增仓库
	 * 
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = "shiro:shiro")
	@RequestMapping(value = "/gotoSaveStorageRepoInfo")
	@Token(generate = true)
	public String gotoSaveStorageRepoInfo(ModelMap map, Integer companyId) {
		if (companyId == null) {
			map.put("actionError", "公司Id有误");
			return "error";
		}
		map.put("companyId", companyId);
		return "storage/save/save-stock";
	}

	/**
	 * 新增仓库
	 * 
	 * @param map
	 * @param stockName
	 * @return
	 */
	@RequiresPermissions(value = "shiro:shiro")
	@RequestMapping(value = "/saveStorageRepoInfo")
	@Token(checking = true)
	public String saveStorageRepoInfo(ModelMap map, String stockName, Integer companyId) {
		map.put("companyId", companyId);
		if (companyId == null) {
			map.put("actionError", "公司Id有误");
			return "error";
		}
		if (StringUtils.isBlank(stockName)) {
			map.put("errorName", "仓库名称不能为空");
			return "storage/save/save-stock";
		}
		saveStorageRepoInfo(stockName, companyId);
		map.put("actionMessage", "仓库创建成功");
		return "forward:/shop/showCompanys";
	}

	/**
	 * 新增仓库
	 * 
	 * @param stockName
	 * @param companyId
	 */
	private void saveStorageRepoInfo(String stockName, Integer companyId) {
		StorageRepoInfo info = new StorageRepoInfo();
		info.setName(stockName);
		info.setOwnerId(companyId);
		info.setStatus(StorageRepoInfo.Status.AVAILABLE.getValue());
		info.setGmtCreate(new Date());
		info.setGmtModify(new Date());
		this.storageRepoInfoDao.save(info);
	}

	/**
	 * 辅助跳转更新仓库
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/gotoUpdateStorageRepoInfo")
	@Token(generate = true)
	public String gotoUpdateStorageRepoInfo(ModelMap map, Integer repoId) {
		StorageRepoInfo info = this.storageRepoInfoDao.findOne(repoId);
		if (info == null) {
			map.put("actionError", "请勿修改页面源代码");
			return "error";
		}
		map.put("repoId", repoId);
		return "storage/update/update-stock";
	}

	/**
	 * 更新仓库
	 * 
	 * @param map
	 * @param stockName
	 * @return
	 */
	@RequestMapping(value = "/updateStorageRepoInfo")
	@Token(checking = true)
	public String updateStorageRepoInfo(ModelMap map, String stockName, Integer repoId) {
		map.put("repoId", repoId);
		map.put("stockName", stockName);
		if (StringUtils.isBlank(stockName)) {
			map.put("errorName", "仓库名称不能为空");
			return "storage/update/update-stock";
		}
		boolean result = updateStorageRepoInfo(stockName, repoId);
		if (result) {
			map.put("actionMessage", "仓库更新成功");
			return "forward:/shop/showCompanyInfo";
		} else {
			map.put("actionError", "请勿修改页面源代码");
			return "error";
		}
	}

	/**
	 * 更新仓库
	 * 
	 * @param stockName
	 * @param repoId
	 * @return
	 */
	private boolean updateStorageRepoInfo(String stockName, Integer repoId) {
		List<StorageRepoInfo> list = this.storageRepoInfoDao.findByOwnerIdAndRepoIdOrderByGmtCreateAsc(
				ShopUtil.getCompanyId(), repoId);
		if (list.isEmpty()) {
			return false;
		}
		StorageRepoInfo info = list.get(0);
		info.setName(stockName);
		info.setStatus(StorageRepoInfo.Status.AVAILABLE.getValue());
		info.setGmtModify(new Date());
		this.storageRepoInfoDao.save(info);
		return true;
	}

	/**
	 * 分页查询
	 * 
	 * @param request
	 * @param map
	 * @param shopId
	 * @param status
	 * @param isbn
	 */
	private void pageInfo(HttpServletRequest request, ModelMap map, String outTradeNo, String buyerName, String status) {
		Pageable page = WebHelper.buildPageRequest(request);
		//如果状态为空默认选择新创建状态
		if (StringUtils.isBlank(status)) {
			status = StorageOrder.TradeStatus.CREATED.getValue();
		}
		Specification<StorageOrder> spec = buildSpec(status, outTradeNo, buyerName);
		Page<StorageOrder> orderList = this.storageOrderDao.findAll(spec, page);
		if (!orderList.getContent().isEmpty()) {
			List<StorageOrder> list = orderList.getContent();
			List<StorageOrderDto> oList = buildOList(list);
			map.put("orderList", oList);
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage(outTradeNo, buyerName, status, map);
			if (orderList.hasPrevious()) {
				map.put("prePage", page.previousOrFirst().getPageNumber());
			}
			if (orderList.hasNext()) {
				map.put("nextPage", page.next().getPageNumber());
			}
		} else {
			map.put("notResult", "未找到需要的结果");
		}
	}

	/**
	 * 获取展示的list
	 * 
	 * @param list
	 * @return
	 */
	private List<StorageOrderDto> buildOList(List<StorageOrder> list) {
		List<StorageOrderDto> dList = new ArrayList<StorageOrderDto>();
		for (StorageOrder s : list) {
			StorageOrderDto dto = new StorageOrderDto();
			// 将内容转换为中文
			if (StorageOrder.TradeStatus.SENT.getValue().equals(s.getTradeStatus())) {
				s.setTradeStatus(StorageOrder.TradeStatus.SENT.getDisplay());
			} else if (StorageOrder.TradeStatus.CANCEL.getValue().equals(s.getTradeStatus())) {
				s.setTradeStatus(StorageOrder.TradeStatus.CANCEL.getDisplay());
			} else if (StorageOrder.TradeStatus.CREATED.getValue().equals(s.getTradeStatus())) {
				s.setTradeStatus(StorageOrder.TradeStatus.CREATED.getDisplay());
			} else if (StorageOrder.TradeStatus.PROCESSING.getValue().equals(s.getTradeStatus())) {
				s.setTradeStatus(StorageOrder.TradeStatus.PROCESSING.getDisplay());
			}
			dto.setStorageOrder(s);
			dto.setoList(getOrderDetailDtoList(s.getOrderDetail()));
			dList.add(dto);
		}
		return dList;
	}

	/**
	 * 获取每条订单中详细的物品数量及其名称
	 * 
	 * @param json
	 * @return
	 */
	private List<OrderDetailDto> getOrderDetailDtoList(String json) {
		List<OrderDetailDto> oList = new ArrayList<OrderDetailDto>();
		List<CreateOrderDetail> detailList = JSONObject.parseArray(json, CreateOrderDetail.class);
		for (CreateOrderDetail c : detailList) {
			OrderDetailDto dto = new OrderDetailDto();
			dto.setBookAmount(c.getAmount());
			dto.setBookTitle(buildTitle(c.getSkuId()));
			oList.add(dto);
		}
		return oList;
	}

	/**
	 * 获取显示的标题
	 * 
	 * @param bookId
	 * @return
	 */
	private String buildTitle(Integer bookId) {
		Bookinfo book = this.bookService.findBookById(bookId);
		String isbn = book.getIsbn();
		String last4Isbn = isbn.substring(isbn.length() - 4, isbn.length());
		String title = String.format("%s %s", book.getBookName(), last4Isbn);
		return title;
	}

	/**
	 * 动态组合查询条件
	 * 
	 * @param status
	 * @param outTradeNo
	 * @return
	 */
	private Specification<StorageOrder> buildSpec(String status, String outTradeNo, String buyerName) {
		QueryUtil<StorageOrder> query = new QueryUtil<StorageOrder>();
		if (StringUtils.isNotBlank(status)) {
			query.eq("status", status);
		}
		if (StringUtils.isNotBlank(buyerName)) {
			query.eq("buyerName", buyerName);
		}
		if (StringUtils.isNotBlank(outTradeNo)) {
			query.eq("outTradeNo", outTradeNo);
		}
		return query.getSpecification();
	}

	/**
	 * 上下页查询get提交组装
	 * 
	 * @param shopId
	 * @param map
	 */
	private void setQueryConditionToPage(String outTradeNo, String buyerName, String status, ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(outTradeNo)) {
			condition.append("outTradeNo=" + outTradeNo + "&");
		}
		if (StringUtils.isNotBlank(buyerName)) {
			condition.append("buyerName=" + buyerName + "&");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("status=" + status + "&");
		}
		condition.append("size=" + DEFAULT_SIZE + "&");
		map.put("queryCondition", condition.toString());
	}
	
	private static final String VIEW_URL_PRODUCT_LIST = "storage/stock/product-list";
	
	/**
	 * 条件查询商品库存
	 * @param isbn
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "stock/listProducts")
	public String queryStorageProduct(String isbn, ModelMap model) {
		if(StringUtils.isBlank(isbn)) {
			model.put(ACTION_ERROR, "isbn不能为空");
			return VIEW_URL_PRODUCT_LIST;
		}
		// 查询图书
		List<Bookinfo> books = this.bookService.findBookByISBN(isbn);
		if(CollectionUtils.isEmpty(books)) {
			model.put(NO_RESULT, "没有找到相关记录");
			return VIEW_URL_PRODUCT_LIST;
		}
		Map<Integer, StorageProductViewDTO> bookMap = new HashMap<Integer, StorageProductViewDTO>();
		for (Bookinfo book : books) {
			StorageProductViewDTO view = new StorageProductViewDTO();
			BeanUtils.copyProperties(book, view);
			bookMap.put(book.getId(), view);
		}
		// 查询库存
		List<Integer> bookIds = new ArrayList<Integer>(bookMap.keySet());
		List<StorageProduct> storageProds = this.storageService.findStorageProductBySkuIdsAndRepoId(bookIds, StorageUtil.getRepoId());
		if(CollectionUtils.isEmpty(storageProds)) {
			model.put(NO_RESULT, "没有找到相关记录");
			return VIEW_URL_PRODUCT_LIST;
		}
		// 转换结果
		for (StorageProduct prod : storageProds) {
			StorageProductViewDTO v = bookMap.get(prod.getSkuId());
			BeanUtils.copyProperties(prod, v);
		}
		model.put("storageProducts", bookMap.values());
		model.put("isbn", isbn);
		return VIEW_URL_PRODUCT_LIST;
	}
	
	private static final String VIEW_URL_STOCK_DIST = "storage/stock/stock-dist-list";
	
	/**
	 * 查询库存分布
	 * @param productId
	 * @param model
	 * @return
	 */
	@RequestMapping("stock/listStockDist")
	public String queryStoragePosStock(Integer productId, ModelMap model) {
		// 参数检验
		if(productId == null) {
			model.put(ACTION_ERROR, "未选择任何图书");
			return VIEW_URL_STOCK_DIST;
		}
		// 查询库存分布
		List<StockDTO> list = this.storageService.findAllStockByProductId(productId);
		if(CollectionUtils.isEmpty(list)) {
			model.put(ACTION_MESSAGE, "无库存记录");
			return VIEW_URL_STOCK_DIST;
		}
		Integer bookId = list.get(0).getSkuId();
		// 查询图书基本信息
		Bookinfo book = this.bookService.findBookById(bookId);
		if(book == null) {
			model.put(ACTION_MESSAGE, "无此图书");
			return VIEW_URL_STOCK_DIST;
		}
		// 数据写入页面
		model.put("list", list);
		model.put("book", book);
		return VIEW_URL_STOCK_DIST;
	}
	
	private static final String VIEW_URL_STOCK_ALTER = "storage/stock/stock-alter-list";

	@RequestMapping("stock/listStockAlter")
	public String queryStorageIoDetails(Integer productId, Integer posId, ModelMap model, HttpServletRequest request) {
		// 参数检验
		if(productId == null) {
			model.put(ACTION_ERROR, "未选择任何图书");
			return VIEW_URL_STOCK_ALTER;
		}
		// 查询库存变动
		Pageable page = WebHelper.buildPageRequest(request);
		Page<StorageIoDetail> rs = storageService.findStorageIoDetailByProductIdAndPosId(productId, posId, page);
		if(CollectionUtils.isEmpty(rs.getContent())) {
			model.put(ACTION_MESSAGE, "没有相关记录");
			return VIEW_URL_STOCK_ALTER;
		}
		// 查询图书基本信息
		Integer bookId = rs.getContent().get(0).getSkuId();
		Bookinfo book = this.bookService.findBookById(bookId);
		if (book == null) {
			model.put(ACTION_MESSAGE, "无此图书");
			return VIEW_URL_STOCK_ALTER;
		}
		// 数据写入页面
		model.put("list", rs.getContent());
		model.put("book", book);
		return VIEW_URL_STOCK_ALTER;
	}
}
