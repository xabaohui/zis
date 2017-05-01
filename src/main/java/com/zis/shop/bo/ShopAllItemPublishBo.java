package com.zis.shop.bo;

import java.util.List;

import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.dto.ApiAddItemDto;

/**
 * 全部发布商品bo
 * @author think
 *
 */
public interface ShopAllItemPublishBo {

	/**
	 * 每500一批发布数据
	 * @param pageList
	 * @param shop
	 * @return
	 */
	List<ApiAddItemDto> allItemPublish(List<ShopItemMapping> bookList, ShopInfo shop);
}
