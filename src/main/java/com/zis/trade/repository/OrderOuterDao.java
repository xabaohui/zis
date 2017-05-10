package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.trade.entity.OrderOuter;

public interface OrderOuterDao extends PagingAndSortingRepository<OrderOuter, Integer>,
		JpaSpecificationExecutor<OrderOuter> {

	@Query("select outOrderNumber from OrderOuter where orderGroupNumber=:orderGroupNumber")
	List<String> findOutOrderNumbersByOrderId(@Param("orderGroupNumber") String orderGroupNumber);

	@Query("select orderGroupNumber from OrderOuter where outOrderNumber=:outOrderNumber")
	List<String> findOrderGroupNumberByOutOrderNumber(@Param("outOrderNumber") String outOrderNumber);

	List<OrderOuter> findByOrderGroupNumber(String orderGroupNumber);

	OrderOuter findByShopIdAndOutOrderNumber(Integer shopId, String outOrderNumber);
}
