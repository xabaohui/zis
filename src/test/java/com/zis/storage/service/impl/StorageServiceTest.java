package com.zis.storage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zis.base.BaseTestUnit;
import com.zis.storage.dto.CreateOrderDTO;
import com.zis.storage.dto.StorageLacknessOpDTO;
import com.zis.storage.dto.CreateOrderDTO.CreateOrderDetail;
import com.zis.storage.entity.StorageIoBatch;
import com.zis.storage.entity.StorageIoDetail;
import com.zis.storage.entity.StorageOrder.OrderType;
import com.zis.storage.service.StorageService;

public class StorageServiceTest extends BaseTestUnit {
	
	final Integer operator = 111;
	final Integer repoId = 1;
	final Integer skuId = 20;
	
	@Autowired
	StorageService service;
	
	@Test
	public void testCreateStoragePosition() {
		service.createStoragePosition(repoId, "A-01-01-08");
		service.createStoragePosition(repoId, "B-02-01-08");
		service.createStoragePosition(repoId, "D-04-03-02");
	}
	
	@Test
	public void testCreateInStorage() {
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
	}
	
	@Test
	public void testAddInStorageDetail() {
		final String posLabel = "A-01-01-08";
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
//		service.createStoragePosition(repoId, posLabel);
		service.addInStorageDetail(batch.getBatchId(), skuId, 3, posLabel, operator);
	}
	
	@Test
	public void testConfirmInStorage() {
		final String posLabel = "A-01-01-08";
		StorageIoBatch batch = service.createInStorage(repoId, "测试创建入库单", operator);
		service.addInStorageDetail(batch.getBatchId(), skuId, 4, posLabel, operator);
		service.confirmInStorage(batch.getBatchId(), operator);
	}
	
	@Test
	public void testCancelInStorage() {
		final String posLabel = "A-01-01-08";
		StorageIoBatch batch = service.createInStorage(repoId, "测试入库单-取消操作", operator);
		service.addInStorageDetail(batch.getBatchId(), skuId, 3, posLabel, operator);
		service.cancelInStorage(batch.getBatchId(), operator);
	}
	
	@Test
	public void testDirectInStorage() {
		service.directInStorage(repoId, skuId, 50, "A-01-01-08", operator);
		service.directInStorage(repoId, skuId, 20, "B-02-01-08", operator);
		service.directInStorage(repoId, skuId, 30, "D-04-03-02", operator);
	}
	
	// --- 测试出库 ---
	
	@Test
	public void testDirectSend() {
		service.directSend(repoId, skuId, 2, "A-01-01-08", operator);
		service.directSend(repoId, skuId, 2, "B-02-01-08", operator);
		service.directSend(repoId, skuId, 2, "D-04-03-02", operator);
	}
	
	@Test
	public void testCreateOrder() {
		List<CreateOrderDetail> detailList = new ArrayList<CreateOrderDTO.CreateOrderDetail>();
		CreateOrderDetail d = new CreateOrderDetail();
		d.setAmount(4);
		d.setSkuId(skuId);
		detailList.add(d);
		CreateOrderDTO request = new CreateOrderDTO();
		request.setBuyerName("zara");
		request.setOutOrderId(1119231);
		request.setOrderType(OrderType.SELF);
		request.setRepoId(repoId);
		request.setShopId(1113);
		request.setDetailList(detailList);
		service.createOrder(request);
	}
	
	@Test
	public void testArrangeOrder() {
		List<Integer> orderIds = new ArrayList<Integer>();
		orderIds.add(123123);
		service.arrangeOrder(repoId, orderIds, operator);
	}
	
	@Test
	public void testPickup() {
		StorageIoDetail detail = service.pickupLock(36, operator);
		System.out.println(JSONObject.toJSONString(detail));
		while (detail != null){
			detail = service.pickupDoneAndLockNext(detail.getDetailId(), operator);
			System.out.println(JSONObject.toJSONString(detail));
		}
		Assert.assertNull(detail);
	}
	
	@Test
	public void testLackAll() {
		// 取件
		StorageIoDetail detail = service.pickupLock(21, operator);
		// 缺货
		StorageLacknessOpDTO rs = service.lackAll(detail.getDetailId(), operator);
		System.out.println(rs.getLacknessMatchNewPos());
	}
	
	@Test
	public void testLackPart() {
		// 取件
		StorageIoDetail detail = service.pickupLock(93, operator);
		// 缺货
		StorageLacknessOpDTO rs = service.lackPart(detail.getDetailId(), operator, 1);
		System.out.println(rs.getLacknessMatchNewPos());
	}
	
	@Test
	public void testCancelOrder() {
		service.cancelOrder(24);
	}
	
	@Test
	public void testFinishBatchSend() {
		service.finishBatchSend(18);
	}
}
