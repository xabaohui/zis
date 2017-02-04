package com.zis.youzan.test;

import java.io.BufferedReader;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;
import com.zis.youzan.response.ResultJsonToItem;
import com.zis.youzan.response.ResultJsonToItemList;

/*
 * 这是个例子
 */
public class KDTApiTest {
	private static final String APP_ID = "2371a1f04e67552040"; // 这里换成你的app_id
	private static final String APP_SECRET = "f9a4ae815439fb683a94b6dfe8b60504"; // 这里换成你的app_secret

	public static void main(String[] args) {
		// sendGet();
		// sendPost();
		// // Long pageNo = Long.valueOf(1);
		get1();
		// System.out.println("18444717:1671967:\u56fe\u4e66\u7c7b\u578b:\u4e8c\u624b\u4e66");
		// System.out.println("\u6d4b\u8bd5123\u5546\u54c1");
		// System.out.println("\u627e\u4e0d\u5230\u5546\u54c1");
		// System.out.println("\u5546\u54c1\u6570\u5b57\u7f16\u53f7\u548c\u5546\u54c1\u522b\u540d\u5fc5\u987b\u9009\u5176\u4e00");
		// String[] urls = new String[1];
		// urls[0] =
		// "http://news.shangqiuw.com/upload/News/2016-6-29/201662915530944bk3v5.jpg";
		// add(urls);
		// post1();
		// System.out.println("\u5546\u54c1\u4ef7\u683c\u5fc5\u987b\u5728Sku\u4ef7\u683c\u533a\u95f4\u5185");
	}

	private static Double get(Long pageNo) {
		String method = "kdt.items.onsale.get";
		// String method = "kdt.items.inventory.get";
		// String method = "kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page_size", "50");
		params.put("page_no", "" + pageNo);
		params.put("fields", "num_iid");
		// params.put("banner", "for_shelved");
		// params.put("num_iid", "12323322");
		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, params);
			System.out.println(response.getEntity().getContentEncoding());
			System.out.println(response.getEntity().getContentType());
			System.out.println(response.getEntity().getContentLength());
			System.out.println(response.getEntity().getContentEncoding());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
			String text = result.toString();
			ResultJsonToItemList item = JSONObject.parseObject(text, ResultJsonToItemList.class);
			System.out.println(item);
			// It i = item.getR().getIt();
			// Item i1 = new Item();
			// BeanUtils.copyProperties(i, i1);
			// System.out.println(i1);
			return Double.longBitsToDouble(item.getResponse().getTotalResults());
		} catch (Exception e) {
			return 0.0;
		}
	}

	private static void get1() {
		String method = "kdt.items.onsale.get";
//		String method = "kdt.items.inventory.get";
		// String method = "kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page_size", "50");
		params.put("page_no", "1");
		// params.put("fields", "num_iid");
		// params.put("banner", "for_shelved");
		// params.put("num_iid", "12323322");
		String json = getJson(method, params);
		ResultJsonToItemList re = JSON.parseObject(json, ResultJsonToItemList.class);
		System.out.println(re);
		System.out.println(re.getResponse().getItems().size());
	}

	/*
	 * 测试获取仓库商品信息
	 */
	private static void post1() {
		String method = "kdt.items.inventory.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("fields", "");
		// params.put("order_by", "column:asc");
		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.post2(method, params);
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
	private static void add(String... urls) {

		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "100.02");
		params.put("title", "测试123商品");
		params.put("desc", "此类型为测试商品无需做处理 看看就好我只是测试数据而已，别当真");
		params.put("outer_id", "123-123");
		params.put("is_used", "1");// 是否为二手
		params.put("quantity", "2");// 商品数量
		params.put("is_display", "0");// 是否上架
		params.put("is_virtual", "0");// 是否为虚拟商品 0否
		params.put("post_fee", "10.01");
		params.put("delivery_template_id", "379054");// 运费模板
		params.put("sku_properties", "图书类型:二手书");
		params.put("sku_quantities", "200");
		params.put("sku_prices", "100.02");
		// params.put("sku_outer_ids", "123-123-456");
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
			String json = "";
			json = result.toString();
			ResultJsonToItem s = JSON.parseObject(json, ResultJsonToItem.class);

			// TestBean s = JSONObject.parseObject(json, TestBean.class);
			// System.out.println(s);
			System.out.println(result.toString());
			System.out.println(s);
			// String[] s =result.toString().split(",");
			// for (String s1 : s) {
			// System.out.println(s1);
			// }
			// System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static byte[][] getBytes(String... urlString) {
		byte[][] bys = new byte[urlString.length][];
		for (int i = 0; i < bys.length; i++) {
			try {
				// 构造URL
				URL url = new URL(urlString[i]);
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

	/*
	 * 测试获取添加商品
	 */
	private static void sendPost() {
		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", "999.01");
		params.put("title", "测试商品");
		params.put("desc", "这是一个号商铺");
		params.put("outer_id", "xp123");
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
	 * 测试获取单个商品信息
	 */
	private static void sendGet() {
		String method = "kdt.item.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("outer_id", "XP");

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

	private static String getJson(String method, HashMap<String, String> parames) {
		KdtApiClient kdtApiClient;
		HttpResponse response;
		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.get(method, parames);
			System.out.println(response.getEntity().getContentEncoding());
			System.out.println(response.getEntity().getContentType());
			System.out.println(response.getEntity().getContentLength());
			System.out.println(response.getEntity().getContentEncoding());
			System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			// StringBuffer result = new StringBuffer();
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			// System.out.println(result.toString());
			String text = result.toString();
			// It i = item.getR().getIt();
			// Item i1 = new Item();
			// BeanUtils.copyProperties(i, i1);
			// System.out.println(i1);
			return text;
		} catch (Exception e) {
			return null;
		}
	}
}
