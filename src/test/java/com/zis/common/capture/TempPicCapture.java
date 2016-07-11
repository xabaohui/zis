package com.zis.common.capture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zis.bookinfo.util.BookMetadata;
import com.zis.common.util.ImageUtils;
import com.zis.common.util.ZisUtils;

/**
 * 图片抓取工具类（临时）
 * @author yz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:applicationContext_bookinfo.xml", "classpath:applicationContext_requirement.xml"})
public class TempPicCapture {

	@Resource
	private DefaultBookMetadataCaptureHandler captureHandler;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	private static final String savePath = "D:/var/img/capture/";
	
	public void captureByIsbn(String isbn) {
		try {
			BookMetadata meta = captureHandler.captureListPage(isbn);
			if(meta == null) {
				return;
			}
			String imgUrl = meta.getImageUrl();
			ImageUtils.downloadImg(imgUrl, savePath, isbn + ".tbi");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asynchCapture(final String isbn) {
		ZisUtils.sleepQuietly(500);
		Thread task = new Thread(new Runnable() {
			@Override
			public void run() {
				captureByIsbn(isbn);
			}
		});
		taskExecutor.execute(task);
	}
	
	@Test
	public void testRun() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(savePath + "isbns"));
			String isbn = null;
			do {
				isbn = br.readLine();
				System.out.println(isbn);
				if(!ifFileExist(isbn)) {
					asynchCapture(isbn);
				}
			} while(isbn != null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean ifFileExist(String isbn) {
		File f = new File(savePath + isbn + ".tbi");
		return f.exists();
	}

	public void setCaptureHandler(
			DefaultBookMetadataCaptureHandler captureHandler) {
		this.captureHandler = captureHandler;
	}
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
