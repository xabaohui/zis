package com.zis.trade.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.dto.CreateTradeOrderDTO.SubOrder;
import com.zis.trade.entity.Order.OrderType;

public class OrderProcessorTest extends BaseTestUnit {

	@Autowired
	OrderProcessor processor;
	
	@Test
	public void testCreateOrder() {
		CreateTradeOrderDTO orderDTO = createOrder("姗姗");
		processor.createOrder(orderDTO);
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
