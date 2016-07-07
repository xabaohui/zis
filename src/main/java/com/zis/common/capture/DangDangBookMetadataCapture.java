package com.zis.common.capture;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zis.bookinfo.util.BookMetadata;
import com.zis.bookinfo.util.BookMetadataSource;
import com.zis.common.util.ZisUtils;

/**
 * 当当网图书信息抓取
 * 
 * @author yz
 * 
 */
public class DangDangBookMetadataCapture extends AbstractBookMetadataCapture {

	private static final String URL_FMT_DETAIL_PAGE = "http://product.dangdang.com/%s.html";
	private static final String URL_FMT_LIST_PAGE = "http://search.dangdang.com/?key=";
	// 无此记录
	private static final String page404Keywords = "搜索无结果页";
	
	// 正则表达式
	protected static final String REGEX_EDITION = "第.*?版"; // 版次
	protected static final String REGEX_USELESS_BRACKETS = "[(（\\[【]{1}\\s*[）)\\]】]{1}"; // 无用的括号
	protected static final String REGEX_ISBN = "(?<=ISBN：)\\d+?(?=$)";
	
	private static final Logger logger = LoggerFactory
			.getLogger(DangDangBookMetadataCapture.class);

	@Override
	public List<BookMetadata> captureListPage(String keyword) {
		Document doc = super.doRequestUrl(URL_FMT_LIST_PAGE + keyword);
		// 没有记录
		if(doc.title().equals(page404Keywords)){
			logger.info("[数据抓取-当当网] 无此记录，keyword=" + keyword);
			return new ArrayList<BookMetadata>();
		}
		Elements items = doc.getElementsByClass("shoplist").get(0).child(0).children();
		if(items == null || items.isEmpty()) {
			logger.info("[数据抓取-当当网] 无此记录，keyword=" + keyword);
			return new ArrayList<BookMetadata>();
		}
		List<BookMetadata> list = new ArrayList<BookMetadata>();
		for (Element item : items) {
			try {
				String itemId = item.attr("id").substring(1); // <li class="line1" id="p23764025">
				BookMetadata meta = super.captureDetailPage(itemId);
				if(meta != null) {
					list.add(meta);
				}
			} catch (Exception e) {
				// 单一错误不影响其他记录
				logger.error("[数据抓取-当当网] 操作失败，原因如下：" + e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	protected String getDetailPageUrl(String itemId) {
		return String.format(URL_FMT_DETAIL_PAGE, itemId);
	}

	@Override
	protected boolean page404(Document doc) {
		// 当当网通过title判断404页面
		return doc.title().equals("对不起，您要访问的页面暂时没有找到。");
	}

	@Override
	protected BookMetadata parseMetadata(Document doc) {
		// 只看当当自营图书
		if(!doc.html().contains("自营图书 详情页")) {
			return null;
		}
		
		//ItemId
		// <span id="stock_span" prd_id="23311588">
		Element itemIdElem = doc.getElementById("stock_span");
		String itemId = itemIdElem.attr("prd_id");
		logger.debug("[数据抓取-当当网] 商品ID={}", itemId);
		
		// 标题
		Element h1 = getUniqueElementByTag(doc, "h1");
		String title = h1.text();
		logger.debug("[数据抓取-当当网] 标题=\t{}", title);
		// 书名、版次
		String edition = null;
		String bookName = null;
		Matcher mt = Pattern.compile(REGEX_EDITION).matcher(title);
		while(mt.find()) {
			edition = mt.group();
			logger.debug("[数据抓取-当当网] 版次\t{}", edition);
		}
		if (edition != null) {
			bookName = title.replaceAll(edition, "").replaceAll(
					REGEX_USELESS_BRACKETS, "");
		} else {
			edition = "第一版";
			bookName = title;
			logger.debug("[数据抓取-当当网] 版次(默认)\t{}", edition);
		}
		logger.debug("[数据抓取-当当网] 书名\t{}", bookName);
		
		// 作者、出版社、出版日期
		String author = null;
		String publisher = null;
		String publishDate = null;
		Element messboxInfo = getUniqueElementByClass(doc, "messbox_info");
		for (Element element : messboxInfo.children()) {
			// <span class="t1" id="author" dd_name="作者">
			if("作者".equals(element.attr("dd_name"))) {
				logger.debug("[数据抓取-当当网] 作者相关数据\t{}", element.text());
				StringBuilder builder = new StringBuilder();
				for(Element authorNode : element.children()) {
					builder.append(" ").append(authorNode.text());
				}
				author = builder.substring(1).toString();
				logger.debug("[数据抓取-当当网] 作者=\t{}", author);
			}
			// <span class="t1" dd_name="出版社">
			if("出版社".equals(element.attr("dd_name"))) {
				publisher = element.child(0).text();
				logger.debug("[数据抓取-当当网] 出版社=\t{}", publisher);
			}
			// <span class="t1">出版时间:2009年8月&nbsp;</span>
			if(element.text().contains("出版时间")) {//(?<=作/译者[：:]{1}).*?(?=出版社)
				Matcher m = Pattern.compile("(?<=出版时间:).*?(?=&)").matcher(element.html());
				while(m.find()) {
					publishDate = m.group().trim();
				}
				logger.debug("[数据抓取-当当网] 出版时间=\t{}", publishDate);
			}
		}
		
		// 定价
		// <span class="price_m">&yen;99.00</span>
		Double tagPrice = null;
		Element priceElement = getUniqueElementByClass(doc, "price_m");
		logger.debug("[数据抓取-当当网] 定价相关数据={}", priceElement.text());
		tagPrice = Double.valueOf(priceElement.text().substring(1));
		logger.debug("[数据抓取-当当网] 定价=\t{}", tagPrice);
		
		// 条形码
		// <div class="pro_content" id="detail_describe"> <ul class="key clearfix"> ... <li>国际标准书号ISBN：9787121209161</li>...
		Element detailDescrib = doc.getElementById("detail_describe");
		String isbnTxt = detailDescrib.child(0).child(9).text();
		logger.debug("[数据抓取-当当网] ISBN相关数据={}", isbnTxt);
		String isbn = null;
		Matcher m = Pattern.compile(REGEX_ISBN).matcher(isbnTxt);
		while(m.find()) {
			isbn = m.group();
		}
		logger.debug("[数据抓取-当当网] ISBN=\t{}", isbn);
		// 图片
		//<div class="pic" id="largePicDiv">
	    //	<a href="javascript:;" class="img" id="img_click">
	    //	<img id="largePic" src="http://img3x8.ddimg.cn/58/34/23311588-1_w_2.jpg"/>
		//	</a>
		Element picElement = doc.getElementById("largePicDiv");
		String imageUrl = picElement.child(0).child(0).attr("src");
		logger.debug("[数据抓取-当当网] 图片URL={}", imageUrl);
		
		// 摘要
		// <div id="content" class="section">
		Element summaryElement = doc.getElementById("content");
		String summary = summaryElement.child(1).text();
		logger.debug("[数据抓取-当当网] 摘要={}", summary);
		
		// 目录
		// <div id="catalog" class="section">
		Element catalogElement = doc.getElementById("catalog");
		String catalog = catalogElement.child(1).child(2).text();
		logger.debug("[数据抓取-当当网] 目录={}", catalog);
		
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
		meta.setSource(BookMetadataSource.DANG_DANG);
		return meta;
	}

}
