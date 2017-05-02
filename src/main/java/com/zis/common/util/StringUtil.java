package com.zis.common.util;

public class StringUtil {

	/**
	 * 字符串拼接
	 * @param conj 拼接字符
	 * @param strings 待拼接的字符串
	 * @return
	 */
	public static String conjunction(String conj, String ...strings) {
		StringBuilder builder = new StringBuilder();
		for (String str : strings) {
			builder.append(str).append(conj);
		}
		return builder.substring(0, builder.length()-conj.length()).toString();
	}
}
