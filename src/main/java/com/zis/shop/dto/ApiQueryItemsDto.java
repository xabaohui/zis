package com.zis.shop.dto;

import java.util.List;

import com.zis.shop.bean.ShopItemMapping;

public class ApiQueryItemsDto {
	
	private List<ShopItemMapping> shopItemMappings;
	
	private  Long totalResults;//返回总数量

	public Long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}

	public List<ShopItemMapping> getShopItemMappings() {
		return shopItemMappings;
	}

	public void setShopItemMappings(List<ShopItemMapping> shopItemMappings) {
		this.shopItemMappings = shopItemMappings;
	}

}
