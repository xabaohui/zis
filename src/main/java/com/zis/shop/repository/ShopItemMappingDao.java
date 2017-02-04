package com.zis.shop.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.shop.bean.ShopItemMapping;

public interface ShopItemMappingDao extends PagingAndSortingRepository<ShopItemMapping, Integer> {
}
