package com.zis.common.capture;

import java.util.List;

import com.zis.bookinfo.util.BookMetadata;

/**
 * 图书信息抓取接口
 * @author yz
 *
 */
public interface BookMetadataCapture {

	/**
	 * 抓取详情页
	 * @param itemId 商品ID
	 * @return
	 */
	public BookMetadata captureDetailPage(String itemId);
	
	/**
	 * 抓取列表页
	 * @param keyword 查询关键字
	 * @return
	 */
	public List<BookMetadata> captureListPage(String keyword);
}
