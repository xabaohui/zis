package com.zis.purchase.dao;


import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.bean.InwarehouseStatus;
import com.zis.purchase.repository.InwarehouseDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class InwarehouseDaoTest {

	@Autowired
	private InwarehouseDao dao;
	
	@Test
	public void test() {
		Inwarehouse bean = generateInwarehouse();
		Inwarehouse db = dao.save(bean);
		Assert.assertNotNull(db);
	}

	private Inwarehouse generateInwarehouse() {
		Inwarehouse bean = new Inwarehouse();
		bean.setAmount(15);
		bean.setBizType(InwarehouseBizType.DIRECT);
		bean.setGmtCreate(new Date());
		bean.setGmtModify(new Date());
		bean.setInwarehouseOperator("Zhangsan");
		bean.setSource("Zhangsan's mom");
		bean.setStatus(InwarehouseStatus.PROCESSING);
		return bean;
	}
}
