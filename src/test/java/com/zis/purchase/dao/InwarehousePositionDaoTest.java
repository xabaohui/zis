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

import com.zis.common.util.TestUtil;
import com.zis.purchase.bean.InwarehousePosition;
import com.zis.purchase.repository.InwarehousePositionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class InwarehousePositionDaoTest {

	@Autowired
	private InwarehousePositionDao dao;
	
	@Test
	public void testFindAvailablePosition() {
		// prepare
		final Integer inId = new Random().nextInt(10000);
		InwarehousePosition pos = generatePosition();
		pos.setInwarehouseId(inId);
		pos.setIsFull(false);
		dao.save(pos);

		InwarehousePosition pos2 = generatePosition();
		pos2.setInwarehouseId(inId);
		pos2.setIsFull(true);
		dao.save(pos2);
		
		// execute
		List<InwarehousePosition> list = dao.findAvailablePosition(inId);
		Assert.assertEquals(1, list.size());
	}

	private InwarehousePosition generatePosition() {
		InwarehousePosition pos = new InwarehousePosition();
		pos.setCapacity(100);
		pos.setCurrentAmount(10);
		pos.setGmtCreate(new Date());
		pos.setGmtModify(new Date());
		pos.setInwarehouseId(new Random().nextInt());
		pos.setIsFull(false);
		pos.setPositionLabel(TestUtil.randomStr(5));
		return pos;
	}
}
