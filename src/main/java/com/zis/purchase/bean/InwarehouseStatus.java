package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 入库记录状态
 * @author yz
 *
 */
public class InwarehouseStatus {

	public static final String PROCESSING = "processing";
	public static final String SUCCESS = "success";
	public static final String CANCEL = "cancel";
	
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(InwarehouseStatus.PROCESSING, "处理中");
		map.put(InwarehouseStatus.SUCCESS, "成功");
		map.put(InwarehouseStatus.CANCEL, "已取消");
	}
	
	public static final String getDisplay(String inwarehouseStatus) {
		return map.get(inwarehouseStatus);
	}
}
