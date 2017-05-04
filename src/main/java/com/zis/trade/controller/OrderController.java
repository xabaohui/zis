package com.zis.trade.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zis.common.controllertemplate.ViewTips;
import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.common.util.ZisUtils;
import com.zis.storage.util.StorageUtil;
import com.zis.trade.dto.ExpressNumberDTO;
import com.zis.trade.dto.FillExpressNumberUploadDTO;
import com.zis.trade.dto.OrderQueryCondition;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.dto.OrderVO.OrderDetailVO;
import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;
import com.zis.trade.service.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController extends ExcelExportController<OrderVO> implements ViewTips {

	@Autowired
	private OrderService orderService;

	/**
	 * 订单列表-仓库视角-全部订单
	 * 
	 * @param cond
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getAllStorageOrderList")
	public String getAllStorageOrderList(@Valid @ModelAttribute("cond") OrderQueryCondition cond,
			HttpServletRequest request, ModelMap map) {
		if (cond == null || StringUtils.isBlank(cond.getExpressNumber())
				&& StringUtils.isBlank(cond.getOutOrderNumber()) && StringUtils.isBlank(cond.getReceiverName())
				&& StringUtils.isBlank(cond.getReceiverPhone())) {
			map.put(ACTION_ERROR, "请添加查询条件");
			return "trade/storage_show/all-storage-header";
		}
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByCondition(StorageUtil.getCompanyId(), cond, page);
			pageInfo(orderList, page, map);
			return "trade/storage_show/all-storage-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/storage_show/all-storage-header";
		}
	}

	/**
	 * 订单列表-仓库视角-等待配货
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getWaitPickUpList")
	public String getWaitPickUpList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(), null, null,
					StorageStatus.ARRANGED, page);
			pageInfo(orderList, page, map);
			return "trade/storage_show/wait-pickup-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/storage_show/wait-pickup-header";
		}
	}

	/**
	 * 订单列表-仓库视角-配货中
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getPickupList")
	public String getPickupList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(), null, null,
					StorageStatus.PICKUP, page);
			pageInfo(orderList, page, map);
			return "trade/storage_show/pickup-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/storage_show/pickup-header";
		}
	}

	/**
	 * 订单列表-仓库视角-等待打印快递单
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getWaitForPrintList")
	public String getWaitForPrintList(HttpServletRequest request, ModelMap map) {
		try {
			request.setAttribute("size", "100");
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(), null,
					ExpressStatus.WAIT_FOR_PRINT, null, page);
			pageInfo(orderList, page, map);
			return "trade/storage_show/wait-print-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/storage_show/wait-print-header";
		}
	}

	/**
	 * 订单列表-仓库视角-已打印快递单
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getPrintedList")
	public String getPrintedList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(), null,
					ExpressStatus.PRINTED, null, page);
			pageInfo(orderList, page, map);
			return "trade/storage_show/printed-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/storage_show/printed-header";
		}
	}

	/**
	 * 订单列表-店铺视角-全部订单
	 * 
	 * @param cond
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getAllShopOrderList")
	public String getAllShopOrderList(@Valid @ModelAttribute("cond") OrderQueryCondition cond,
			HttpServletRequest request, ModelMap map) {
		if (cond == null || StringUtils.isBlank(cond.getExpressNumber())
				&& StringUtils.isBlank(cond.getOutOrderNumber()) && StringUtils.isBlank(cond.getReceiverName())
				&& StringUtils.isBlank(cond.getReceiverPhone())) {
			map.put(ACTION_ERROR, "请添加查询条件");
			return "trade/shop_show/all-shop-header";
		}
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByCondition(StorageUtil.getCompanyId(), cond, page);
			pageInfo(orderList, page, map);
			return "trade/shop_show/all-shop-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/shop_show/all-shop-header";
		}
	}

	/**
	 * 订单列表-店铺视角-未支付
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getUnpaidList")
	public String getUnpaidList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(),
					PayStatus.UNPAID, null, null, page);
			pageInfo(orderList, page, map);
			return "trade/shop_show/unpaid-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/shop_show/unpaid-header";
		}
	}

	/**
	 * 订单列表-店铺视角-退款中
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getRefundingList")
	public String getRefundingList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(),
					PayStatus.REFUNDING, null, null, page);
			pageInfo(orderList, page, map);
			return "trade/shop_show/refunding-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/shop_show/refunding-header";
		}
	}

	/**
	 * 订单列表-店铺视角-未分配仓库
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/getWaitArrangeHeaderList")
	public String getWaitArrangeHeaderList(HttpServletRequest request, ModelMap map) {
		try {
			Pageable page = WebHelper.buildPageRequest(request);
			Page<OrderVO> orderList = this.orderService.findOrdersByStatus(StorageUtil.getCompanyId(), null, null,
					StorageStatus.WAIT_ARRANGE, page);
			pageInfo(orderList, page, map);
			return "trade/shop_show/wait-arrange-header";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "trade/shop_show/wait-arrange-header";
		}
	}

	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/cancelOrder")
	public String cancelOrder(Integer[] orderId, String forwardUrl, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> oIds = Arrays.asList(orderId);
			for (Integer i : oIds) {
				this.orderService.cancelOrder(i, StorageUtil.getUserId());
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 支付订单
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/payOrder")
	public String payOrder(Integer orderId, String forwardUrl, Double paymentAmount, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			this.orderService.payOrder(orderId, paymentAmount, StorageUtil.getUserId());
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 同意退款
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param memo
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/agreeRefund")
	public String agreeRefund(Integer[] orderId, String forwardUrl, String memo, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> list = Arrays.asList(orderId);
			for (Integer i : list) {
				this.orderService.agreeRefund(i, StorageUtil.getUserId(), memo);
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 取消退款
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param memo
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/cancelRefund")
	public String cancelRefund(Integer[] orderId, String forwardUrl, String memo, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> list = Arrays.asList(orderId);
			for (Integer i : list) {
				this.orderService.cancelRefund(i, StorageUtil.getUserId(), memo);
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 分配仓库
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param repoId
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/arrangeOrderToPos")
	public String arrangeOrderToPos(Integer[] orderId, String forwardUrl, Integer repoId, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> orderIds = Arrays.asList(orderId);
			this.orderService.arrangeOrderToPos(repoId, orderIds, StorageUtil.getUserId());
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 配货
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/pickingUpOrder")
	public String pickingUpOrder(Integer[] orderId, String forwardUrl, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> orderIds = Arrays.asList(orderId);
			this.orderService.arrangeOrderToPos(StorageUtil.getRepoId(), orderIds, StorageUtil.getUserId());
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 取消配货
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param memo
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/cancelArrangeOrder")
	public String cancelArrangeOrder(Integer[] orderId, String forwardUrl, String memo, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> orderIds = Arrays.asList(orderId);
			for (Integer oId : orderIds) {
				this.orderService.cancelArrangeOrder(oId, StorageUtil.getUserId(), memo);
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 缺货
	 * 
	 * @param orderId
	 * @param forwardUrl
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/lackness")
	public String lackness(Integer[] orderId, String forwardUrl, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/order/" + forwardUrl;
		}
		try {
			List<Integer> orderIds = Arrays.asList(orderId);
			for (Integer oId : orderIds) {
				this.orderService.lackness(oId, StorageUtil.getUserId());
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	/**
	 * 批量回填单号
	 * 
	 * @param excelFile
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/fillExpressNumberUpload")
	public String fillExpressNumberUpload(@RequestParam MultipartFile excelFile, ModelMap map) {
		try {
			List<FillExpressNumberUploadDTO> dtoList = buildDTOList(excelFile.getInputStream());
			List<ExpressNumberDTO> list = new ArrayList<ExpressNumberDTO>();
			for (FillExpressNumberUploadDTO d : dtoList) {
				ExpressNumberDTO dto = new ExpressNumberDTO();
				BeanUtils.copyProperties(d, dto);
				list.add(dto);
			}
			this.orderService.fillExpressNumbers(list, StorageUtil.getUserId());
			// TODO
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/order/getPrintedList";
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/getPrintedList";
		}
	}

	/**
	 * 打印快递单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/printExpress")
	public String printExpress(HttpServletRequest request, HttpServletResponse response, String forwardUrl,
			ModelMap map, Integer[] orderId) {
		if(orderId == null){
			map.put(ACTION_ERROR, "订单Id为空");
			return "forward:/order/" + forwardUrl; 
		}
		try {
			return super.export(request, response, forwardUrl, map);
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/order/" + forwardUrl;
		}
	}

	@Override
	protected String getSuccessPage(String forwardUrl) {
		return "forward:/order/" + forwardUrl;
	}

	@Override
	protected String[] getTableHeaders() {
		return new String[] { "订单编号", "收件人", "固话", "手机", "地址", "发货信息", "备注", "代收金额", "保价金额", "业务类型" };
	}

	@Override
	protected String[] getRowDatas(OrderVO vo) {
		String[] rowDatas = new String[this.getTableHeaders().length];
		rowDatas[0] = vo.getOrderId().toString();
		rowDatas[1] = vo.getReceiverName();
		rowDatas[3] = vo.getReceiverPhone();
		rowDatas[4] = vo.getReceiverAddr();
		rowDatas[5] = getOrderDetailVOToString(vo.getOrderDetails());
		rowDatas[6] = vo.getBuyerMessage();
		return rowDatas;
	}

	/**
	 * 构建String 类型的订单商品详情
	 * 
	 * @param list
	 * @return
	 */
	private String getOrderDetailVOToString(List<OrderDetailVO> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			String isbn = list.get(i).getIsbn();
			if (StringUtils.isBlank(isbn) && isbn.length() > 3) {
				isbn = isbn.substring(isbn.length() - 4, isbn.length());
			}
			String bookName = list.get(i).getBookName();
			Integer bookAmount = list.get(i).getItemCount();
			String fmt = String.format("%s %s * %s", bookName, isbn, bookAmount);
			sb.append(fmt);
			if (i != 0) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 设置导出文件名
	 * 
	 * @return
	 */
	protected String setExportFileName() {
		return "快递单打印" + ZisUtils.getDateString("yyyy-MM-dd") + ".xls";
	}

	@Override
	protected List<OrderVO> queryExportData(HttpServletRequest request) {
		String[] orderIdStr = request.getParameterValues("orderId");
		List<Integer> orderIds = new ArrayList<Integer>();
		for (String s : orderIdStr) {
			Integer orderId = Integer.parseInt(s);
			orderIds.add(orderId);
		}
		// List<OrderVO> list = this.orderService.printExpressList(orderIds,
		// StorageUtil.getUserId());
		// return list;
		return getSimulationData();
	}

	private List<OrderVO> getSimulationData() {
		List<OrderVO> list = new ArrayList<OrderVO>();
		for (int i = 0; i < 30; i++) {
			OrderVO vo = new OrderVO();
			vo.setOrderId(123123 + i);
			vo.setReceiverName("楚中天" + i);
			vo.setReceiverPhone("135222222222" + i);
			vo.setReceiverAddr("北京市中关村南" + i + "号院");
			vo.setBuyerMessage("我是你第" + i + "买家哦");
			vo.setOrderDetails(getxxx());
			list.add(vo);
		}
		return list;
	}

	private List<OrderDetailVO> getxxx() {
		List<OrderDetailVO> list = new ArrayList<OrderVO.OrderDetailVO>();
		for (int i = 0; i < 4; i++) {
			OrderDetailVO vo = new OrderDetailVO();
			vo.setIsbn("123123333" + i);
			vo.setBookName("流氓会武术第" + i + "部");
			vo.setItemCount(10+i);
			list.add(vo);
		}
		return list;
	}

	private List<FillExpressNumberUploadDTO> buildDTOList(InputStream input) {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 2;
		try {
			// 初始化导入器
			FileImporter<FillExpressNumberUploadDTO> im = new ExcelImporter<FillExpressNumberUploadDTO>(input, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				throw new RuntimeException("导入文件非法");
			}
			String subCheck = subCheckFileFormat(im.loadFactHeader());
			if (StringUtils.isNotBlank(subCheck)) {
				throw new RuntimeException(subCheck);
			}

			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			FillExpressNumberUploadDTO instance = new FillExpressNumberUploadDTO();
			List<FillExpressNumberUploadDTO> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				throw new RuntimeException("导入失败，文件为空");
			}
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 检查列开头是否符合规范
	 * 
	 * @param factHeader
	 * @return
	 */
	private String subCheckFileFormat(List<String> factHeader) {
		String outOrderNumber = "订单编号";
		String receiverName = "收件人";
		String expressCompany = "快递名称";
		String expressNumber = "快递单号";
		if (!factHeader.get(1).equals(outOrderNumber)) {
			return "格式错误，B列必须是:" + outOrderNumber;
		}
		if (!factHeader.get(6).equals(receiverName)) {
			return "格式错误，G列必须是:" + receiverName;
		}
		if (!factHeader.get(13).equals(expressCompany)) {
			return "格式错误，N列必须是:" + expressCompany;
		}
		if (!factHeader.get(14).equals(expressNumber)) {
			return "格式错误，O列必须是:" + expressNumber;
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
		map.put("orderId", 1);
		map.put("receiverName", 6);
		map.put("expressCompany", 13);
		map.put("expressNumber", 14);
		return map;
	}

	/**
	 * 分页查询
	 * 
	 * @param orderList
	 * @param page
	 * @param map
	 * @param status
	 */
	private void pageInfo(Page<OrderVO> orderList, Pageable page, ModelMap map) {
		if (!orderList.getContent().isEmpty()) {
			List<OrderVO> list = orderList.getContent();
			map.put("orderList", list);
			map.put("page", page.getPageNumber() + 1);
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
}
