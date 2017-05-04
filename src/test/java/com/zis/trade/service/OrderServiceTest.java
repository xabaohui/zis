package com.zis.trade.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageIoDetail;
import com.zis.storage.service.StorageService;
import com.zis.trade.dto.ChangeAddressDTO;
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.CreateTradeOrderDTO.SubOrder;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.OrderType;


public class OrderServiceTest extends BaseTestUnit {

	@Autowired
	OrderService service;
	@Autowired
	StorageService storageService;
	
	@Test
	public void testCreateOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("蕾蕾-未支付");
		service.createOrder(orderDTO);
	}
	
	@Test
	public void testPayOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-支付");
		Order order = service.createOrder(orderDTO);
		
		service.payOrder(order.getOrderId(), 39.2, 1001);
	}

	@Test
	public void testCancelOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-取消");
		Order order = service.createOrder(orderDTO);
		
		service.cancelOrder(order.getOrderId(), 1001);
	}
	
	@Test
	public void testApplyRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getOrderId(), 39.2, 1001);
		
		service.applyRefund(order.getOrderId(), 8311, new Date(), "买家不想要了");
	}
	
	@Test
	public void testAgreeRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getOrderId(), 39.2, 1001);
		service.applyRefund(order.getOrderId(), 8311, new Date(), "买家不想要了");
		
		service.agreeRefund(order.getOrderId(), 1124, "已停止发货");
	}
	
	@Test
	public void testCancelRefund() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getOrderId(), 39.2, 1001);
		service.applyRefund(order.getOrderId(), 8311, new Date(), "买家不想要了");

		service.cancelRefund(order.getOrderId(), 1124, "继续发货");
	}
	
	@Test
	public void testChangeOrderAddress() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-申请退款");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getOrderId(), 39.2, 1001);
		
		ChangeAddressDTO addr = new ChangeAddressDTO();
		addr.setReceiverAddr("山东省 枣庄市 小镇");
		addr.setReceiverName("大锤");
		addr.setReceiverPhone("13100222200");
		service.changeOrderAddress(order.getOrderId(), 1234, addr);
	}
	
	@Test
	public void testBlockOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		service.payOrder(order.getOrderId(), 39.2, 1001);
		
		service.blockOrder(order.getOrderId(), 311, "涉嫌欺诈");
	}

	@Test
	public void testBlockOrderForSendOut() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		final Integer oid = order.getOrderId();
		final Integer repoId = 1;
		service.payOrder(oid, 39.2, 1001);
		service.arrangeOrderToRepo(oid, 111, repoId);
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(oid);
		int batchId = service.arrangeOrderToPos(repoId, orderIds, 311);
		StorageIoDetail ioDetail = storageService.pickupLock(batchId, 1);
		while(ioDetail != null) {
			ioDetail = storageService.pickupDoneAndLockNext(ioDetail.getDetailId(), 1);
		}
 		service.finishSend(repoId, batchId, 5);
		service.printExpress(oid, 211);
		service.blockOrder(oid, 311, "涉嫌欺诈");
		service.fillExpressNumber(oid, "ex"+oid, "申通快递", 111);
		service.sendOut(repoId, "ex"+oid, 222);
	}
	
	@Test
	public void testAppendSellerRemark() {
		CreateTradeOrderDTO orderDTO = createOrder("张三-拦截");
		Order order = service.createOrder(orderDTO);
		service.appendSellerRemark(order.getOrderId(), 1, "仔细打包");
	}
	
	@Test
	public void testFindOrdersByCondition() {
		Page<OrderVO> orders = service.findOrdersByCondition(null, null, null);
		System.out.println(JSONObject.toJSONString(orders));
	}
	
	private CreateTradeOrderDTO createOrder(String name) {
		CreateTradeOrderDTO orderDTO = new CreateTradeOrderDTO();
		orderDTO.setOperator(1101);
		orderDTO.setOrderMoney(99.5);
		orderDTO.setOrderType(OrderType.SELF.getValue());
		orderDTO.setOutOrderNumber("TB3100943-" + new Random().nextInt(1000));
		orderDTO.setReceiverAddr("北京朝阳区来广营");
		orderDTO.setReceiverName(name);
		orderDTO.setReceiverPhone("13812345670");
		orderDTO.setBuyerMessage("尽快发货哦");
		orderDTO.setShopId(1);
		List<SubOrder> subOrders = new ArrayList<SubOrder>();
		subOrders.add(createSubOrder(71419, 16));
		subOrders.add(createSubOrder(42115, 20));
		orderDTO.setSubOrders(subOrders);
		return orderDTO;
	}

	private SubOrder createSubOrder(Integer itemId, Integer skuId) {
		SubOrder subOrder = new SubOrder();
		subOrder.setItemCount(2);
		subOrder.setItemId(itemId);
		subOrder.setItemName("商品" + itemId);
		subOrder.setItemOutNum("out-1101-" + itemId);
		subOrder.setItemPrice(29.9);
		subOrder.setSkuId(skuId);
		return subOrder;
	}
}
