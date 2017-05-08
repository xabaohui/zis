package com.zis.storage.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageOrder;
import com.zis.storage.entity.StorageOrder.OrderType;
import com.zis.storage.entity.StorageOrder.TradeStatus;

@TransactionConfiguration(defaultRollback=false)
public class StorageOrderDaoTest extends BaseTestUnit {

	@Autowired
	StorageOrderDao dao;
	
	@Test
	public void testFindByOrderIds() {
		StorageOrder order = buildOrder();
		order = dao.save(order);
		StorageOrder order2 = buildOrder();
		order2 = dao.save(order2);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(order.getOrderId());
		ids.add(order2.getOrderId());
		List<StorageOrder> list = dao.findByOrderIds(ids);
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	@Transactional
	public void testUpdateToSentByBatchId() {
		dao.updateToSentByBatchId(28);
	}

	private StorageOrder buildOrder() {
		StorageOrder o = new StorageOrder();
		o.setOutOrderId(1111);
		o.setRepoId(1);
		o.setShopId(1);
		o.setAmount(1);
		o.setBuyerName("buyer");
		o.setOrderDetail("testDetail");
		o.setOrderType(OrderType.SELF.getValue());
		o.setTradeStatus(TradeStatus.CREATED.getValue());
		o.setVersion(0);
		o.setGmtCreate(new Date());
		o.setGmtModify(new Date());
		return o;
	}
}
