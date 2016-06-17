package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 入库业务类型
 * @author yz
 *
 */
public class InwarehouseBizType {

	public static final String PURCHASE = "purchase";
	public static final String RETURN = "return";
	public static final String DIRECT = "direct";
	
	public static final Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(InwarehouseBizType.PURCHASE, "采购入库");
		map.put(InwarehouseBizType.RETURN, "退货入库");
		map.put(InwarehouseBizType.DIRECT, "直接入库");
	}
	public static String getDisplay(String inwarehouseBizType) {
		return map.get(inwarehouseBizType);
	}
}
