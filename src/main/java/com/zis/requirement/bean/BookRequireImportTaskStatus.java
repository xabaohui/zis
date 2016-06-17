package com.zis.requirement.bean;

import java.util.HashMap;
import java.util.Map;

public class BookRequireImportTaskStatus {

	public static final String NOT_MATCHED = "not_matched";
	public static final String MATCHED = "matched";
	public static final String IMPORT_SUCCESS = "import_success";
	
	private static final Map<String, String> map = new HashMap<String, String>();
	static{
		map.put(NOT_MATCHED, "未匹配");
		map.put(MATCHED, "匹配完成");
		map.put(IMPORT_SUCCESS, "导入完成");
	}
	
	public static String getDisplay(String status) {
		return map.get(status);
	}
}
