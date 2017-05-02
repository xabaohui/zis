package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.trade.entity.OrderOuter;

public interface OrderOuterDao extends PagingAndSortingRepository<OrderOuter, Integer>, JpaSpecificationExecutor<OrderOuter>{

	@Query("select outOrderNumber from OrderOuter where orderId=:orderId")
	List<String> findOutOrderNumbersByOrderId(@Param("orderId")Integer orderId);
}
