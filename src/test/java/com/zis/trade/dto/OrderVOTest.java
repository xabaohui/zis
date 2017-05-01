package com.zis.trade.dto;

import org.junit.Test;

import com.zis.trade.entity.Order.ExpressStatus;
import com.zis.trade.entity.Order.PayStatus;
import com.zis.trade.entity.Order.StorageStatus;

public class OrderVOTest {

	@Test
	public void testGetUniqueStatusDisplay() {
		OrderVO vo = new OrderVO();
		vo.setPayStatus(PayStatus.REFUND_FINISH.getValue());
		vo.setPayStatusDisplay(PayStatus.REFUND_FINISH.getDisplay());
		vo.setExpressStatus(ExpressStatus.PRINTED.getValue());
		vo.setExpressStatusDisplay(ExpressStatus.PRINTED.getDisplay());
		vo.setStorageStatus(StorageStatus.ARRANGED.getValue());
		vo.setStorageStatusDisplay(StorageStatus.ARRANGED.getDisplay());
		
		System.out.println(vo.getUniqueStatusDisplay(null));
	}
	
	@Test
	public void testGetShopNameDisplay() {
		OrderVO vo = new OrderVO();
		vo.setShopName("小龙女书屋");
		vo.setpName("淘宝");
		
		System.out.println(vo.getShopNameDisplay());
	}
}
