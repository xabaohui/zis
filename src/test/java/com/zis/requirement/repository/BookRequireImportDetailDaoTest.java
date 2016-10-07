package com.zis.requirement.repository;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.bean.BookRequireImportDetailStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BookRequireImportDetailDaoTest {

	@Autowired
	BookRequireImportDetailDao bookRequireImportDetailDao;

	@Test
	public void testFindByBatchId() {
		// prepare data
		BookRequireImportDetail test = getBookRequireImportDetail();
		this.bookRequireImportDetailDao.save(test);

		// execute
		List<BookRequireImportDetail> list = this.bookRequireImportDetailDao
				.findByBatchId(test.getBatchId());

		// assert
		Assert.assertFalse(list.isEmpty());
		for (BookRequireImportDetail rec : list) {
			Assert.assertEquals(BookRequireImportDetailStatus.BOOK_NOT_MATCHED, rec.getStatus());
			Assert.assertEquals(test.getIsbn(), rec.getIsbn());
		}
	}

	private BookRequireImportDetail getBookRequireImportDetail() {
		BookRequireImportDetail testBean = new BookRequireImportDetail();
		testBean.setBookid(9527);
		testBean.setIsbn("95279527");
		testBean.setBookName("初中生物");
		testBean.setBookAuthor("彭sir");
		testBean.setBookPublisher("生物化工出版社");
		testBean.setBookEdition("第一版");
		testBean.setDepartId(100);
		testBean.setCollege("麻省理工");
		testBean.setInstitute("生物电子学院");
		testBean.setPartName("爆破专业");
		testBean.setClassNum("09527");
		testBean.setGrade(3);
		testBean.setTerm(2);
		testBean.setAmount(100);
		testBean.setBatchId(8859);
		testBean.setStatus(BookRequireImportDetailStatus.BOOK_NOT_MATCHED);
		testBean.setGmtCreate(new Timestamp(System.currentTimeMillis()));
		testBean.setGmtModify(new Timestamp(System.currentTimeMillis()));
		return testBean;
	}
}