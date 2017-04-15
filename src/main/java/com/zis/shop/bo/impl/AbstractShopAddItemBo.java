package com.zis.shop.bo.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.zis.shop.bean.ShopItemMapping;
import com.zis.shop.bean.ShopItemMapping.ShopItemMappingSystemStatus;
import com.zis.shop.bo.ShopAddItemsBo;
import com.zis.shop.repository.ShopItemMappingDao;

public abstract class AbstractShopAddItemBo implements ShopAddItemsBo {

	@Autowired
	private ShopItemMappingDao dao;

	/**
	 * 添加成功更新映射表信息
	 * 
	 * @param numIid
	 * @param mapping
	 * @return
	 */
	protected void successUpdateMapping(Long numIid, ShopItemMapping mapping) {
		mapping.setUpdateTime(new Date());
		mapping.setUploadTime(new Date());
		mapping.setSystemStatus(ShopItemMappingSystemStatus.SUCCESS.getValue());
		mapping.setFailReason("");
		mapping.setpItemId(numIid);
		this.dao.save(mapping);
	}
	
	/**
	 * 等待下载更新映射表信息
	 * 
	 * @param numIid
	 * @param mapping
	 * @return
	 */
	protected void waitDownloadUpdateMapping(ShopItemMapping mapping) {
		mapping.setUpdateTime(new Date());
		mapping.setUploadTime(new Date());
		mapping.setSystemStatus(ShopItemMappingSystemStatus.WAIT_DOWNLOAD.getValue());
		mapping.setFailReason(ShopItemMappingSystemStatus.WAIT_DOWNLOAD.getName());
		this.dao.save(mapping);
	}

	/**
	 * 发布失败更新映射表
	 * @param msg
	 * @param mapping
	 */
	protected void failUpdateMapping(String msg, ShopItemMapping mapping) {
		mapping.setUpdateTime(new Date());
		mapping.setSystemStatus(ShopItemMappingSystemStatus.FAIL.getValue());
		mapping.setFailReason(msg);
		this.dao.save(mapping);
	}
}
