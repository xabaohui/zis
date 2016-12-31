package com.zis.youzan.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;

import com.zis.common.util.ImageUtils;

/*
 * 这是个例子
 */
public class KDTApiTest {
	private static final String APP_ID = "2371a1f04e67552040"; // 这里换成你的app_id
	private static final String APP_SECRET = "f9a4ae815439fb683a94b6dfe8b60504"; // 这里换成你的app_secret

	public static void main(String[] args) {
		// sendGet();
		// sendPost();
		// get();
		String[] urls = new String[1];
		urls[0] = "http://s1.51cto.com/images/201609/867f777736dd156c0da612ce7afc5104210544.png";
		add(urls);
	}

	/*
	 * 测试获取单个商品信息
	 */
	private static void sendGet() {
		String method = "kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("num_iid", "321540209");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取仓库商品信息
	 */
	private static void get() {
		String method = "kdt.items.onsale.get";
		HashMap<String, String> params = new HashMap<String, String>();
		// params.put("num_iid", "2651514");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				result.append(line);
			}

			// System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取添加商品
	 */
	private static void sendPost() {
		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "999.01");
		params.put("title", "测试商品");
		params.put("desc", "这是一个号商铺");
		params.put("is_virtual", "0");
		params.put("post_fee", "10.01");
		params.put("sku_properties", "");
		params.put("sku_quantities", "");
		params.put("sku_prices", "");
		params.put("sku_outer_ids", "");
		String fileKey = "images[]";
		List<String> filePaths = new ArrayList<String>();
		filePaths.add("/Users/xuexiaozhe/Desktop/1.png");
		filePaths.add("/Users/xuexiaozhe/Desktop/2.png");

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.post(method, params, filePaths, fileKey);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 测试获取添加商品
	 */
	private static void add(String[] urls) {
		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "999.01");
		params.put("title", "测试商品");
		params.put("desc", "这是一个号商铺");
		params.put("is_virtual", "0");
		params.put("post_fee", "10.01");
		params.put("sku_properties", "");
		params.put("sku_quantities", "");
		params.put("sku_prices", "");
		params.put("sku_outer_ids", "123");
		String fileKey = "images[]";

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.post1(method, params, getBytes(urls), fileKey);
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			String[] s =result.toString().split(",");
			for (String s1 : s) {
				System.out.println(s1);
			}
//			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[][] getBytes(String[] urlString) {
		byte[][] bys = new byte[urlString.length][];
		for (int i = 0; i < bys.length; i++) {
			for (String urls : urlString) {
				try {
					// 构造URL
					URL url = new URL(urls);
					// 打开连接
					URLConnection con = url.openConnection();
					// 设置请求超时为5s
					con.setConnectTimeout(5 * 1000);
					// 输入流
					InputStream is = con.getInputStream();
					// 转换为byte 数组
					bys[i] = input2byte(is);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bys;
	}

	private static byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}
