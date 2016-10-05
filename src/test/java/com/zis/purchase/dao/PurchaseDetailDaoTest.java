package com.zis.purchase.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.common.util.TestUtil;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.repository.PurchaseDetailDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class PurchaseDetailDaoTest {

	@Autowired
	private PurchaseDetailDao dao;
	
	@Test
	public void testSumPurchasedAmount() {
		// prepare data
		final Integer bookId = new Random().nextInt(10000);
		Integer expectSum = 0;
		for(int i=0; i<5; i++) {
			PurchaseDetail detail = generateDetail(bookId);
			dao.save(detail);
			expectSum += (detail.getPurchasedAmount() - detail.getInwarehouseAmount());
		}
		
		// execute
		Integer factSum = dao.sumPurchasedAmount(bookId);
		Assert.assertEquals(expectSum, factSum);
		System.out.println(factSum);
	}
	
	@Test
	public void testFindByOperatorAndStatus() {
		// prepare data
		final Integer bookId = new Random().nextInt(10000);
		PurchaseDetail detail = generateDetail(bookId);
		dao.save(detail);
		
		// execute
		List<PurchaseDetail> list = dao.findByOperatorAndStatus(detail.getOperator(), detail.getStatus());
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindByOperatorAndStatusAndBookId() {
		// prepare data
		final Integer bookId = new Random().nextInt(10000);
		PurchaseDetail detail = generateDetail(bookId);
		dao.save(detail);
		
		// execute
		List<PurchaseDetail> list = dao.findByOperatorAndStatusAndBookId(detail.getOperator(), detail.getStatus(), bookId);
		Assert.assertFalse(list.isEmpty());
	}

	private PurchaseDetail generateDetail(int bookId) {
		PurchaseDetail d = new PurchaseDetail();
		d.setBatchId(1);
		d.setBookId(bookId);
		d.setGmtCreate(new Date());
		d.setGmtModify(new Date());
		d.setOperator("testUser");
		d.setPosition(TestUtil.randomStr(5));
		d.setInwarehouseAmount(new Random().nextInt(10)); // 入库量随机数
		d.setPurchasedAmount(d.getInwarehouseAmount() + new Random().nextInt(10));// 采购量随机数，比入库量略大
		d.setStatus(PurchaseDetailStatus.PURCHASED);
		return d;
	}
}
