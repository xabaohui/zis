package com.zis.common.capture;

import org.junit.Test;

public class DangDangBookMetadataCaptureTest {

	DangDangBookMetadataCapture capture = new DangDangBookMetadataCapture();
	
	@Test
	public void testCaptureListPage() {
		capture.captureListPage("Java从入门到精通");
	}
	
	@Test
	public void testCaptureDetailPage() {
		capture.captureDetailPage("23311588");
		capture.captureDetailPage("23056713");
	}
}
