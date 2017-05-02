package com.zis.trade.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.controllertemplate.ViewTips;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.storage.util.StorageUtil;
import com.zis.trade.dto.OrderQueryCondition;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;
import com.zis.trade.service.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController implements ViewTips {

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
			return "forward:/trade/" + forwardUrl;
		}
		try {
			List<Integer> oIds = Arrays.asList(orderId);
			for (Integer i : oIds) {
				this.orderService.cancelOrder(i, StorageUtil.getUserId());
			}
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/trade/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/trade/" + forwardUrl;
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
			return "forward:/trade/" + forwardUrl;
		}
		try {
			this.orderService.payOrder(orderId, paymentAmount, StorageUtil.getUserId());
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/trade/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/trade/" + forwardUrl;
		}
	}
	
	@RequestMapping(value = "/applyRefund")
	public String applyRefund(Integer orderId, String forwardUrl, String refundMemo, ModelMap map) {
		if (orderId == null) {
			map.put(ACTION_ERROR, "请选择订单");
			return "forward:/trade/" + forwardUrl;
		}
		try {
			this.orderService.applyRefund(orderId, StorageUtil.getUserId(), new Date(), refundMemo);
			map.put(ACTION_MESSAGE, "操作成功");
			return "forward:/trade/" + forwardUrl;
		} catch (Exception e) {
			map.put(ACTION_ERROR, e.getMessage());
			return "forward:/trade/" + forwardUrl;
		}
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
