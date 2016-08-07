package com.zis.purchase.bean;

/**
 * 临时导入表的业务类型
 * @author yz
 *
 */
public enum TempImportTaskBizTypeEnum {
	
	STOCK, SHOP_STATUS, SHOP_TITLE, SHOP_CATEGORY_ID, TAOBAO_FORBIDDEN, BOOKINFO, PURCHASE;
	
	public String getValue() {
		switch(this) {
		case STOCK:
			return "stock_import";
		case SHOP_STATUS:
			return "shop_status";
		case SHOP_TITLE:
			return "shop_title";
		case SHOP_CATEGORY_ID:
			return "shop_category_id";
		case TAOBAO_FORBIDDEN:
			return "taobao_forbidden";
		case BOOKINFO:
			return "bookinfo";
		case PURCHASE:
			return "purchase";
		default:
				throw new RuntimeException("不支持的业务类型" + this);
		}
	}
	
	public String getDisplayValue() {
		switch(this) {
		case STOCK:
			return "库存导入";
		case SHOP_STATUS:
			return "网店商品状态更新";
		case SHOP_TITLE:
			return "网店标题更新";
		case SHOP_CATEGORY_ID:
			return "网店类目更新";
		case TAOBAO_FORBIDDEN:
			return "淘宝网禁止发布";
		case BOOKINFO:
			return "图书基本信息";
		case PURCHASE:
			return "采购数据导入";
		default:
				throw new RuntimeException("不支持的业务类型" + this);
		}
	}
	
	public static TempImportTaskBizTypeEnum parseEnum(String value) {
		for (TempImportTaskBizTypeEnum e : values()) {
			if(e.getValue().equals(value)) {
				return e;
			}
		}
		return null;
	}

}
