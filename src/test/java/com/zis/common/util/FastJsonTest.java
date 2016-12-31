package com.zis.common.util;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.zis.bookinfo.bean.Bookinfo;

public class FastJsonTest {

	@Test
	public void test() {
		Bookinfo book = new Bookinfo();
		book.setBookName("三只松鼠");
		book.setBookPrice(100.00);
		// 对象转json（序列化）
		String jsonStr = JSONObject.toJSONString(book);
		System.out.println(jsonStr);
		// json转对象（反序列化）
		Bookinfo bookinfo = JSONObject.parseObject(jsonStr, Bookinfo.class);
	}
}
