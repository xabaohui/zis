package com.zis.common.capture;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zis.bookinfo.util.BookMetadata;

/**
 * 默认图书信息抓取工具
 * 
 * @author yz
 *
 */
public class DefaultBookMetadataCaptureHandler {
	
	// 所有的抓取类
	private Map<String, BookMetadataCapture> captureMap;
	
	private Logger logger = LoggerFactory.getLogger(DefaultBookMetadataCaptureHandler.class);
	
	/**
	 * 抓取详情页
	 * @param itemId 外网商品ID
	 * @param bookMetadataSource 来源网站
	 * @return
	 */
	public BookMetadata captureDetailPage(String itemId, String bookMetadataSource) {
		if(isBlank(itemId) || isBlank(bookMetadataSource)) {
			throw new IllegalArgumentException("抓取数据失败，参数不能为空");
		}
		BookMetadataCapture capture = captureMap.get(bookMetadataSource);
		if(capture == null) {
			throw new BookMetadataCaptureException("抓取数据失败，没有找到对应的Capture:" + bookMetadataSource);
		}
		return capture.captureDetailPage(itemId);
	}

	/**
	 * 按照关键词抓取单个商品详情页<p/>
	 * 如果抓取到多个，会直接跳过（疑似重复记录，需要人工处理）
	 * @param keyword
	 * @return
	 */
	public BookMetadata captureListPage(String keyword) {
		if(isBlank(keyword)) {
			throw new IllegalArgumentException("抓取数据失败，参数不能为空");
		}
		BookMetadata metaFinal = null; // 最终结果
		for (Entry<String, BookMetadataCapture> entry : captureMap.entrySet()) {
			List<BookMetadata> list = entry.getValue().captureListPage(keyword);
			// 无记录，则跳过，自动尝试抓取其他网站
			if (list == null || list.isEmpty()) {
				logger.info("[数据抓取-{}] 未找到记录，keyword={}", entry.getKey(), keyword);
				continue;
			}
			// 存在多条记录，则跳过，自动尝试抓取其他网站
			if (list.size() > 1) {
				logger.info("[数据抓取-{}] 存在多条记录，keyword={}", entry.getKey(), keyword);
				continue;
			}
			// 如果数据检验通过，则直接返回
			BookMetadata metaTmp = list.get(0);
			if (isValid(metaTmp)) {
				logger.info("[数据抓取-{}] 抓取成功，keyword={}", entry.getKey(), keyword);
				return metaTmp;
			}
			// 如果数据不完整，需通过多个网站抓取后合并数据
			if (metaFinal == null) {
				// 首次查询到的记录，但是由于数据不完整，暂时记录下来，方便后续补全
				metaFinal = metaTmp;
				logger.debug("[数据抓取-{}] 数据不完整，暂时保存，keyword={}", entry.getKey(), keyword);
			} else {
				// 后续查询到的记录，补全首次查询记录
				fillEmptyData(metaFinal, metaTmp);
			}
		}
		// 抓取结束，并且数据已完整，返回结果
		if(metaFinal != null && isValid(metaFinal)) {
			logger.info("[数据抓取-多站点] 抓取成功，数据来源于多站点，keyword={}", keyword);
			return metaFinal;
		}
		// 如果数据不完整，依然返回null
		return null;
	}

	// 后续查询到的记录，补全首次查询记录
	private void fillEmptyData(BookMetadata mf, BookMetadata mt) {
		if(isBlank(mf.getName()) && !isBlank(mt.getName())) {
			mf.setName(mt.getName());
		}
		if(isBlank(mf.getAuthor()) && !isBlank(mt.getAuthor())) {
			mf.setAuthor(mt.getAuthor());
		}
		if(isBlank(mf.getPublisher()) && !isBlank(mt.getPublisher())) {
			mf.setPublisher(mt.getPublisher());
		}
		if(mf.getPublishDate() == null && mt.getPublishDate() != null) {
			mf.setPublishDate(mt.getPublishDate());
		}
		if(isBlank(mf.getIsbnCode()) && !isBlank(mt.getIsbnCode())) {
			mf.setIsbnCode(mt.getIsbnCode());
		}
		if(isBlank(mf.getImageUrl()) && !isBlank(mt.getImageUrl())) {
			mf.setImageUrl(mt.getImageUrl());
		}
		if(isBlank(mf.getSummary()) && !isBlank(mt.getSummary())) {
			mf.setSummary(mt.getSummary());
		}
		if(isBlank(mf.getCatalog()) && !isBlank(mt.getCatalog())) {
			mf.setCatalog(mt.getCatalog());
		}
		if(mf.getPrice() == null && mt.getPrice()!= null) {
			mf.setPrice(mt.getPrice());
		}
	}

	// 数据完整性检查
	private boolean isValid(BookMetadata book) {
		if(isBlank(book.getName())) {
			return false;
		}
		if(isBlank(book.getAuthor())) {
			return false;
		}
		if(isBlank(book.getEdition())) {
			return false;
		}
		if(isBlank(book.getPublisher())) {
			return false;
		}
		if(book.getPublishDate() == null) {
			return false;
		}
		if(isBlank(book.getIsbnCode())) {
			return false;
		}
		if(book.getPrice() == null || book.getPrice() <= 0) {
			return false;
		}
		if(isBlank(book.getImageUrl())) {
			return false;
		}
		if(book.getOutId() == null) {
			return false;
		}
		return true;
	}

	public void setCaptureMap(Map<String, BookMetadataCapture> captureMap) {
		this.captureMap = captureMap;
	}
}
