package com.zis.youzan.test;

/**
 * 商品类目接口
 * @author think
 *
 */
public class YouZanCommodityTypeApi {

	/**
	 * 获取商品分类二维列表
	 */
	private final String KDT_ITEMCATEGORIES_GET = "kdt.itemcategories.get";

	/**
	 * 获取商品自定义标签列表
	 */
	private final String KDT_ITEMCATEGORIES_TAGS_GET = "kdt.itemcategories.tags.get";

	/**
	 * 更新一个商品标签
	 */
	private final String KDT_ITEMCATEGORIES_TAG_UPDATE = "kdt.itemcategories.tag.update";

	/**
	 * 删除一个商品标签
	 */
	private final String KDT_ITEMCATEGORIES_TAG_DELETE = "kdt.itemcategories.tag.delete";
	
	/**
	 * 新增一个商品标签
	 */
	private final String KDT_ITEMCATEGORIES_TAG_ADD = "kdt.itemcategories.tag.add";
	
	/**
	 * 分页获取商品自定义标签列表
	 */
	private final String KDT_ITEMCATEGORIES_TAGS_GETPAGE = "kdt.itemcategories.tags.getpage";
	
	/**
	 * 获取商品推广栏目列表
	 */
	private final String KDT_ITEMCATEGORIES_PROMOTIONS_GET = "kdt.itemcategories.promotions.get";
}
