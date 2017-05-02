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
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.CreateTradeOrderDTO.SubOrder;
import com.zis.trade.dto.OrderVO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.Order.OrderType;


public class OrderServiceTest extends BaseTestUnit {

	@Autowired
	OrderService service;
	
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
		orderDTO.setShopId(1);
		List<SubOrder> subOrders = new ArrayList<SubOrder>();
		subOrders.add(createSubOrder(71419, 31581));
		subOrders.add(createSubOrder(42115, 31671));
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
