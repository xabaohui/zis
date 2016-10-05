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

import com.alibaba.fastjson.JSONObject;
import com.zis.common.util.TestUtil;
import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.repository.InwarehouseDetailDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class InwarehouseDetailDaoTest {
	
	@Autowired
	InwarehouseDetailDao dao;
	
	@Test
	public void testFindByInwarehouseIds() {
		// prepare
		Integer[] inwarehouseIds = new Integer[3];
		for(int i=0; i<inwarehouseIds.length; i++) {
			InwarehouseDetail detail = generateDetail();
			dao.save(detail);
			inwarehouseIds[i] = detail.getInwarehouseId();
		}
		
		// execute
		List<InwarehouseDetail> list = dao.findByInwarehouseIds(inwarehouseIds);
		Assert.assertTrue(list.size() >= inwarehouseIds.length);
		for (InwarehouseDetail detail : list) {
			System.out.println(JSONObject.toJSONString(detail));
		}
	}

	private InwarehouseDetail generateDetail() {
		InwarehouseDetail detail = new InwarehouseDetail();
		detail.setAmount(10);
		detail.setBizType(InwarehouseBizType.DIRECT);
		detail.setBookId(new Random().nextInt(1000));
		detail.setGmtCreate(new Date());
		detail.setGmtModify(new Date());
		detail.setPositionLabel(TestUtil.randomStr(5));
		detail.setInwarehouseId(new Random().nextInt(1000));
		return detail;
	}

}
