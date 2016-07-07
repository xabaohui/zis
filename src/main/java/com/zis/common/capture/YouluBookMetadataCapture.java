package com.zis.common.capture;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zis.bookinfo.util.BookMetadata;
import com.zis.bookinfo.util.BookMetadataSource;
import com.zis.common.util.ZisUtils;

/**
 * 有路网图书信息抓取
 * 
 * @author yz
 * 
 */
public class YouluBookMetadataCapture extends AbstractBookMetadataCapture {

	// 图书详情页链接
	private static final String URL_FMT_DETAIL_PAGE = "http://www.youlu.net/";
	private static final String URL_FMT_LIST_PAGE = "http://www.youlu.net/search/result/?isbn=";
	// 图书目录和摘要获取链接
	private static final String URL_FMT_CONTENT = "http://www.youlu.net/info/bodyContent.aspx?bookId=%s&contentType=%s";
	// 有路网无此图片的链接
	private static final String URL_NO_PHOTO = "http://img3.youlu.net/noPhoto.jpg";
	// 无此记录
	private static final String page404Keywords = "抱歉，找不到您要的页面";

	// -----图书属性匹配 Regex------
	protected static final String REGEX_ITEM_ID = "(?<=图书编号[：:]{1}).*?(?=\\s)"; // 图书编号
	protected static final String REGEX_ISBN = "(?<=ISBN[：:]{1})\\d*\\b"; // ISBN：9787544601207
	protected static final String REGEX_AUTHOR = "(?<=作/译者[：:]{1}).*?(?=出版社)"; // 作/译者：李荫华 张龙（在出版社之前）
	protected static final String REGEX_PUBLISHER = "(?<=出版社[：:]{1}).*\\b"; // 出版社：上海外语教育出版社（结束符）
	protected static final String REGEX_PUBLISH_DATE = "(?<=出版日期[：:]{1}).*?(?=\\s)"; // 出版日期：2006年08月（空格以及其他内容）
	protected static final String REGEX_TAG_PRICE = "(?<=定价[：:]{1}￥).*\\b"; // 定价：￥31.00（结束符）
	protected static final String REGEX_EDITION = "第.*?版"; // 版次
	protected static final String REGEX_USELESS_BRACKETS = "[(（\\[【]{1}\\s*[）)\\]】]{1}"; // 无用的括号

	private static final Logger logger = LoggerFactory
			.getLogger(YouluBookMetadataCapture.class);

