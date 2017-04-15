package com.zis.shop.dto;

import com.zis.shop.bean.ShopItemMapping;

public class ApiUpdateItemDto extends ShopItemMapping{

	private Integer amount;
	
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
