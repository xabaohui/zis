package com.zis.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zis.storage.entity.StorageProduct;

public interface StorageProductDao extends JpaRepository<StorageProduct, Integer> {

	StorageProduct findBySkuIdAndRepoId(Integer skuId, Integer repoId);

	@Query("from StorageProduct where skuId in (:skuIds) and repoId=:repoId")
	List<StorageProduct> findBySkuIdsAndRepoId(@Param("skuIds") List<Integer> skuIds, @Param("repoId") Integer repoId);

	List<StorageProduct> findByRepoIdAndProductIdInOrderBySkuIdAsc(Integer repoId, List<Integer> productIds);
	
}