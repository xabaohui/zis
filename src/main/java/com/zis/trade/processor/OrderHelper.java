package com.zis.trade.processor;

import org.apache.commons.lang3.StringUtils;

import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;

/**
 * 订单工具类
 * 
 * @author yz
 * 
 */
public class OrderHelper {

	/**
	 * 能否取消订单
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canCancelOrder(Order order) {
		checkOrder(order);
		// 物流状态：未出库
		if (expressStatusIsSendOut(order)) {
			return false;
		}
		// 资金状态：未支付
		return payStatusIsUnpaid(order);
	}

	/**
	 * 能否支付
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canPay(Order order) {
		checkOrder(order);
		// 资金状态：未支付
		return payStatusIsUnpaid(order);
	}

	/**
	 * 能否申请退款
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canApplyRefund(Order order) {
		checkOrder(order);
		// 物流状态：未出库
		if (expressStatusIsSendOut(order)) {
			return false;
		}
		// 资金状态：已支付
		return payStatusIsPaid(order);
	}

	/**
	 * 能否同意退款
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canAgreeRefund(Order order) {
		checkOrder(order);
		// 物流状态：未出库
		if (expressStatusIsSendOut(order)) {
			return false;
		}
		// 资金状态：退款中
		return payStatusIsRefunding(order);
	}

	/**
	 * 能否取消退款
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canCancelRefund(Order order) {
		return canAgreeRefund(order);
	}

	/**
	 * 能否修改地址
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canChangeOrderAddress(Order order) {
		checkOrder(order);
		// 物流状态：未打印
		if (!expressStatusIsWaitForPrint(order)) {
			return false;
		}
		// 资金状态：未支付、已支付、退款中
		return !payStatusIsClosed(order);
	}

	/**
	 * 能否改商品
	 * 
	 * @param order
	 * @return
	 */
	public static boolean canChangeItems(Order order) {
		checkOrder(order);
		// 配货状态：未分配、已分配
		if (!storageStatusIsWaitForPickup(order)) {
			return false;
		}
		// 物流状态：未打印
		if (!expressStatusIsWaitForPrint(order)) {
			return false;
		}
		// 资金状态：未支付、已支付、退款中
		return !payStatusIsClosed(order);
	}

	/**
	 * 能否合并订单
	 * @param existOrder 已存在订单
	 * @param newOrderPayStatus 新订单资金状态
	 * @return
	 */
	public static boolean canCombined(Order existOrder, String newOrderPayStatus) {
		checkOrder(existOrder);
		if(!PayStatus.isDefined(newOrderPayStatus)) {
			throw new IllegalArgumentException("资金状态非法：" + newOrderPayStatus);
		}
		// 配货状态：未分配、已分配
		if (!storageStatusIsWaitForPickup(existOrder)) {
			return false;
		}
		// 物流状态：未打印
		if (!expressStatusIsWaitForPrint(existOrder)) {
			return false;
		}
		// 资金状态：新老订单均为未支付
		if (payStatusIsUnpaid(existOrder) && existOrder.getPayStatus().equals(newOrderPayStatus)) {
			return true;
		}
		// 资金状态：新老订单均为已支付
		if (payStatusIsPaid(existOrder) && existOrder.getPayStatus().equals(newOrderPayStatus)) {
			return true;
		}
		// 其他资金状态或新老订单状态不一致，均不允许合并
		return false;
	}
	
	/**
	 * 能否拆分订单
	 * @param order
	 * @return
	 */
	public static boolean canSplit(Order order) {
		checkOrder(order);
		// 配货状态：未分配
		if (!storageStatusIsWaitForArrange(order)) {
			return false;
		}
		// 物流状态：未打印
		if (!expressStatusIsWaitForPrint(order)) {
			return false;
		}
		// 资金状态：未支付、已支付
		return payStatusIsPaid(order) || payStatusIsUnpaid(order);
	}
	
