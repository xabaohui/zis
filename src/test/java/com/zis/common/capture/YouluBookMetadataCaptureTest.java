package com.zis.common.capture;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.zis.bookinfo.util.BookMetadata;

public class YouluBookMetadataCaptureTest {

	private static YouluBookMetadataCapture capture = new YouluBookMetadataCapture();
	
	@Test
	public void testPage404() {
		BookMetadata data = capture.captureDetailPage("95279552");
		Assert.assertNull(data);
	}
	
	@Test
	public void testParseMetadata() {
		BookMetadata data = capture.captureDetailPage("100034");
	}
	
	@Test
	public void testCapatureListPage() {
		List<BookMetadata> resultList = capture.captureListPage("9787040212778");
		for (BookMetadata bookMetadata : resultList) {
			System.out.println(bookMetadata);
		}
	}
}
