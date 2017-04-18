package com.zis.storage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zis.storage.entity.StorageProductOccupy;

public interface StorageProductOccupyDao extends CrudRepository<StorageProductOccupy, Integer>{
	
	/**
	 * 按照订单Id查询库存占用记录
	 * @param orderId
	 * @return
	 */
	List<StorageProductOccupy> findByOrderId(Integer orderId);
	
	/**
	 * 按照订单Id和商品Id查询占用记录
	 * @param orderId
	 * @param productId
	 * @return
	 */
	//FIXME　如果同样skuId下了2次这边就会报错
	StorageProductOccupy findByOrderIdAndProductId(Integer orderId, Integer productId);
}
