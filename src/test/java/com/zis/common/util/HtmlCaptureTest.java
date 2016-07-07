package com.zis.common.util;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlCaptureTest {

	public static void main(String[] args) {
		new HtmlCaptureTest().getHtmlData();
	}

	public void getHtmlData() {
		String pattern = "第\\d{3}讲";// 活动标题  
		try {
			URL url = new URL("http://flyhigher.hunnu.edu.cn/channels/399.html");
			Document doc = Jsoup.parse(url, 100000);
			Elements element = doc.getElementsByTag("a");
			for (Element e : element) {
				String linkText = e.text();
				if (regularMatch(pattern, linkText)!=null) {
					System.out.println("linkText: " + linkText);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 传入2个字符串参数 一个是pattern(我们使用的正则) 另一个matcher是html源代码
	public String regularMatch(String pattern, String matcher) {
		Pattern p = Pattern.compile(pattern, Pattern.COMMENTS);
		Matcher m = p.matcher(matcher);
		if (m.find()) { // 如果读到
			return m.group();// 返回捕获的数据
		} else {
			return null; // 否则返回一个空值
		}
	}
}
