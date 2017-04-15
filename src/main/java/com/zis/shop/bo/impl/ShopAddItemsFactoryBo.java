package com.zis.shop.bo.impl;

import java.util.Map;

import com.zis.shop.bo.ShopAddItemsBo;

/**
 * 新增商品到店铺bo工厂
 * 
 * @author think
 * 
 */
public class ShopAddItemsFactoryBo {
	
	private Map<String, ShopAddItemsBo> shopAddItemsBoMap;

	/**
	 * 获取根据平台名称获取新增商品到店铺bo
	 * 
	 * @param type
	 * @return
	 */
	public ShopAddItemsBo getInstance(String type) {
		return shopAddItemsBoMap.get(type);
	}

	public void setShopAddItemsBoMap(Map<String, ShopAddItemsBo> shopAddItemsBoMap) {
		this.shopAddItemsBoMap = shopAddItemsBoMap;
	}

}
