package com.zis.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zis.storage.entity.StorageProduct;

public interface StorageProductDao extends JpaRepository<StorageProduct, Integer>{
	
	StorageProduct findBySkuIdAndRepoId(Integer skuId, Integer repoId);
}