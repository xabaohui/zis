package com.zis.common.capture;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.bookinfo.util.BookMetadata;
import com.zis.bookinfo.util.BookMetadataSource;

//@TransactionConfiguration(transactionManager = "transactionManagerH", defaultRollback = false)
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext_bookinfo.xml", "classpath:applicationContext_requirement.xml"})
public class DefaultBookMetadataCaptureHandlerTest {

	@Resource
	private DefaultBookMetadataCaptureHandler defaultBookMetadataCapture;
	
	@Test
	public void testCaptureDetailPage() {
		BookMetadata meta = defaultBookMetadataCapture.captureDetailPage("3008142", BookMetadataSource.YOU_LU);
		System.out.println(meta);
		Assert.assertNotNull(meta);
	}
	
	@Test
	public void testCaptureListPage() {
		BookMetadata meta = defaultBookMetadataCapture.captureListPage("9787300166636");
		System.out.println(meta);
		Assert.assertNotNull(meta);
	}
	
}
