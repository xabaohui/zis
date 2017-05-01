package com.zis.trade.processor;

import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.OrderOuter;

public class OrderProcessorImpl implements OrderProcessor {

	@Override
	public Order createOrder(CreateTradeOrderDTO orderDTO) {
		checkForCreateOrder(orderDTO);
		// 幂等性控制
		
		// 创建orderOuter
		OrderOuter outOrder = new OrderOuter();
		// 按照shopId+地址，查找已存在的订单
		
		// 判断是否可以合并
		
		// 合并到之前的订单
		
		// 生成新订单
		
		return null;
	}

	
}
