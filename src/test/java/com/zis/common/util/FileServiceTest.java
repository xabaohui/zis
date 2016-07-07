package com.zis.common.util;

import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceTest {
	
	private static FileService fileService;
	
	@BeforeClass
	public static void init() {
		System.out.println("init...");
		FileServiceImpl fileServiceBean = new FileServiceImpl();
		fileServiceBean.setBaseDir("d:/var/testFileService/");
		fileServiceBean.setDirDeepth(4);
		fileService = fileServiceBean;
	}

	@Test
	/**
	 * 随机文件名存储文件
	 */
	public void testPut() {
		String path = fileService.put("hahaha".getBytes());
		System.out.println(path);
		File f = new File(fileService.getBaseDir() + "/" + path);
		Assert.assertTrue(f.exists());
	}
	
	@Test
	/**
	 * 按照特定文件名存储文件
	 */
	public void testPutSpecifiedName() {
		String path = fileService.put("hahah".getBytes(), "testName1");
		System.out.println(path);
		File f = new File(fileService.getBaseDir() + "/" + path);
		Assert.assertTrue(f.exists());
	}
	
	/**
	 * 获取文件
	 */
	@Test
	public void testGet() {
		String path = fileService.put("hahahaddd".getBytes());
		byte[] b = fileService.get(path);
		Assert.assertNotNull(b);
	}
	
	@Test
	/**
	 * 获取文件绝对路径
	 */
	public void testGetAbsolutePath() {
		String path = fileService.put("hahahaddd".getBytes());
		String absPath = fileService.getAbsolutePath(path);
		System.out.println(absPath);
		Assert.assertNotNull(absPath);
	}
	
	@Test
	public void testRemove() {
		String path = fileService.put("hahahaddd".getBytes(), "test11");
		File f = new File(fileService.getBaseDir() + "/" + path);
		Assert.assertTrue(f.exists());
		fileService.remove(path);
		Assert.assertFalse(f.exists());
	}
}