	/**
	 * 能否分配仓库
	 * @param order
	 * @return
	 */
	public static boolean canArrangeOrderToRepo(Order order) {
		checkOrder(order);
		// 配货状态：未分配
		if (!storageStatusIsWaitForArrange(order)) {
			return false;
		}
		// 物流状态：未打印、已打印、已填单
		if (expressStatusIsSendOut(order)) {
			return false;
		}
		// 资金状态：未支付、已支付
		return payStatusIsPaid(order) || payStatusIsUnpaid(order);
	}
	
	/**
	 * 能否开始配货
	 * @return
	 */
	public static boolean canArrangeOrderToPos(Order order) {
		checkOrder(order);
		// 配货状态：已分配
		if (!storageStatusIsArranged(order)) {
			return false;
		}
		// 资金状态未支付、已支付的情况下，支持未打印、已打印、已填单等物流状态
		if (payStatusIsPaid(order) || payStatusIsUnpaid(order)) {
			return !expressStatusIsSendOut(order);
		}
		// 资金状态退款中、订单关闭（退款、取消）的情况下，支持已打印、已填单（未打印不允许配货）
		return expressStatusIsPrinted(order) || expressStatusIsFilledExNumber(order);
	}
	
	/**
	 * 能否取消配货
	 * @param order
	 * @return
	 */
	public static boolean canCancelArrangeOrder(Order order) {
		checkOrder(order);
		// 配货状态：已分配
		if (!storageStatusIsArranged(order)) {
			return false;
		}
		// 物流状态：未打印
		return expressStatusIsWaitForPrint(order);
	}
	
	/**
	 * 能否完成配货
	 * @param order
	 * @return
	 */
	public static boolean canFinishSend(Order order) {
		checkOrder(order);
		// 配货状态：配货中
		return storageStatusIsPickup(order);
	}
	
	/**
	 * 能否标记缺货
	 * @param order
	 * @return
	 */
	public static boolean canLackness(Order order) {
		checkOrder(order);
		// 配货状态：配货中
		if(!storageStatusIsPickup(order)) {
			return false;
		}
		// 物流状态：未打印、已打印、已填单
		return !expressStatusIsSendOut(order);
	}
	
	/**
	 * 能否打印快递单
	 * @param order
	 * @return
	 */
	public static boolean canPrint(Order order) {
		checkOrder(order);
		// 物流状态：未打印
		if(!expressStatusIsWaitForPrint(order)) {
			return false;
		}
		// 资金状态未支付、已支付的情况下，支持已分配，配货中，配货完成等配货状态
		if (payStatusIsPaid(order) || payStatusIsUnpaid(order)) {
			return !storageStatusIsWaitForArrange(order);
		}
		// 资金状态退款中、订单关闭（退款、取消）的情况下，支持配货中、配货完成等配货状态
		return storageStatusIsPickup(order) || storageStatusIsPickupFinish(order);
	}
	
	/**
	 * 能否回填快递单号
	 * @param order
	 * @return
	 */
	public static boolean canFillExpressNumber(Order order) {
		checkOrder(order);
		// 物流状态：已打印
		if(!expressStatusIsPrinted(order)) {
			return false;
		}
		// 资金状态：未支付、已支付、退款中
		return !payStatusIsClosed(order);
	}
	
	/**
	 * 能否出库
	 * @param order
	 * @return
	 */
	public static boolean canSendOut(Order order) {
		checkOrder(order);
		// 配货状态：配货完成
		if(!storageStatusIsPickupFinish(order)) {
			return false;
		}
		// 物流状态：已填单
		if(!expressStatusIsFilledExNumber(order)) {
			return false;
		}
		// 资金状态：已支付
		if(!payStatusIsPaid(order)) {
			return false;
		}
		return !order.isBlockFlag();
	}
	
