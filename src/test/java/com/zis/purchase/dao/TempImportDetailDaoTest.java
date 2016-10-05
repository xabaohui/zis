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

import com.zis.purchase.bean.TempImportDetail;
import com.zis.purchase.bean.TempImportDetailStatus;
import com.zis.purchase.repository.TempImportDetailDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class TempImportDetailDaoTest {

	@Autowired
	private TempImportDetailDao dao;
	
	@Test
	public void test() {
		// prepare data
		TempImportDetail detail = generateDetail();
		final Integer taskId = detail.getTaskId();
		final String status = detail.getStatus();
		dao.save(detail);
		
		// execute
		List<TempImportDetail> list = dao.findByTaskIdAndStatus(taskId, status);
		Assert.assertFalse(list.isEmpty());
		for (TempImportDetail data : list) {
			Assert.assertEquals(taskId, data.getTaskId());
			Assert.assertEquals(status, data.getStatus());
		}
	}

	private TempImportDetail generateDetail() {
		TempImportDetail d = new TempImportDetail();
		d.setBookId(123123);
		d.setData("test data");
		d.setGmtCreate(new Date());
		d.setGmtModify(new Date());
		d.setIsbn("9787123123");
		d.setOrigIsbn("9787132131");
		d.setStatus(TempImportDetailStatus.MATCHED);
		d.setTaskId(new Random().nextInt(1000));
		return d;
	}
}
