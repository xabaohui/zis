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

import com.zis.purchase.bean.Storesales;
import com.zis.purchase.repository.StoreSalesDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class StoreSalesDaoTest {

	@Autowired
	private StoreSalesDao dao;
	
	@Test
	public void testFindByBookId() {
		// prepare data
		Storesales bean = generateStoreSales();
		final Integer bookId = bean.getBookId();
		dao.save(bean);
		
		// execute 
		List<Storesales> list = dao.findByBookId(bookId);
		Assert.assertFalse(list.isEmpty());
		for (Storesales data : list) {
			Assert.assertEquals(bookId, data.getBookId());
		}
	}
	
	private Storesales generateStoreSales() {
		Storesales bean = new Storesales();
		bean.setBookId(new Random().nextInt(1000));
		bean.setCaptureDate(new Date());
		bean.setGmtCreate(new Date());
		bean.setGmtModify(new Date());
		bean.setIsbn("9787111");
		bean.setOutId("1213123");
		bean.setSales(10);
		return bean;
	}
}
