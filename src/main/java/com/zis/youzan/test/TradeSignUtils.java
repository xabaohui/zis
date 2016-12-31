package com.zis.youzan.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 订单系统签名工具类
 * 
 * @author lvbin
 * 
 *         2016年11月14日
 * 
 */
public class TradeSignUtils {

	/** 默认的签名字段的key */
	public static final String DEF_SIGN_LABEL = "sign";
	public static final String DEF_SIGN_TYPE_LABEL = "signType";

	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * MD5签名
	 * 
	 * @param params
	 * @param secretKey
	 * @param charset
	 * @return
	 */
	public static String signByMd5(Map<String, String> params, String secretKey, String charset) {
		return signByMd5(params, secretKey, charset, DEF_SIGN_LABEL);
	}

	/**
	 * MD5签名
	 * 
	 * @param params
	 * @param secretKey
	 * @param charset
	 * @param signLabel
	 *            签名字段的key
	 * @return
	 */
	public static String signByMd5(Map<String, String> params, String secretKey, String charset, String signLabel) {
		// 过滤空值、sign与sign_type参数
		// 把数组所有元素排序，并按照"key1=val1&key2=val2&key3=val3"字符串
		String text = createLinkString(params, signLabel) + secretKey;
		int i = 0;
		while (i++ < 500) {
			text = DigestUtils.md5Hex(getContentBytes(text, charset));
		}
		return text;
	}

	/**
	 * 
	 * 功能描述：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 */
	private static String createLinkString(Map<String, String> params, String signLabel) {
		if (params == null || params.isEmpty()) {
			return "";
		}
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);// 排序

		// 拼接时，不包括最后一个&字符
		StringBuilder builder = new StringBuilder();
		for (String key : keys) {
			// 过滤数组中的空值和签名参数
			if (key.equalsIgnoreCase(DEF_SIGN_LABEL)) {
				continue;
			}
			if (key.equalsIgnoreCase(DEF_SIGN_TYPE_LABEL)) {
				continue;
			}
			if (params.get(key) == null || params.get(key).equals("") || params.get(key).equals("null")) {
				continue;
			}
			builder.append(key).append("=").append(params.get(key)).append("&");
		}
		return builder.substring(0, builder.length() - 1);
	}

	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}
}
