package com.zis.trade.processor;

import com.zis.trade.dto.CreateTradeOrderDTO;
import com.zis.trade.entity.Order;

public interface OrderProcessor {

	/**
	 * 创建订单
	 * <p/>
	 * 自动合并同店铺、同地址的订单
	 * 
	 * @param orderDTO
	 * @return
	 */
	Order createOrder(CreateTradeOrderDTO orderDTO);
}
