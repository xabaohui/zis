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
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.zis.bookinfo.bean.ShopItemInfo;
import com.zis.bookinfo.bean.ShopItemInfoStatus;
import com.zis.bookinfo.repository.ShopItemInfoDao;
import com.zis.common.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:spring.xml"})
@Transactional
public class ShopItemInfoDaoTest {

	@Autowired
	private ShopItemInfoDao dao;
	
	@Test
	public void testFindByShopNameAndBookId() {
		ShopItemInfo item = generateItem();
		final String shopName = item.getShopName();
		final Integer bookId = item.getBookId();
		dao.save(item);
		
		// execute
		List<ShopItemInfo> list = dao.findByShopNameAndBookId(shopName, bookId);
		for (ShopItemInfo si : list) {
			Assert.assertEquals(shopName, si.getShopName());
			Assert.assertEquals(bookId, si.getBookId());
			System.out.println(JSONObject.toJSONString(si));
		}
		Assert.assertFalse(list.isEmpty());
	}

	private ShopItemInfo generateItem() {
		ShopItemInfo item = new ShopItemInfo();
		item.setBookId(new Random().nextInt(1000));
		item.setGmtCreate(new Date());
		item.setGmtModified(new Date());
		item.setIsbn("9787" + TestUtil.randomStr(9));
		item.setShopName("ZaiJian");
		item.setShopStatus(ShopItemInfoStatus.ON_SALES);
		return item;
	}
}
