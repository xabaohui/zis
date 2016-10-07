package com.zis.requirement.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.requirement.bean.SysVar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class SysVarDaoTest {

	@Autowired
	SysVarDao dao;

	@Test
	public void testFindByDepKey() {
		// prepare data
		SysVar test = getSysVar();
		this.dao.save(test);

		// execute
		List<SysVar> list = this.dao.findByDepKey(test.getDepKey());

		// assert
		Assert.assertFalse(list.isEmpty());
		for (SysVar sysVar : list) {
			Assert.assertEquals(test.getDepKey(), sysVar.getDepKey());
			Assert.assertEquals(test.getDepValue(), sysVar.getDepValue());
		}
	}

	private SysVar getSysVar() {
		SysVar test = new SysVar();
		test.setDepKey("2323");
		test.setDepValue(22353);
		return test;
	}
}