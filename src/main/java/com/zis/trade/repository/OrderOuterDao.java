package com.zis.trade.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.trade.entity.OrderOuter;

public interface OrderOuterDao extends PagingAndSortingRepository<OrderOuter, Integer>, JpaSpecificationExecutor<OrderOuter>{

}
