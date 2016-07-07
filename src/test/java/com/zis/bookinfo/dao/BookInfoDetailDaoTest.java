package com.zis.bookinfo.dao;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.common.util.ZisUtils;

public class BookInfoDetailDaoTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml", "applicationContext_bookinfo.xml", "applicationContext_requirement.xml"});
		BookinfoDetailDao bookinfoDetailDao = (BookinfoDetailDao) ctx.getBean("bookinfoDetailDao");
		BookinfoDetail detail = new BookinfoDetail();
		detail.setBookid(32);
		detail.setCatalog("catalog");
		detail.setSummary("summary");
		detail.setGmtCreate(ZisUtils.getTS());
		detail.setGmtModify(ZisUtils.getTS());
		detail.setVersion(0);
		bookinfoDetailDao.save(detail);
		
		BookinfoDetail d = bookinfoDetailDao.findByBookId(1);
		System.out.println(d.getCatalog());
	}
}
