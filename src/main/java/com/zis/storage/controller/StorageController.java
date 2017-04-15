package com.zis.storage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.mvc.ext.Token;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.shop.util.ShopUtil;
import com.zis.storage.dto.CreateOrderDTO.CreateOrderDetail;
import com.zis.storage.dto.OrderDetailDto;
import com.zis.storage.dto.StorageOrderDto;
import com.zis.storage.entity.StorageOrder;
import com.zis.storage.entity.StorageOrder.TradeStatus;
import com.zis.storage.entity.StorageRepoInfo;
import com.zis.storage.repository.StorageOrderDao;
import com.zis.storage.repository.StorageRepoInfoDao;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {

	@Autowired
	private StorageRepoInfoDao storageRepoInfoDao;

	@Autowired
	private StorageOrderDao storageOrderDao;

	@Autowired
	private BookService bookService;

	private final String[] allStatus = { TradeStatus.CREATED.getValue(), TradeStatus.PROCESSING.getValue(),
			TradeStatus.CANCEL.getValue() };

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
	 * @param addressee
	 *            收件人
	 * @param status
	 *            订单状态
	 * @return
	 */
	public String queryStorageOrder(HttpServletRequest request, ModelMap map, String outTradeNo, String addressee,
			String status) {
		List<String> sList = Arrays.asList(allStatus);
		if (!sList.contains(status)) {
			map.put("actionError", "错误的订单状态");
			return "error";
		}
		pageInfo(request, map, outTradeNo, addressee, status);
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
	private void pageInfo(HttpServletRequest request, ModelMap map, String outTradeNo, String addressee, String status) {
		Pageable page = WebHelper.buildPageRequest(request);
		Specification<StorageOrder> spec = null;
		if (StringUtils.isNotBlank(addressee)) {
			spec = buildSpec(status, outTradeNo);// FIXME 暂时不定义,待订单系统开发好后注入
			// TODO 收件人按照收件人方式查询 将订单list放入查询包含此订单号关联查询，如果订单号为空全查询，不包含返回无结果
		} else {
			spec = buildSpec(status, outTradeNo);
		}
		Page<StorageOrder> orderList = this.storageOrderDao.findAll(spec, page);
		if (!orderList.getContent().isEmpty()) {
			List<StorageOrder> list = orderList.getContent();
			List<StorageOrderDto> oList = buildOList(list);
			map.put("orderList", oList);
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage(outTradeNo, addressee, status, map);
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
	private Specification<StorageOrder> buildSpec(String status, String... outTradeNo) {
		QueryUtil<StorageOrder> query = new QueryUtil<StorageOrder>();
		if (StringUtils.isNoneBlank(outTradeNo)) {
			if (outTradeNo.length == 1) {
				query.eq("outTradeNo", outTradeNo[0]);
			} else {
				query.in("outTradeNo", (Object[]) outTradeNo);
			}
		}
		if (!StringUtils.isBlank(status)) {
			query.eq("outTradeNo", outTradeNo[0]);
		}
		return query.getSpecification();
	}

	/**
	 * 上下页查询get提交组装
	 * 
	 * @param shopId
	 * @param map
	 */
	private void setQueryConditionToPage(String outTradeNo, String addressee, String status, ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(outTradeNo)) {
			condition.append("outTradeNo=" + outTradeNo + "&");
		}
		if (StringUtils.isNotBlank(addressee)) {
			condition.append("addressee=" + addressee + "&");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("status=" + status + "&");
		}
		map.put("queryCondition", condition.toString());
	}
}
