package com.zis.common.capture;

import java.util.List;

import org.junit.Test;

import com.zis.bookinfo.util.BookMetadata;

public class DangDangBookMetadataCaptureTest {

	DangDangBookMetadataCapture capture = new DangDangBookMetadataCapture();
	
	@Test
	public void testCaptureListPage() {
		List<BookMetadata> list = capture.captureListPage("9787539996295");
		for (BookMetadata data : list) {
			System.out.println(data);
		}
	}
	
	@Test
	public void testCaptureDetailPage() {
		BookMetadata meta = capture.captureDetailPage("1479837742");
		System.out.println(meta);
		BookMetadata meta2 = capture.captureDetailPage("23056713");
		System.out.println(meta2);
	}
}
