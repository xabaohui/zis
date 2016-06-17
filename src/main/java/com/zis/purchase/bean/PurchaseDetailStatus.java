package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 采购单状态
 * @author yz
 *
 */
public class PurchaseDetailStatus {

	public static final String PURCHASED = "purchased";
	public static final String CHECKED = "checked";
	public static final String USELESS = "useless";
	
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(PURCHASED, "已采购");
		map.put(CHECKED, "已入库");
		map.put(USELESS, "已废弃");
	}
	
	public static String getDisplay(String purchaseOrderStatus) {
		return map.get(purchaseOrderStatus);
	}
}
