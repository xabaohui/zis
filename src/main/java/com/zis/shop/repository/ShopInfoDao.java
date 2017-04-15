package com.zis.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shop.bean.ShopInfo;

public interface ShopInfoDao extends PagingAndSortingRepository<ShopInfo, Integer> {

	final String NORMAL = "normal";
	final String DELETE = "delete";
	final String NEW = "new";

	@Query(value = "FROM ShopInfo WHERE companyId = :companyId AND status <> '" + DELETE + "'")
	List<ShopInfo> findByCompanyId(@Param("companyId") Integer companyId);

	@Query(value = "FROM ShopInfo WHERE shopId = :shopId AND companyId = :companyId AND status <> '" + DELETE + "'")
	ShopInfo findByCompanyIdAndShopId(@Param("companyId") Integer companyId, @Param("shopId") Integer shopId);

	@Query(value = "FROM ShopInfo WHERE pName = :pName AND companyId = :companyId AND shopName = :shopName AND status <> '"
			+ DELETE + "'")
	ShopInfo findByShopNameAndPNameAndCompanyId(@Param("shopName") String shopName, @Param("pName") String pName,
			@Param("companyId") Integer companyId);

}
