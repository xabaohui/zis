package com.zis.trade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.ExpressNumberDTO;
import com.zis.trade.dto.OrderQueryCondition;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.dto.OrderVO.OrderDetailVO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.OrderType;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;

@Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public Order createOrder(CreateTradeOrderDTO orderDTO) {
		logger.info("创建订单, orderDTO={}", JSONObject.toJSONString(orderDTO));
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelOrder(Integer orderId, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("取消订单, orderId={}, operator={}", orderId, operator);
	}

	@Override
	public void cancelOrder(Integer shopId, String outOrderNumber, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("取消订单, shopId={}, outOrderNumber={}, operator={}", shopId, outOrderNumber, operator);
	}

	@Override
	public void payOrder(Integer orderId, Double paymentAmount, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("支付订单, orderId={}, paymentAmount={}, operator={}", orderId, paymentAmount, operator);
	}

	@Override
	public void payOrder(Integer shopId, String outOrderNumber, Double paymentAmount, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("支付订单, shopId={}, outOrderNumber={}, paymentAmount={}, operator={}",
				shopId, outOrderNumber, paymentAmount, operator);
	}

	@Override
	public void applyRefund(Integer orderId, Integer operator, Date applyTime, String refundMemo) {
		// TODO Auto-generated method stub
		logger.info("申请订单退款, orderId={}, operator={}, applyTime={}, refundMemo={}",
				orderId, operator, applyTime, refundMemo);
	}

	@Override
	public void applyRefund(Integer shopId, String outOrderNumber, Integer operator, Date applyTime, String refundMemo) {
		// TODO Auto-generated method stub
		logger.info("申请订单退款, shopId={}, outOrderNumber={}, operator={}, applyTime={}, refundMemo={}",
				shopId, outOrderNumber, operator, applyTime, refundMemo);
	}

	@Override
	public void agreeRefund(Integer orderId, Integer operator, String memo) {
		// TODO Auto-generated method stub
		logger.info("同意退款, orderId={}, operator={}, memo={}", orderId, operator, memo);
	}

	@Override
	public void cancelRefund(Integer orderId, Integer operator, String memo) {
		// TODO Auto-generated method stub
		logger.info("取消退款, orderId={}, operator={}, memo={}", orderId, operator, memo);
	}

	@Override
	public void changeOrderAddress(Integer orderId, Integer operator, String newAddress) {
		// TODO Auto-generated method stub
		logger.info("修改收货地址, orderId={}, operator={}, newAddress={}", orderId, operator, newAddress);
	}

	@Override
	public void changeItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void blockOrder(Integer orderId, Integer operator, String blockReason) {
		// TODO Auto-generated method stub
		logger.info("拦截订单, orderId={}, operator={}, blockReason={}", orderId, operator, blockReason);
	}

	@Override
	public void arrangeOrderToRepo(Integer orderId, Integer operator, Integer repoId) {
		// TODO Auto-generated method stub
		logger.info("分配订单到仓库, orderId={}, operator={}, repoId={}", orderId, operator, repoId);
	}

	@Override
	public void arrangeOrderToPos(List<Integer> orderIds, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("开始配货, orderIds={}, operator={}", orderIds, operator);
	}

	@Override
	public void cancelArrangeOrder(Integer orderId, Integer operator, String memo) {
		// TODO Auto-generated method stub
		logger.info("取消分配仓库, orderId={}, operator={}, memo={}", orderId, operator, memo);
	}

	@Override
	public void finishSend(Integer batchId, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("完成配货, batchId={}, operator={}", batchId, operator);
	}

	@Override
	public void lackness(Integer orderId, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("缺货订单退出仓库, orderId={}, operator={}", orderId, operator);
	}

	@Override
	public void printExpressList(List<Integer> orderIds, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("打印快递单, orderIds={}, operator={}", orderIds, operator);
	}

	@Override
	public void fillExpressNumber(Integer orderId, String expressNumber, String expressCompany, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("回填快递单号, orderId={}, expressNumber={}, expressCompany={}, operator={}",
				orderId, expressNumber, expressCompany, operator);
	}

	@Override
	public void fillExpressNumbers(List<ExpressNumberDTO> numbers, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("回填快递单号, numbers={}, operator={}", JSONObject.toJSONString(numbers), operator);
	}

	@Override
	public void sendOut(Integer repoId, String expressNumber, Integer operator) {
		// TODO Auto-generated method stub
		logger.info("扫描出库, repoId={}, expressNumber={}, operator", repoId, expressNumber, operator);
	}

	@Override
	public Page<OrderVO> findOrdersByStatus(Integer companyId, PayStatus payStatus, ExpressStatus expressStatus,
			StorageStatus storageStatus, PageRequest page) {
		// TODO Auto-generated method stub
		List<OrderVO> rs = new ArrayList<OrderVO>();
		OrderVO blockVO = buildOrderVO(ExpressStatus.PRINTED, PayStatus.UNPAID, StorageStatus.ARRANGED);
		blockVO.setBlockFlag(true);
		blockVO.setBlockReason("买家不要了，请勿发货");
		rs.add(blockVO);
		
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.UNPAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.PAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUNDING, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUND_FINISH, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.CANCELLED, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.UNPAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.PAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUNDING, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUND_FINISH, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.CANCELLED, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.UNPAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.PAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUNDING, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.REFUND_FINISH, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.WAIT_FOR_PRINT, PayStatus.CANCELLED, StorageStatus.PICKUP_FINISH));
		
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.UNPAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.PAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUNDING, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUND_FINISH, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.CANCELLED, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.UNPAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.PAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUNDING, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUND_FINISH, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.CANCELLED, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.UNPAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.PAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUNDING, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.REFUND_FINISH, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.PRINTED, PayStatus.CANCELLED, StorageStatus.PICKUP_FINISH));
		
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.UNPAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.PAID, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUNDING, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUND_FINISH, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.CANCELLED, StorageStatus.ARRANGED));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.UNPAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.PAID, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUNDING, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUND_FINISH, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.CANCELLED, StorageStatus.PICKUP));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.UNPAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.PAID, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUNDING, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.REFUND_FINISH, StorageStatus.PICKUP_FINISH));
		rs.add(buildOrderVO(ExpressStatus.FILLED_EX_NUM, PayStatus.CANCELLED, StorageStatus.PICKUP_FINISH));
		
		rs.add(buildOrderVO(ExpressStatus.SEND_OUT, PayStatus.PAID, StorageStatus.PICKUP_FINISH));
		return new PageImpl<OrderVO>(rs);
	}
	
	private OrderVO buildOrderVO(ExpressStatus ex, PayStatus pay, StorageStatus st) {
		OrderVO vo = new OrderVO();
		vo.setBuyerMessage("尽快发货哦老板");
		vo.setExpressStatus(ex.getValue());
		vo.setExpressStatusDisplay(ex.getDisplay());
		if(ExpressStatus.FILLED_EX_NUM.equals(ex) || ExpressStatus.SEND_OUT.equals(ex)) {
			vo.setExpressCompany("中通");
			vo.setExpressNumber("610313554924");
		}
		vo.setOrderDetails(buildOrderDetailVOs());
		vo.setOrderId(49213);
		vo.setOrderType(OrderType.SELF.getValue());
		List<String> outNumbers = new ArrayList<String>();
		outNumbers.add("TB313233230640981");
		outNumbers.add("TB313258780640982");
		vo.setOutOrderNumbers(outNumbers);
		vo.setPayStatus(pay.getValue());
		vo.setPayStatusDisplay(pay.getDisplay());
		vo.setReceiverName("张三");
		vo.setReceiverPhone("13810010322");
		vo.setStorageStatus(st.getValue());
		vo.setStorageStatusDisplay(st.getDisplay());
		return vo;
	}

	private List<OrderDetailVO> buildOrderDetailVOs() {
		List<OrderDetailVO> list = new ArrayList<OrderVO.OrderDetailVO>();
		list.add(buildOrderDetails(3001, "大学语文精讲精练", 3));
		list.add(buildOrderDetails(5318, "高等数学", 2));
		return list;
	}

	private OrderDetailVO buildOrderDetails(Integer bookId, String name, Integer count) {
		OrderDetailVO vo = new OrderDetailVO();
		vo.setBookAuthor("楚中天");
		vo.setBookEdition("第一版");
		vo.setBookId(bookId);
		vo.setBookName(name);
		vo.setIsbn("9787123456789");
		vo.setItemCount(count);
		vo.setItemId(bookId);
		vo.setSkuId(bookId);
		vo.setItemName(name);
		vo.setItemPrice(39.5);
		return vo;
	}

	@Override
	public Page<OrderVO> findOrdersByCondition(Integer companyId, OrderQueryCondition cond, PageRequest page) {
		return findOrdersByStatus(null, null, null, null, null);
	}

}
