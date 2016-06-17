package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

public class PurchasePlanStatus {

	public static final String NORMAL = "normal";
	public static final String USELESS = "useless";
	private static final Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(NORMAL, "正常");
		map.put(USELESS, "废弃");
	}
	
	public static String getDisplay(String purchasePlanStatus) {
		return map.get(purchasePlanStatus);
	}
}
