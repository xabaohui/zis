package com.zis.bookinfo.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.bookinfo.bean.YouluSales;
import com.zis.bookinfo.repository.YouluSalesDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:spring.xml"})
public class YouluSalesDaoTest {

	@Autowired
	private YouluSalesDao youluSalesDao;
	
	@Test
	public void testSave() {
		YouluSales bean = generateYouluSales();
		youluSalesDao.save(bean);
	}
	
	@Test
	public void testFindByOutId() {
		// prepare
		YouluSales bean = generateYouluSales();
		final Integer outId = bean.getOutId();
		youluSalesDao.save(bean);
		// execute
		List<YouluSales> list = youluSalesDao.findByOutId(outId);
		Assert.assertFalse(list.isEmpty());
	}

	private YouluSales generateYouluSales() {
		YouluSales bean = new YouluSales();
		bean.setBookId(new Random().nextInt(1000));
		bean.setBookPrice(25.30);
		bean.setGmtCreate(new Date());
		bean.setGmtModify(new Date());
		bean.setOutId(new Random().nextInt(1000));
		bean.setStockBalance(2);
		return bean;
	}
}
