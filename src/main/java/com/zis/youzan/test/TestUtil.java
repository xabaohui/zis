package com.zis.youzan.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSON;
import com.zis.bookinfo.util.BookMetadata;
import com.zis.common.capture.DefaultBookMetadataCaptureHandler;
import com.zis.common.excel.ExcelImporter;
import com.zis.common.excel.FileImporter;
import com.zis.youzan.dto.AddItemDto;
import com.zis.youzan.response.ResultJsonToItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class TestUtil {

	private static final String APP_ID = "2371a1f04e67552040"; // 这里换成你的app_id
	private static final String APP_SECRET = "f9a4ae815439fb683a94b6dfe8b60504"; // 这里换成你的app_secret
	
	@Autowired
	private DefaultBookMetadataCaptureHandler bookMetadataCapture;
	
	@Test
	public void test() {
		AddItemDto dto = new AddItemDto();
		dto.setPrice(10.2);
		dto.setImagesUrl("http://news.shangqiuw.com/upload/News/2016-6-29/201662915530944bk3v5.jpg");
		dto.setOuterId("123123");
		dto.setTitle("测试商品");
		dto.setPostFee(10.2);
		dto.setDeliveryTemplateId(379054);
		dto.setDesc("sadasd222222");
		dto.setQuantity(123123);
		dto.setSkuQuantities(222);
		dto.setSkuPrices(10.2);
		add(dto);
	}

	public static void main(String[] args) {
		
	}

	@Test
	public void test1() {
		// 设置模板文件，用于检验导入文件是否合法
		Integer headerRownums = 1;
		File file = new File("E:/zis/有路网上新有赞2.xls");

		try {
			InputStream fileInputStream = new FileInputStream(file);
			// 初始化导入器
			FileImporter<TestDto> im = new ExcelImporter<TestDto>(fileInputStream, null);
			im.setHeaderRowNums(headerRownums);

			// 检验导入文件是否合法
			String errMsg = im.validate();
			if (StringUtils.isNotBlank(errMsg)) {
				// 失败
				System.out.println("失败1");
			}
			// 解析文件并入库
			Map<String, Integer> propMapping = initPropMapping();
			TestDto instance = new TestDto();
			List<TestDto> list = im.parse(instance, propMapping);
			if (list.isEmpty()) {
				System.out.println(list.size());
			}
			for (int i = 0; i < 11; i++) {
				System.out.println(list.get(i).getIsbn().length());
			}
			System.out.println(list.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void ssss(){
		BookMetadata meta = bookMetadataCapture.captureListPage("9787040254174");
		System.out.println("------------------------------------------------");
		System.out.println(meta);
		System.out.println("------------------------------------------------");
	}
	
	public void test2(){
		File file = new File("E:/zis/fail/fail.csv");
		InputStream in = null;
		OutputStream ou = null;
		try {
			// 根据文件创建文件的输入流
			in = new FileInputStream(file);
			// 创建字节数组
			byte[] data = new byte[1024];
			// 读取内容，放到字节数组里面
			in.read(data);
			String input = new String(data).trim();
			in.close();
			ou = new FileOutputStream(file);
			String add = "2e3123,12e231\n";
			System.out.println(input);
			String all = null;
			if (StringUtils.isBlank(input)) {
				all = add;
			} else {
				all = input + "\n" + add;
			}
			byte[] by = all.getBytes();
			ou.write(by);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭输入流
				// in.close();
				ou.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Map<String, Integer> initPropMapping() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("isbn", 0);
		map.put("amount", 1);
		return map;
	}

	private static void add(AddItemDto dto) {

		String method = "kdt.item.add";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("price", dto.getPrice().toString());
		params.put("title", dto.getTitle());
		params.put("desc", dto.getDesc());
		params.put("outer_id", dto.getOuterId().toString());
		params.put("is_used", "1");// 是否为二手
		params.put("quantity", dto.getQuantity().toString());// 商品数量
		params.put("is_display", "1");// 是否上架
		params.put("is_virtual", "0");// 是否为虚拟商品 0否
		params.put("post_fee", "10");
		params.put("delivery_template_id", "379054");// 运费模板
		params.put("sku_properties", "图书类型:二手书");
		params.put("sku_quantities", dto.getSkuQuantities().toString());
		params.put("sku_prices", dto.getSkuPrices().toString());
		// params.put("sku_outer_ids", "123-123-456");
		String fileKey = "images[]";

		KdtApiClient kdtApiClient;
		HttpResponse response;

		try {
			kdtApiClient = new KdtApiClient(APP_ID, APP_SECRET);
			response = kdtApiClient.post1(method, params, getBytes(dto.getImagesUrl()), fileKey);
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
}