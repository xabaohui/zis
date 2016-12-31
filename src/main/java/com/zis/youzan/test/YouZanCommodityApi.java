package com.zis.youzan.test;

/**
 * 商品接口
 * 
 * @author think
 * 
 */
public class YouZanCommodityApi {

	/**
	 * 删除一个商品
	 */
	public static  KdtItemDelete KDT_ITEM_DELETE;
	
	public class KdtItemDelete{
		public static final String name = "123";

		public String getName() {
			return name;
		}
	}

	/**
	 * 更新单个商品信息
	 */
	private static final String KDT_ITEM_UPDATE = "kdt.item.update";

	/**
	 * 获取单个商品信息
	 */
	private static final String KDT_ITEM_GET = "kdt.item.get";

	/**
	 * 商品下架
	 */
	private static final String KDT_ITEM_UPDATE_DELISTING = "kdt.item.update.delisting";

	/**
	 * 商品上架
	 */
	private static final String KDT_ITEM_UPDATE_LISTING = "kdt.item.update.listing";

	/**
	 * 批量上架商品
	 */
	private static final String KDT_ITEMS_UPDATE_LISTING = "kdt.items.update.listing";

	/**
	 * 批量下架商品
	 */
	private static final String KDT_ITEMS_UPDATE_DELISTING = "kdt.items.update.delisting";

	/**
	 * 根据外部编号取商品Sku
	 */
	private static final String KDT_SKUS_CUSTOM_GET = "kdt.skus.custom.get";

	/**
	 * 获取出售中的商品列表
	 */
	private static final String KDT_ITEMS_ONSALE_GET = "kdt.items.onsale.get";

	/**
	 * 获取仓库中的商品列表
	 */
	private static final String KDT_ITEMS_INVENTORY_GET = "kdt.items.inventory.get";

	/**
	 * 根据商品货号获取商品
	 */
	private static final String KDT_ITEMS_CUSTOM_GET = "kdt.items.custom.get";

	/**
	 * 更新SKU信息
	 */
	private static final String KDT_ITEM_SKU_UPDATE = "kdt.item.sku.update";

	/**
	 * 新增一个商品
	 */
	private static final String KDT_ITEM_ADD = "kdt.item.add";

}
