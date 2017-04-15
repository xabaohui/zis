package com.zis.shop.bo;

import java.util.List;

import com.zis.shop.bean.ShopInfo;
import com.zis.shop.dto.ApiAddItemDto;

/**
 * 新增商品到店铺bo
 * 
 * @author think
 * 
 */
public interface ShopAddItemsBo {

	/**
	 * 添加商品到店铺
	 * @param list
	 * @param shop
	 */
	public void addItems2Shop(List<ApiAddItemDto> list, ShopInfo shop);
}
