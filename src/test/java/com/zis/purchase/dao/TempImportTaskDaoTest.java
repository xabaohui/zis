package com.zis.purchase.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskBizTypeEnum;
import com.zis.purchase.bean.TempImportTaskStatus;
import com.zis.purchase.repository.TempImportTaskDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class TempImportTaskDaoTest {

	@Autowired
	private TempImportTaskDao dao;
	
	@Test
	public void testSave() {
		// prepare data
		TempImportTask task = generateTask();
		dao.save(task);
		
		// execute
		TempImportTask data = dao.findOne(task.getId());
		Assert.assertNotNull(data);
	}

	private TempImportTask generateTask() {
		TempImportTask task = new TempImportTask();
		task.setBizType(TempImportTaskBizTypeEnum.PURCHASE.getValue());
		task.setGmtCreate(new Date());
		task.setGmtModify(new Date());
		task.setStatus(TempImportTaskStatus.SUCCESS);
		task.setTotalCount(10);
		return task;
	}
}
