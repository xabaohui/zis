package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 采购计划标记，包括黑名单、白名单、正常三种
 * @author yz
 *
 */
public class PurchasePlanFlag {

	public static final String NORMAL = "normal";
	public static final String BLACK = "black";
	public static final String WHITE = "white";
	private static final Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(NORMAL, "正常");
		map.put(BLACK, "黑名单");
		map.put(WHITE, "白名单");
	}
	
	public static String getDisplay(String purchasePlanStatus) {
		return map.get(purchasePlanStatus);
	}
}
