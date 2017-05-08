package com.zis.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.storage.entity.StorageOrder;

public interface StorageOrderDao extends CrudRepository<StorageOrder, Integer>,
		PagingAndSortingRepository<StorageOrder, Integer>, JpaSpecificationExecutor<StorageOrder> {

	@Query("from StorageOrder where orderId in (:ids)")
	List<StorageOrder> findByOrderIds(@Param("ids") List<Integer> ids);
	
	StorageOrder findByOutOrderId(Integer outOrderId);

	/**
	 * 更新订单状态：processing->sent
	 * 
	 * @param batchId
	 * @return
	 */
	@Modifying
	@Query("update StorageOrder s set s.tradeStatus='sent' where s.tradeStatus='processing' and s.orderId in (select orderId from StorageIoDetail where batchId = :batchId)")
	Integer updateToSentByBatchId(@Param("batchId") Integer batchId);

    @Query("select distinct(o.outOrderId) from StorageOrder o, StorageIoDetail d where o.orderId=d.orderId and d.batchId=:batchId")
    List<Integer> findOutOrderIdsByBatchId(@Param("batchId") Integer batchId);
}
