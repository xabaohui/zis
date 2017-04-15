package com.zis.storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zis.storage.entity.StorageIoDetail;

public interface StorageIoDetailDao extends CrudRepository<StorageIoDetail, Integer>,
		JpaSpecificationExecutor<StorageIoDetail> {

	List<StorageIoDetail> findByBatchId(Integer batchId);

	List<StorageIoDetail> findByBatchIdAndDetailStatus(Integer batchId, String detailStatus);
	
	List<StorageIoDetail> findByOrderId(Integer orderId);
	
	List<StorageIoDetail> findByBatchIdAndSkuIdAndPosId(Integer batchId, Integer skuId, Integer posId);
	
	@Modifying
	@Query("update StorageIoDetail set detailStatus='cancel' where batchId=:batchId")
	int batchCancel(@Param("batchId") Integer batchId);
	
	/**
	 * 查找(下一条)等待取件的记录
	 * @return
	 */
	@Query(nativeQuery=true,
			value="select * from storage_io_detail where io_detail_type='outwarehouse' and detail_status='waiting' and batch_id=:batchId order by pos_label asc limit 1")
	StorageIoDetail findNextRecordForPickup(@Param("batchId")Integer batchId);
	
	/**
	 * 查找指定批次指定操作员锁定的正在处理的记录
	 * @param batchId
	 * @param operator
	 * @return
	 */
	@Query(nativeQuery=true,
			value="select * from storage_io_detail where io_detail_type='outwarehouse' and detail_status='processing' and batch_id=:batchId and operator=:operator order by gmt_modify asc limit 1")
	StorageIoDetail findProcessingRecordByBatchIdAndOperator(@Param("batchId")Integer batchId, @Param("operator")Integer operator);
}
