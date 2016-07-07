package com.zis.common.util;

/**
 * 文件中心服务<p/>
 * 特点：<p/>
 * <ul>
 * <li>提供相对路径，解决web环境下迁移难的问题</li>
 * <li>存储文件不用考虑路径和文件名，系统自动分配，确保每个目录下文件不会过多</li>
 * </ul>
 * @author yz
 *
 */
public interface FileService {

	/**
	 * 文件存储，随机生成一个文件名
	 * @param data
	 * @return 文件相对路径
	 */
	public String put(byte[] data);
	
	/**
	 * 文件存储，使用指定的文件名存储
	 * @param data
	 * @param filename 文件名
	 * @return 文件相对路径
	 */
	public String put(byte[] data, String filename);
	
	/**
	 * 获取文件
	 * @param relativePath 文件相对路径
	 * @return 文件内容
	 */
	public byte[] get(String relativePath);
	
	/**
	 * 获取文件绝对路径
	 * @param relativePath 文件相对路径
	 * @return 文件绝对路径
	 */
	public String getAbsolutePath(String relativePath);
	
	/**
	 * 删除文件
	 * @param relativePath 文件相对路径
	 */
	public void remove(String relativePath);
	
	/**
	 * 获取所有文件的基础路径
	 * @return
	 */
	public String getBaseDir();
}
