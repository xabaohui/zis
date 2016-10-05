package com.zis.bookinfo.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.zis.bookinfo.bean.BookinfoAid;
import com.zis.bookinfo.repository.BookInfoAidDao;
import com.zis.common.util.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring.xml" })
public class BookInfoAidDaoTest {

	@Autowired
	private BookInfoAidDao bookinfoAidDao;

	@Test
	public void testFindByGroupKey() {
		// prepare
		BookinfoAid aid = generateAid();
		final String groupKey = aid.getGroupKey();
		aid.setGroupKey(groupKey);
		bookinfoAidDao.save(aid);
		System.out.println(JSONObject.toJSONString(aid));
		
		// executes
		List<BookinfoAid> list = this.bookinfoAidDao.findByGroupKey(groupKey);
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindByGroupKeyAndShortBookName() {
		// prepare
		BookinfoAid aid = generateAid();
		final String groupKey = aid.getGroupKey();
		final String shortName = aid.getShortBookName();
		aid.setGroupKey(groupKey);
		bookinfoAidDao.save(aid);
		
		// executes
		List<BookinfoAid> list = this.bookinfoAidDao.findByGroupKeyAndShortBookName(groupKey, shortName);
		for (BookinfoAid a : list) {
			System.out.println(JSONObject.toJSONString(a));
			Assert.assertEquals(shortName, a.getShortBookName());
			Assert.assertEquals(groupKey, a.getGroupKey());
		}
		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void testFindMaxId() {
		Integer id = this.bookinfoAidDao.findMaxId();
		System.out.println(id);
		Assert.assertNotNull(id);
	}

	@Test
	public void testFindMinId() {
		Integer id = this.bookinfoAidDao.findMinId();
		System.out.println(id);
		Assert.assertNotNull(id);
	}

	private BookinfoAid generateAid() {
		BookinfoAid aid = new BookinfoAid();
		aid.setCheckLevel(0);
		aid.setGroupKey(TestUtil.randomStr(5));
		aid.setIds(TestUtil.randomStr(5));
		aid.setShortBookName("test shortBook");
		aid.setTotalCount(1);
		return aid;
	}
}
