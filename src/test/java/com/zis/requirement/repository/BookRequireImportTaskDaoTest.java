package com.zis.requirement.repository;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.requirement.bean.BookRequireImportTask;
import com.zis.requirement.bean.BookRequireImportTaskStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BookRequireImportTaskDaoTest {

	@Autowired
	BookRequireImportTaskDao dao;

	@Test
	public void testSave() {
		// prepare data
		BookRequireImportTask test = getBookRequireImportTask();

		// execute
		this.dao.save(test);

		// assert
		BookRequireImportTask result = this.dao.findOne(test.getId());
		Assert.assertEquals(test.getCollege(), result.getCollege());
		Assert.assertEquals(test.getOperator(), result.getOperator());
		Assert.assertEquals(test.getMemo(), result.getMemo());
		Assert.assertEquals(test.getTotalCount(), result.getTotalCount());
		Assert.assertEquals(test.getStatus(), result.getStatus());
	}

	private BookRequireImportTask getBookRequireImportTask() {
		BookRequireImportTask test = new BookRequireImportTask();
		test.setCollege("加里敦大学");
		test.setOperator("斯蒂芬.霍金");
		test.setMemo("你猜你猜我猜不猜");
		test.setTotalCount(110);
		test.setStatus(BookRequireImportTaskStatus.NOT_MATCHED);
		test.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		test.setGmtModify(new Timestamp(System.currentTimeMillis()));
		return test;
	}
}