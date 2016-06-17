package com.zis.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaobaoTest {
	
	private static String test(String url) {
		try {
			URL urlObj = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) urlObj
					.openConnection();
			httpConn.setConnectTimeout(3000);
			InputStreamReader input = new InputStreamReader(
					httpConn.getInputStream(), "gbk");
			BufferedReader bufReader = new BufferedReader(input);
			String line = "";
			StringBuilder contentBuf = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				contentBuf.append(line);
			}
			return contentBuf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		String content = test("https://detail.tmall.com/item.htm?id=36275931086");
		System.out.println(content);
	}
}
