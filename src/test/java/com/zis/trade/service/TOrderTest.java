package com.zis.trade.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zis.base.BaseTestUnit;
import com.zis.trade.entity.Order;
import com.zis.trade.entity.TOrder;
import com.zis.trade.entity.TOrderDetail;
import com.zis.trade.repository.TOrderDao;
import com.zis.trade.repository.TOrderDetailDao;

public class TOrderTest extends BaseTestUnit {

	@Autowired
	TOrderDao oDao;
	@Autowired
	TOrderDetailDao odDao;
	
	@Test
	public void test2() {
		TOrder od = oDao.findOne(2);
		System.out.println(od.getOrderDetails());
		System.out.println(od.getOSerial());

		TOrder od2 = oDao.findOne(3);
		System.out.println(od2.getOrderDetails());
		System.out.println(od2.getOSerial());
	}
	
	@Test
	public void test() {
		TOrder order = new TOrder();
		order.setAddr("addr");
		order.setName("name");
		
		TOrderDetail od1 = new TOrderDetail(3, order); 
		TOrderDetail od2 = new TOrderDetail(1, order); 
		
		List<TOrderDetail> ods = new ArrayList<TOrderDetail>();
		ods.add(od1);
		ods.add(od2);
		
		oDao.save(order);
		odDao.save(od1);
		odDao.save(od2);
		
		TOrder rsOrder = oDao.findOne(2);
		for (TOrderDetail od : rsOrder.getOrderDetails()) {
			System.out.println(od.getId());
		}
		
		TOrderDetail tod = odDao.findOne(3);
		System.out.println(tod.getOrder());
	}
}
