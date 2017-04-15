package com.zis.storage.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.storage.entity.StorageProduct;
import com.zis.storage.entity.StorageProductOccupy;
import com.zis.storage.entity.StorageProductOccupy.Status;

public class StorageProductOccupyDaoTest extends BaseTestUnit {

	@Autowired
	StorageProductOccupyDao dao;
	@Autowired
	StorageProductDao productDao;
	
	@Test
	public void testFindByOrderId() {
		StorageProductOccupy op = buildOccupy();
		dao.save(op);
		
		List<StorageProductOccupy> list = dao.findByOrderId(op.getOrderId());
		Assert.assertFalse(list.isEmpty());
	}
	
	private StorageProductOccupy buildOccupy() {
		StorageProductOccupy op = new StorageProductOccupy();
		op.setCurAmt(1);
		op.setOrigAmt(5);
		op.setOrderId(new Random().nextInt());
		op.setProductId(1);
		op.setStatus(Status.OCCUPY.getValue());
		op.setCreateTime(new Date());
		op.setUpdateTime(new Date());
		op.setVersion(0);
		return op;
	}
	
	private StorageProduct buildProduct() {
		StorageProduct p = new StorageProduct();
		p.setSkuId(new Random().nextInt());
		p.setSubjectId(1);
		p.setRepoId(1);
		p.setStockAmt(10);
		p.setStockAvailable(10);
		p.setLockFlag(false);
		p.setGmtCreate(new Date());
		p.setGmtModify(new Date());
		p.setVersion(0);
		return p;
	}
}
