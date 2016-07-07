package com.zis.bookinfo.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@TransactionConfiguration(transactionManager = "transactionManagerH", defaultRollback = false)
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext_bookinfo.xml", "classpath:applicationContext_requirement.xml"})
public class BookServiceTest {
	
	@Resource
	private BookService bookService;
	
	@Test
	public void testCaptureBookMetadataFromNet() {
		bookService.captureBookInfoDetailFromNet(103);
	}
}
