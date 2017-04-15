package com.zis.storage.repository;

import java.util.Date;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageProduct;

public class StorageProductDaoTest extends BaseTestUnit {

	@Autowired
	StorageProductDao dao;

	@Test
	public void testFindBySkuIdAndRepoId() {
		StorageProduct s = getStorageProduct();
		dao.save(s);

		StorageProduct prod = dao.findBySkuIdAndRepoId(s.getSkuId(), s.getRepoId());
		Assert.assertNotNull(prod);
	}

	private StorageProduct getStorageProduct() {
		StorageProduct s = new StorageProduct();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setLockFlag(false);
		s.setLockReason("sdsdsdsd");
		s.setSkuId(new Random().nextInt());
		s.setSkuName("testSku");
		s.setSubjectId(1);
		s.setSubjectName("testSubjectName");
		s.setRepoId(1);
		s.setStockAmt(23333);
		s.setStockAvailable(131312);
		s.setStockOccupy(13131);
		s.setSubjectId(2111113);
		return s;
	}
}