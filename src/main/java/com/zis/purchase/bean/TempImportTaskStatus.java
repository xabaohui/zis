package com.zis.purchase.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * TempImportTask.status枚举值
 * @author yz
 *
 */
public class TempImportTaskStatus {

	/** 导入完成 */
	public static final Integer IMPORT_COMPLETE = 0;
	/** 匹配完成 */
	public static final Integer FULLY_MATCHED = 1;
	/** 操作成功 */
	public static final Integer SUCCESS = 2;
	/** 撤销操作 */
	public static final Integer CANCEL = 9;
	
	private static final Map<Integer, String> displayMap = new HashMap<Integer, String>();
	static{
		displayMap.put(IMPORT_COMPLETE, "导入成功");
		displayMap.put(FULLY_MATCHED, "匹配完成");
		displayMap.put(SUCCESS, "操作成功");
		displayMap.put(CANCEL, "撤销操作");
	}
	
	public static String getDisplay(Integer tempImportTaskStatus) {
		return displayMap.get(tempImportTaskStatus);
	}
}
