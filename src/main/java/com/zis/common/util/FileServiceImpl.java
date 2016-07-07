package com.zis.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileServiceImpl implements FileService {
	
	private String baseDir; // 所有文件的基础路径
	private String[] dirDatePatterns = {"yyyyMM", "yyyyMMdd", "yyyyMMddHH", "yyyyMMddHHmm"}; // 使用时间作为文件目录
	private Integer dirDeepth = 1; // 文件目录深度：1代表只有年月一级；2代表年月、日两级；3代表年月、日、时三级；4代表年月、日、时、分四级	
	private static String randomFileNamePattern = "yyyyMMddHHmmss";
	
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String put(byte[] data) {
		String filename = getRandomFileName();
		return put(data, filename);
	}
	
	@Override
	public String put(byte[] data, String filename) {
		if(data == null) {
			throw new IllegalArgumentException("文件存储异常，不能存储空文件");
		}
		if(StringUtils.isBlank(filename)) {
			throw new IllegalArgumentException("文件存储异常，文件名不能为空");
		}
		// 生成目录
		String directory = getDirectoryForPut();
		try {
			// 不允许覆盖文件，如果重名，直接报错
			File file = new File(baseDir + "/" + directory, filename);
			if(file.exists()) {
				logger.error("file '{}' exists already!", file.getPath());
				throw new RuntimeException("已存在同名文件:" + file.getPath());
			}
			// 保存文件，自动创建目录
			FileUtils.writeByteArrayToFile(file, data);
			logger.debug("store file '{}' to path '{}'", filename, file.getPath());
			// 返回相对路径
			return directory + "/" + filename;
		} catch (IOException e) {
			throw new RuntimeException("文件存储异常，" + e.getMessage(), e);
		}
	}
	
	// 生成随机文件名
	private String getRandomFileName() {
		int x = (int) (Math.random() * 1000);
		return new SimpleDateFormat(randomFileNamePattern).format(new Date()) + x;
	}

	// 获取文件相对路径，根据目录深度逐层创建
	private String getDirectoryForPut() {
		StringBuilder path = new StringBuilder();
		for (int i = 0; i < dirDeepth; i++) {
			path.append("/").append(new SimpleDateFormat(dirDatePatterns[i]).format(new Date()));
		}
		return path.substring(1).toString();
	}

	@Override
	public byte[] get(String relativePath) {
		if(StringUtils.isBlank(relativePath)) {
			throw new IllegalArgumentException("文件存储异常，相对路径不能为空");
		}
		try {
			File file = new File(baseDir, relativePath);
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			logger.error("读取文件过程中发生异常," + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public String getAbsolutePath(String relativePath) {
		File file = new File(baseDir, relativePath);
		if(!file.exists()) {
			throw new RuntimeException("文件不存在:" + relativePath);
		}
		return file.getPath();
	}

	@Override
	public void remove(String relativePath) {
		FileUtils.deleteQuietly(new File(baseDir, relativePath));
	}
	
	@Override
	public String getBaseDir() {
		return baseDir;
	}
	
	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
	
	public void setDirDeepth(Integer dirDeepth) {
		if(dirDeepth < 1) {
			throw new IllegalArgumentException("FileService支持的最小深度为 1");
		}
		if(dirDeepth > dirDatePatterns.length) {
			throw new IllegalArgumentException("FileService支持的最大深度为" + dirDatePatterns.length);
		}
		this.dirDeepth = dirDeepth;
	}
}
