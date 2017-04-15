package com.zis.shop.bo;

import java.util.List;

import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.dto.ShopDownloadInterfaceDto;

public interface ShopBo {
	/**
	 * 库存变动更新或者添加网店商品
	 * 
	 * @param mapping
	 * @param shop
	 * @param amount
	 */
	public void stockChange2UpdateOrAddItems(ShopItemMapping mapping, ShopInfo shop, Integer amount);

	/**
	 * 异步全部发布商品
	 * 
	 * @param mappingIds
	 * @param shop
	 */
	public void abstractAddAllProcessingItems(final ShopInfo shop);

	/**
	 * 批量发布商品
	 * 
	 * @param mappingIds
	 * @param shop
	 */
	public void addProcessingItems(List<Integer> mappingIds, ShopInfo shop);

	/**
	 * 商品下载bo
	 * 
	 * @param list
	 * @param shop
	 */
	public void downloadItems2Mapping(ShopDownloadInterfaceDto dto, ShopInfo shop);
}
