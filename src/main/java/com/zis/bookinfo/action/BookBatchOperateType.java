package com.zis.bookinfo.action;

/**
 * 图书批量操作类型
 * @author yz
 *
 */
public class BookBatchOperateType {

	/** 设置成相关图书 */
	public static final String SET_TO_RELATED = "setToRelated";
	/** 设置成不同版本的图书 */
	public static final String SET_TO_GROUP = "setToGroup";
	/** 批量删除 */
	public static final String BATCH_DELETE = "batchDelete";
	/** 批量拉黑 */
	public static final String BATCH_ADD_TO_BLACK_LIST = "batchAddToBlackList";
}
