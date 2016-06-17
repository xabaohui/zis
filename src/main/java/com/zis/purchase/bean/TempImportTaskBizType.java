package com.zis.purchase.bean;

/**
 * 临时导入表的业务类型
 * @author yz
 *
 */
public class TempImportTaskBizType {

	/** 查询图书基本信息 */
	public static final String BOOK_INFO = "bookinfo";
	/** 库存导入 */
	public static final String STOCK_IMPORT = "stock_import";
	/** 采购入库 */
	public static final String PURCHASE = "purchase";
	
	public static String getBizTypeDisplay(String bizType) {
		if(BOOK_INFO.equals(bizType)) {
			return "图书基本信息";
		} else if(STOCK_IMPORT.equals(bizType)) {
			return "库存导入";
		} else if(PURCHASE.equals(bizType)) {
			return "采购入库";
		} else {
			return null;
		}
	}
}
