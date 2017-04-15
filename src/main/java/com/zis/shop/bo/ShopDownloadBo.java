package com.zis.shop.bo;

import java.util.List;

import com.zis.shop.bean.ShopInfo;
import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.dto.ShopDownloadInterfaceDto;

/**
 * 网店下载商品bo
 * @author think
 *
 */
public interface ShopDownloadBo {
	
	/**
	 * 下载商品组装成ShopItemMapping list
	 * @param list
	 * @param shop
	 * @return
	 */
	public List<ShopItemMapping> downloadItems(ShopDownloadInterfaceDto dto, ShopInfo shop);
}
