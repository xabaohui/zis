package com.zis.storage.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StoragePosition;
import com.zis.storage.entity.StoragePosition.PosStatus;

public class StoragePositionDaoTest extends BaseTestUnit {

	@Autowired
	StoragePositionDao dao;
	
	@Test
	public void testSave() {
		StoragePosition pos = buildPosition();
		dao.save(pos);
	}
	
	@Test
	public void testFindByRepoId() {
		final Integer repoId = 1;
		StoragePosition pos = buildPosition();
		pos.setRepoId(repoId);
		dao.save(pos);
		
		List<StoragePosition> list = dao.findByRepoId(repoId);
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindByLabelAndRepoId() {
		final Integer repoId = 199;
		StoragePosition pos = buildPosition();
		pos.setRepoId(repoId);
		dao.save(pos);
		
		StoragePosition record = dao.findByLabelAndRepoId(pos.getLabel(), repoId);
		Assert.assertNotNull(record);
//		Assert.assertFalse(list.isEmpty());
	}

	private StoragePosition buildPosition() {
		StoragePosition pos = new StoragePosition();
		pos.setLabel("L-" + new Random().nextInt(100000));
		pos.setRepoId(1);
		pos.setPosStatus(PosStatus.AVAILABLE.getValue());
		pos.setGmtCreate(new Date());
		pos.setGmtModify(new Date());
		return pos;
	}
}
