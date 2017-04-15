package com.zis.shop.bo.impl;

import java.util.Map;

import com.zis.shop.bo.ShopDownloadBo;

/**
 * 下载商品class工厂
 * 
 * @author think
 * 
 */
public class ShopDownloadFactoryBo {

	private Map<String, ShopDownloadBo> shopDownloadItemsBoMap;

	/**
	 * 获取根据平台名称下载店铺商品bo
	 * 
	 * @param type
	 * @return
	 */
	public ShopDownloadBo getInstance(String type) {
		return shopDownloadItemsBoMap.get(type);
	}

	public void setShopDownloadItemsBoMap(Map<String, ShopDownloadBo> shopDownloadItemsBoMap) {
		this.shopDownloadItemsBoMap = shopDownloadItemsBoMap;
	}
}
