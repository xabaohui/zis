package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.trade.entity.OrderDetail;

public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail, Integer>, JpaSpecificationExecutor<OrderDetail>{

	List<OrderDetail> findByOrderIdAndStatus(Integer orderId, String status);
	
	/**
	 * 更新子订单状态为无效
	 * @param orderId
	 * @return
	 */
	@Modifying
	@Query("update OrderDetail set status = 'invalid', version=version+1, updateTime=now() where status='valid' and orderId=:orderId")
	int updateStatusToInvalidByOrderId(@Param("orderId")Integer orderId);
}
