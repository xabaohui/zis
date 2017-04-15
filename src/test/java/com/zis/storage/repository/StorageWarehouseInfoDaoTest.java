package com.zis.storage.repository;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageRepoInfo;

public class StorageWarehouseInfoDaoTest extends BaseTestUnit {

	@Autowired
	StorageRepoInfoDao dao;

	@Test
	public void testSave() {
		StorageRepoInfo s = getStorageWarehouseInfo();
		dao.save(s);

		StorageRepoInfo s1 = dao.findOne(s.getRepoId());
		Assert.assertNotNull(s1);
	}

	private StorageRepoInfo getStorageWarehouseInfo() {
		StorageRepoInfo s = new StorageRepoInfo();
		s.setName("testRepo-" + new Random().nextInt());
		s.setOwnerId(1);
		s.setStatus(StorageRepoInfo.Status.AVAILABLE.getValue());
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		return s;
	}
}