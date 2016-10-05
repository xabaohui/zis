package com.zis.purchase.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zis.common.util.TestUtil;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.repository.PurchasePlanDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class PurchasePlanDaoTest {

	@Autowired
	private PurchasePlanDao dao;
	
	@Test
	public void testSave() {
		PurchasePlan plan = generatePlan();
		dao.save(plan);
		System.out.println(JSONObject.toJSONString(plan));
		plan.setStatus(PurchasePlanStatus.USELESS);
	}
	
	@Test
	public void testUpdateToUselessByBookId() {
		// prepare data
		PurchasePlan plan = generatePlan();
		dao.save(plan);
		System.out.println(JSONObject.toJSONString(plan));
		
		// execute
		dao.updateToUselessByBookId(plan.getBookId());
		PurchasePlan p2 = dao.findOne(plan.getId());
		Assert.assertEquals(PurchasePlanStatus.USELESS, p2.getStatus());
		Assert.assertEquals(new Integer(1), p2.getVersion());
	}
	
	@Test
	// 为了防止脏数据干扰，会清空干扰数据，本方法必须强制回滚
	@Rollback(value=true)
	public void testFindByBookId() {
		// prepare data, clear exist data
		PurchasePlan plan = generatePlan();
		final Integer bookId = plan.getBookId();
		List<PurchasePlan> list = dao.findByBookIdForAll(bookId);
		if(!list.isEmpty()) {
			System.out.println("clear data, id=" + list.get(0).getId());
			dao.delete(list); // clear exist data
		}
		dao.save(plan);
		System.out.println(JSONObject.toJSONString(plan));
		
		// execute
		PurchasePlan data = dao.findByBookId(bookId);
		Assert.assertNotNull(data);
		Assert.assertEquals(plan.getBookId(), bookId);
	}
	
	@Test
	public void testFindByIsbn() {
		// prepare data
		PurchasePlan plan = generatePlan();
		final String isbn = plan.getIsbn();
		dao.save(plan);
		
		// execute
		List<PurchasePlan> list = dao.findByIsbn(isbn);
		Assert.assertFalse(list.isEmpty());
		for (PurchasePlan rcd : list) {
			Assert.assertEquals(isbn, rcd.getIsbn());
		}
	}
	
	@Test
	public void testFindForRecalcOnwayStock() {
		// prepare data
		PurchasePlan plan = generatePlan();
		plan.setPurchasedAmount(10);
		plan.setStatus(PurchasePlanStatus.NORMAL);
		dao.save(plan);

		// execute
		List<PurchasePlan> list = dao.findForRecalcOnwayStock();
		Assert.assertFalse(list.isEmpty());
		for (PurchasePlan rcd : list) {
			Assert.assertEquals(PurchasePlanStatus.NORMAL, rcd.getStatus());
			Assert.assertTrue(rcd.getPurchasedAmount() > 0);
		}
	}

	private PurchasePlan generatePlan() {
		PurchasePlan p = new PurchasePlan();
		p.setBookAuthor("author");
		p.setBookEdition("第一版");
		p.setBookId(new Random().nextInt(1000));
		p.setBookName("Java 疯狂讲义");
		p.setBookPublisher("人民教育出版社");
		p.setGmtCreate(new Date());
		p.setGmtModify(new Date());
		p.setIsbn("9787" + TestUtil.randomStr(9));
		p.setManualDecision(0);
		p.setPurchasedAmount(0);
		p.setRequireAmount(10);
		p.setStatus(PurchasePlanStatus.NORMAL);;
		p.setStockAmount(2);
		return p;
	}
}
