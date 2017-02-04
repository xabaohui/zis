package com.zis.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shop.bean.ShopWaitUpload;

public interface ShopWaitUploadDao extends PagingAndSortingRepository<ShopWaitUpload, Integer> {

	final String SUCCESS = "success";
	final String WAIT = "wait";

	@Query(value = "SELECT swu FROM ShopWaitUpload swu WHERE swu.shopId = :shopId AND status = '" + WAIT + "'")
	public Page<ShopWaitUpload> findByShopId(@Param("shopId") Integer shopId, Pageable page);
}