	@Override
	public List<BookMetadata> captureListPage(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			throw new IllegalArgumentException("查询关键词keyword不能为空");
		}
		String url = URL_FMT_LIST_PAGE + keyword;
		Document doc = super.doRequestUrl(url);
		Element newBookList = getUniqueElementByClass(doc, "newBookList");
		if(newBookList == null) {
			return new ArrayList<BookMetadata>();
		}
		List<BookMetadata> resultList = new ArrayList<BookMetadata>();
		Elements ulList = newBookList.child(0).children();
		for (Element li : ulList) {
			try {
				String itemId = li.child(0).child(0).attr("href").substring(1);
				resultList.add(captureDetailPage(itemId));
			} catch (Exception e) {
				// 单一错误不影响其他记录
				logger.error("[数据抓取-有路网] 操作失败，原因如下：" + e.getMessage(), e);
			}
		}
		return resultList;
	}

	@Override
	protected String getDetailPageUrl(String itemId) {
		return URL_FMT_DETAIL_PAGE + itemId;
	}

	@Override
	protected boolean page404(Document doc) {
		Elements elements = doc.getElementsByTag("h2");
		for (Element element : elements) {
			if (element.text().contains(page404Keywords)) {
				logger.debug("[数据抓取-有路网] 无此记录");
				return true;
			}
		}
		return false;
	}

	@Override
	protected BookMetadata parseMetadata(Document doc) {
		// 有路网标题
		Element h1 = getUniqueElementByTag(doc, "h1");
		String title = h1.text(); // TODO 书名中剔除不必要内容
		String bookName = null;
		String edition = null;
		logger.debug("[数据抓取-有路网] 标题\t{}", title);
		// 拆分书名、版次
		Matcher tm = Pattern.compile(REGEX_EDITION).matcher(title);
		while (tm.find()) {
			edition = tm.group();
			logger.debug("[数据抓取-有路网] 版次\t{}", edition);
		}
		if (edition != null) {
			bookName = title.replaceAll(edition, "").replaceAll(
					REGEX_USELESS_BRACKETS, "");
		} else {
			edition = "第一版";
			bookName = title;
			logger.debug("[数据抓取-有路网] 版次(默认)\t{}", edition);
		}
		logger.debug("[数据抓取-有路网] 书名\t{}", bookName);

		// 条形码、作者、出版社、出版日期、标价
		Elements infoTits = doc.getElementsByClass("infoList");
		String itemId = null;
		String isbn = null;
		String author = null;
		String publisher = null;
		String publishDate = null;
		Double tagPrice = null;
		for (Element e : infoTits) {
			String txt = e.text();
			if (txt.contains("图书编号")) {
				logger.debug("[数据抓取-有路网] 图书编号相关数据\t{}", txt);
				Matcher m = Pattern.compile(REGEX_ITEM_ID).matcher(txt);
				while (m.find()) {
					itemId = m.group();
					logger.debug("[数据抓取-有路网] 图书编号=\t{}", itemId);
				}
			}
			if (txt.contains("ISBN")) {
				logger.debug("[数据抓取-有路网] ISBN相关数据\t{}", txt);
				Matcher m = Pattern.compile(REGEX_ISBN).matcher(txt);
				while (m.find()) {
					isbn = m.group();
					logger.debug("[数据抓取-有路网] ISBN=\t{}", isbn);
				}
			}
			if (txt.contains("作/译者")) {
				logger.debug("[数据抓取-有路网] 作者相关数据\t{}", txt);
				author = e.child(1).text();
				logger.debug("[数据抓取-有路网] 作者=\t{}", author);
			}
			if (txt.contains("出版社")) {
				logger.debug("[数据抓取-有路网] 出版社相关数据\t{}", txt);
				publisher = e.child(3).text();
				logger.debug("[数据抓取-有路网] 出版社=\t{}", publisher);
			}
			if (txt.contains("出版日期")) {
				logger.debug("[数据抓取-有路网] 出版日期相关数据\t{}", txt);
				Matcher m = Pattern.compile(REGEX_PUBLISH_DATE).matcher(txt);
				while (m.find()) {
					publishDate = m.group();
					logger.debug("[数据抓取-有路网] 出版日期=\t{}", publishDate);
				}
			}
			if (txt.contains("定价")) {
				logger.debug("[数据抓取-有路网] 定价相关数据\t{}", txt);
				Matcher m = Pattern.compile(REGEX_TAG_PRICE).matcher(txt);
				String priceStr = null;
				while (m.find()) {
					priceStr = m.group();
					logger.debug("[数据抓取-有路网] 标价=\t{}", priceStr);
				}
				tagPrice = priceStr == null ? null : Double.valueOf(priceStr);
			}
		}

		// 图片URL
		String imageUrl = parseImageUrl(doc);
		logger.debug("[数据抓取-有路网] 图片URL=\t{}", imageUrl);

		// 库存、售价暂不抓取

		// 摘要
		String summaryUrl = String.format(URL_FMT_CONTENT, itemId, "summary");
		String summary = super.doRequestUrlToPlainText(summaryUrl, "gbk");
		logger.debug("[数据抓取-有路网] 摘要=\t{}", summary);
		// 目录
		String catalogUrl = String.format(URL_FMT_CONTENT, itemId, "catalog");
		String catalog = super.doRequestUrlToPlainText(catalogUrl, "gbk");
		logger.debug("[数据抓取-有路网] 目录=\t{}", catalog);
		BookMetadata meta = new BookMetadata();
		meta.setAuthor(author);
		meta.setSummary(summary);
		meta.setCatalog(catalog);
		meta.setEdition(edition);
		meta.setImageUrl(imageUrl);
		meta.setIsbnCode(isbn);
		meta.setName(bookName);
		meta.setOutId(itemId);
		meta.setPrice(tagPrice);
		meta.setPublishDate(ZisUtils.stringToDate(publishDate, "yyyy年MM月"));
		meta.setPublisher(publisher);
		meta.setSummary(summary);
		meta.setSource(BookMetadataSource.YOU_LU);
		return meta;
	}

	private String parseImageUrl(Document doc) {
//		Elements elements = doc.getElementsByClass("info3");
//		if (elements.isEmpty()) {
//			throw new BookMetadataCaptureException(
//					"[数据抓取-有路网]操作失败，解析图片URL时没有找到<div class=\"info3\">");
//		}
//		if (elements.size() > 1) {
//			throw new BookMetadataCaptureException(
//					"[数据抓取-有路网]操作失败，解析图片URL时找到多个<div class=\"info3\">");
//		}
		Element info3 = getUniqueElementByClass(doc, "info3");
		if(info3 == null) {
			return null;
		}
		Element node = info3.child(0).child(0).child(0);
		String imageUrl = node.attr("src");
		// 有路网无此图片是通过重定向到noPhoto页面实现的（很古怪的逻辑）
		String realImageUrl = super.getRedirectUrl(imageUrl);
		return URL_NO_PHOTO.equals(realImageUrl) ? null : imageUrl;
	}

}
