package com.zis.trade.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.zis.base.BaseTestUnit;
import com.zis.trade.dto.OrderVO;


public class OrderServiceTest extends BaseTestUnit {

	@Autowired
	OrderService service;
	
	@Test
	public void testPayOrder() {
		service.payOrder(3001, 39.2, 1001);
	}
	
	@Test
	public void testFindOrdersByCondition() {
		Page<OrderVO> orders = service.findOrdersByCondition(null, null, null);
		System.out.println(JSONObject.toJSONString(orders));
	}
}
