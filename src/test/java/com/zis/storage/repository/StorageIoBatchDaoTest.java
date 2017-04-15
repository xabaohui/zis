package com.zis.storage.repository;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageIoBatch;
import com.zis.storage.entity.StorageIoBatch.BizType;
import com.zis.storage.entity.StorageIoBatch.Status;

public class StorageIoBatchDaoTest extends BaseTestUnit{
	
	@Autowired
	StorageIoBatchDao dao;

	@Test
	public void testSave() {
		StorageIoBatch task = buildTask();
		dao.save(task); 
	}
	
	@Test
	public void test() {
		StorageIoBatch list = dao.findDailyBatchByBizTypeAndRepoId(BizType.IN_BATCH.getValue(), 6);
		System.out.println(JSONObject.toJSON(list));
	}

	private StorageIoBatch buildTask() {
		StorageIoBatch t = new StorageIoBatch();
		t.setBizType(BizType.IN_BATCH.getValue());
		t.setStatus(Status.CREATED.getValue());
		t.setMemo("memo");
		t.setRepoId(1);
		t.setOperator(1);
		t.setGmtCreate(new Date());
		t.setGmtModify(new Date());
		return t;
	}
}
