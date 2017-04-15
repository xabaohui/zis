package com.zis.shop.dto;

import com.zis.bookinfo.dto.BookInfoAndDetailV2DTO;
import com.zis.shop.bean.ShopItemMapping;

public class ApiAddItemDto extends BookInfoAndDetailV2DTO{
	
	private static final long serialVersionUID = 375697539301674982L;
	
	private ShopItemMapping shopItemMapping;

	public ShopItemMapping getShopItemMapping() {
		return shopItemMapping;
	}

	public void setShopItemMapping(ShopItemMapping shopItemMapping) {
		this.shopItemMapping = shopItemMapping;
	}
}