	/**
	 * 能否出库
	 * @param order
	 * @return 能出库返回null，不能出库返回原因
	 */
	public static String canSendOutWithMessage(Order order) {
		checkOrder(order);
		// 配货状态：配货完成
		if(!storageStatusIsPickupFinish(order)) {
			return "未完成配货";
		}
		// 物流状态：已填单
		if(!expressStatusIsFilledExNumber(order)) {
			return "未填写单号";
		}
		// 资金状态：已支付
		if(!payStatusIsPaid(order)) {
			return "未完成支付";
		}
		return order.getBlockReason();
	}
	
	/**
	 * 能否拦截
	 * @param order
	 * @return
	 */
	public static boolean canBlock(Order order) {
		checkOrder(order);
		// 物流状态：未出库
		return !expressStatusIsSendOut(order);
	}

	private static void checkOrder(Order order) {
		if (order == null) {
			throw new IllegalArgumentException("order不能为空");
		}
		if (StringUtils.isBlank(order.getExpressStatus())) {
			throw new IllegalArgumentException("ExpressStatus不能为空");
		}
		if (StringUtils.isBlank(order.getPayStatus())) {
			throw new IllegalArgumentException("PayStatus不能为空");
		}
		if (StringUtils.isBlank(order.getStorageStatus())) {
			throw new IllegalArgumentException("StorageStatus不能为空");
		}
	}

	private static boolean payStatusIsPaid(Order order) {
		return PayStatus.PAID.getValue().equals(order.getPayStatus());
	}
	
	private static boolean payStatusIsUnpaid(Order order) {
		return PayStatus.UNPAID.getValue().equals(order.getPayStatus());
	}

	private static boolean payStatusIsRefunding(Order order) {
		return PayStatus.REFUNDING.getValue().equals(order.getPayStatus());
	}

	private static boolean payStatusIsClosed(Order order) {
		return PayStatus.REFUND_FINISH.getValue().equals(order.getPayStatus())
				|| PayStatus.CANCELLED.getValue().equals(order.getPayStatus());
	}

	private static boolean expressStatusIsWaitForPrint(Order order) {
		return ExpressStatus.WAIT_FOR_PRINT.getValue().equals(order.getExpressStatus());
	}
	
	private static boolean expressStatusIsPrinted(Order order) {
		return ExpressStatus.PRINTED.getValue().equals(order.getExpressStatus());
	}

	private static boolean expressStatusIsFilledExNumber(Order order) {
		return ExpressStatus.FILLED_EX_NUM.getValue().equals(order.getExpressStatus());
	}

	private static boolean expressStatusIsSendOut(Order order) {
		return ExpressStatus.SEND_OUT.getValue().equals(order.getExpressStatus());
	}

	private static boolean storageStatusIsWaitForArrange(Order order) {
		StorageStatus st = StorageStatus.getEnum(order.getStorageStatus());
		switch (st) {
		case WAIT_ARRANGE:
		case WAIT_ARRANGE_BY_LACKNESS:
		case WAIT_ARRANGE_BY_MANUAL:
			return true;
		default:
			return false;
		}
	}
	
	private static boolean storageStatusIsWaitForPickup(Order order) {
		StorageStatus st = StorageStatus.getEnum(order.getStorageStatus());
		switch (st) {
		case WAIT_ARRANGE:
		case WAIT_ARRANGE_BY_LACKNESS:
		case WAIT_ARRANGE_BY_MANUAL:
		case ARRANGED:
			return true;
		case PICKUP:
		case PICKUP_FINISH:
			return false;
		default:
			throw new RuntimeException("不支持的配货状态:" + st);
		}
	}
	
	private static boolean storageStatusIsArranged(Order order) {
		return StorageStatus.ARRANGED.getValue().equals(order.getStorageStatus());
	}
	
	private static boolean storageStatusIsPickup(Order order) {
		return StorageStatus.PICKUP.getValue().equals(order.getStorageStatus());
	}
	
	private static boolean storageStatusIsPickupFinish(Order order) {
		return StorageStatus.PICKUP_FINISH.getValue().equals(order.getStorageStatus());
	}
}
