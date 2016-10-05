package com.zis.bookinfo.dao;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.repository.BookInfoDetailDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value={"classpath:spring.xml"})
public class BookInfoDetailDaoTest {

	@Autowired
	private BookInfoDetailDao dao;
	
	@Test
	public void testSave() {
		// execute
		BookinfoDetail detail = generateDetail();
		dao.save(detail);
	}

	private BookinfoDetail generateDetail() {
		BookinfoDetail detail = new BookinfoDetail();
		detail.setBookid(new Random().nextInt(1000));
		detail.setCatalog("catalog");
		detail.setSummary("summary");
		detail.setSource("taobao");
		detail.setGmtCreate(new Date());
		detail.setGmtModify(new Date());
		return detail;
	}
}
