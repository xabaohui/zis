package com.zis.common.util;

import java.util.Random;

public class TestUtil {
	
	public static String randomStr(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(new Random().nextInt(10));
		}
		return builder.toString();
	}
}
