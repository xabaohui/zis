package com.zis.storage.repository;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zis.base.BaseTestUnit;
import com.zis.storage.dto.StockDTO;
import com.zis.storage.entity.StoragePosStock;
import com.zis.storage.entity.StoragePosition;
import com.zis.storage.entity.StoragePosition.PosStatus;
import com.zis.storage.entity.StorageProduct;

public class StoragePosStockDaoTest extends BaseTestUnit {

	@Autowired
	StoragePosStockDao stockDao;
	@Autowired
	StoragePositionDao posDao;
	@Autowired
	StorageProductDao prodDao;

	@Test
	public void testFindByPosId() {
		StoragePosStock s = buildStoragePosStock();
		stockDao.save(s);
		
		List<StoragePosStock> list = stockDao.findByPosId(s.getPosId());
		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void testFindAvailableStock() {
		StoragePosition pos = buildPosition();
		pos = posDao.save(pos);
		StorageProduct prod = buildStorageProduct();
		prod = prodDao.save(prod);
		StoragePosStock stock = buildStoragePosStock();
		stock.setPosId(pos.getPosId());
		
		List<StockDTO> stockDTO = stockDao.findAvailableStock(prod.getProductId());
		Assert.assertNotNull(stockDTO);
	}
	
	@Test
	public void testFindByLabelAndProductId() {
		StoragePosition pos = buildPosition();
		pos = posDao.save(pos);
		StorageProduct prod = buildStorageProduct();
		prod = prodDao.save(prod);
		StoragePosStock stock = buildStoragePosStock();
		stock.setProductId(prod.getProductId());
		stock.setPosId(pos.getPosId());
		stockDao.save(stock);
		
		StoragePosStock stockRs = stockDao.findByLabelAndProductId(pos.getRepoId(), pos.getLabel(), prod.getProductId());
		Assert.assertNotNull(stockRs);
	}

	private StoragePosStock buildStoragePosStock() {
		StoragePosStock s = new StoragePosStock();
		s.setTotalAmt(100);
		s.setOccupyAmt(0);
		s.setPosId(new Random().nextInt(100000));
		s.setProductId(1);
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		return s;
	}
	
	private StoragePosition buildPosition() {
		StoragePosition pos = new StoragePosition();
		pos.setLabel("L" + new Random().nextInt(100000));
		pos.setRepoId(1);
		pos.setPosStatus(PosStatus.AVAILABLE.getValue());
		pos.setGmtCreate(new Date());
		pos.setGmtModify(new Date());
		return pos;
	}
	
	private StorageProduct buildStorageProduct() {
		StorageProduct s = new StorageProduct();
		s.setGmtCreate(new Date());
		s.setGmtModify(new Date());
		s.setLockFlag(false);
		s.setLockReason("sdsdsdsd");
		s.setSkuId(new Random().nextInt());
		s.setSkuName("testSku");
		s.setRepoId(1);
		s.setStockAmt(23333);
		s.setStockAvailable(131312);
		s.setStockOccupy(13131);
		s.setSubjectId(2111113);
		s.setSubjectName("subject");
		return s;
	}
}