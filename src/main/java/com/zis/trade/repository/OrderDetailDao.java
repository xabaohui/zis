package com.zis.trade.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.trade.entity.OrderDetail;

public interface OrderDetailDao extends PagingAndSortingRepository<OrderDetail, Integer>, JpaSpecificationExecutor<OrderDetail>{

}
