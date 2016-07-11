package com.zis.common.capture;

import org.junit.Test;

import com.zis.bookinfo.util.BookMetadata;

public class DangDangBookMetadataCaptureTest {

	DangDangBookMetadataCapture capture = new DangDangBookMetadataCapture();
	
	@Test
	public void testCaptureListPage() {
		capture.captureListPage("Java从入门到精通");
	}
	
	@Test
	public void testCaptureDetailPage() {
		BookMetadata meta = capture.captureDetailPage("1446314046");
		System.out.println(meta);
		BookMetadata meta2 = capture.captureDetailPage("23056713");
		System.out.println(meta2);
	}
}
