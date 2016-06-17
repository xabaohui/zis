package com.zis.bookinfo.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 图书状态
 * @author yz
 *
 */
public class BookinfoStatus {

	public static final String NORMAL = "正式";
	public static final String WAITCHECK = "待审核";
	public static final String DISCARD = "废弃";
	
	private static Map<String, String> displayMap = new HashMap<String, String>();
	static {
		displayMap.put(NORMAL, BookinfoStatus.NORMAL);
		displayMap.put(WAITCHECK, BookinfoStatus.WAITCHECK);
		displayMap.put(DISCARD, BookinfoStatus.DISCARD);
	}
	public static String getDisplay(String status) {
		return displayMap.get(status);
	}
}
