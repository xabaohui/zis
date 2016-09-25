package com.zis.bookinfo.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.bookinfo.bean.ShopItemInfo;

public interface ShopItemInfoDao {

	public ShopItemInfo findById(java.lang.Integer id);

	public List<ShopItemInfo> findByExample(ShopItemInfo instance);

	public List<ShopItemInfo> findByShopName(Object shopName);

	public void save(ShopItemInfo instance);
	
	public void update(ShopItemInfo instance);

	public List<ShopItemInfo> findbyCriteria(DetachedCriteria criteria);
}