package com.zis.common.capture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zis.bookinfo.util.BookMetadata;

public abstract class AbstractBookMetadataCapture implements
		BookMetadataCapture {

	private static final int ERR_RETRY_COUNT = 3;
	private static final int TIMEOUT = 6000;
	private static final int TIMEOUT_SLEEP = 5000;
	

	@Override
	public BookMetadata captureDetailPage(String itemId) {
		if (StringUtils.isBlank(itemId)) {
			throw new IllegalArgumentException(
					"itemId should not be null or empty.");
		}
		try {
			// 生成详情页URL
			String pageUrl = getDetailPageUrl(itemId);
			// 请求URL，并将response封装成Document对象
			Document doc = doRequestUrl(pageUrl);
			// 图书不存在
			if (page404(doc)) {
				return null;
			}
			// 抓取图书信息
			BookMetadata bi = parseMetadata(doc);
			return bi;
		} catch (Exception e) {
			throw new BookMetadataCaptureException("抓取数据失败，" + e.getMessage(),
					e);
		}
	}

	/**
	 * 请求URL，并将response封装成Document对象
	 * 
	 * @param pageUrl
	 * @return
	 * @throws Exception
	 */
	protected Document doRequestUrl(String pageUrl) {
		URL url;
		try {
			url = new URL(pageUrl);
		} catch (MalformedURLException e2) {
			throw new BookMetadataCaptureException("数据抓取过程中发生错误" + e2.getMessage(), e2);
		}
		int errCount = 0;
		Exception ex = null;
		// 如果网络超时，进行有限次尝试
		while (errCount < ERR_RETRY_COUNT) {
			try {
				Document doc = Jsoup.connect(pageUrl).header("User-Agent","Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2").timeout(TIMEOUT).get();
//				Document doc = Jsoup.parse(url, TIMEOUT);
				return doc;
			} catch (Exception e) {
				errCount++;
				ex = e;
				try {
					Thread.sleep(TIMEOUT_SLEEP);
				} catch (Exception e1) {// sleep quietly
				}
			}
		}
		throw new BookMetadataCaptureException("数据抓取过程中发生错误" + ex.getMessage(), ex);
	}

	/**
	 * 请求URL，直接展示response的内容
	 * @param url
	 * @param charset
	 * @return
	 */
	protected String doRequestUrlToPlainText(String url, String charset) {
		int errCount = 0;
		Exception ex = null;
		while(errCount < ERR_RETRY_COUNT) {
			try {
				return innerGetUrlContent(url, charset);
			} catch (Exception e) {
				ex = e;
				errCount++;
				try {
					Thread.sleep(TIMEOUT_SLEEP);
				} catch (Exception e1) {// sleep quietly
				}
			}
		}
		throw new BookMetadataCaptureException(ex);
	}

	private String innerGetUrlContent(String url, String charset)
			throws Exception {
		URL urlObj = new URL(url);
		HttpURLConnection httpConn = (HttpURLConnection) urlObj
				.openConnection();
		httpConn.setConnectTimeout(TIMEOUT);
		InputStreamReader input = new InputStreamReader(
				httpConn.getInputStream(), charset);
		BufferedReader bufReader = new BufferedReader(input);
		String line = "";
		StringBuilder contentBuf = new StringBuilder();
		while ((line = bufReader.readLine()) != null) {
			contentBuf.append(line);
		}
		return contentBuf.toString();
	}
	
	/**
	 * 获取重定向最终URL，如果不是重定向链接，返回null
	 * @param url
	 * @return
	 */
	protected String getRedirectUrl(String url) {
		int errCount = 0;
		Exception ex = null;
		while(errCount < ERR_RETRY_COUNT) {
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setInstanceFollowRedirects(false);
				conn.setConnectTimeout(5000);
				return conn.getHeaderField("Location");
			} catch (Exception e) {
				ex = e;
				errCount++;
			}
		}
		throw new BookMetadataCaptureException("抓取数据失败，" + ex.getMessage(), ex);
	}

	/**
	 * 生成商品详情页URL
	 * 
	 * @param itemId
	 * @return
	 */
	protected abstract String getDetailPageUrl(String itemId);

	/**
	 * 判断抓取的商品是否存在
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract boolean page404(Document doc);

	/**
	 * 解析页面信息，提取商品元数据
	 * 
	 * @param doc
	 * @return
	 */
	protected abstract BookMetadata parseMetadata(Document doc);
	
	/**
	 * 获取class唯一的元素，如果存在多个，会抛出异常
	 * @param doc
	 * @param classname
	 * @return
	 */
	protected Element getUniqueElementByClass(Document doc, String classname) {
		Elements elements = doc.getElementsByClass(classname);
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		if (elements.size() > 1) {
			String msg = String.format("[数据抓取]发生错误，class=%s的元素只能有一个，请检查网页是否发生变化", classname);
			throw new BookMetadataCaptureException(msg);
		}
		return elements.get(0);
	}
	
	/**
	 * 获取tag唯一的元素，如果存在多个，会抛出异常
	 * @param doc
	 * @param tag
	 * @return
	 */
	protected Element getUniqueElementByTag(Document doc, String tag) {
		Elements elements = doc.getElementsByTag(tag);
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		if (elements.size() > 1) {
			String msg = String.format("[数据抓取]发生错误，tag=%s的元素只能有一个，请检查网页是否发生变化", tag);
			throw new BookMetadataCaptureException(msg);
		}
		return elements.get(0);
	}
	
	// 如果出版社结尾没有“出版社”三个字，则自动加上
	protected String formatPublisher(String publisher) {
		if (StringUtils.isBlank(publisher)) {
			return publisher;
		}
		if (!publisher.endsWith("出版社")) {
			return publisher + "出版社";
		}
		return publisher;
	}
}
