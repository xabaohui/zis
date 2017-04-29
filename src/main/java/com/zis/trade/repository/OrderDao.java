package com.zis.trade.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.trade.entity.Order;


public interface OrderDao extends PagingAndSortingRepository<Order, Integer>, JpaSpecificationExecutor<Order>{

}
