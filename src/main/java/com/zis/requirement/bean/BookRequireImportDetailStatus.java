package com.zis.requirement.bean;

import java.util.HashMap;
import java.util.Map;

public class BookRequireImportDetailStatus {

	public static final String BOOK_NOT_MATCHED = "book_not_matched";
	public static final String DEPARTMENT_NOT_MATCHED = "department_not_matched";
	public static final String ALL_MATCHED = "all_matched";
	public static final String IMPORT_SUCCESS = "import_success";
	
	private static Map<String, String> map = new HashMap<String, String>();
	static {
		map.put(DEPARTMENT_NOT_MATCHED, "专业未匹配");
		map.put(BOOK_NOT_MATCHED, "图书未匹配");
		map.put(ALL_MATCHED, "匹配成功");
		map.put(IMPORT_SUCCESS, "导入成功");
	}
	
	public static String getDisplay(String status) {
		return map.get(status);
	}
}
