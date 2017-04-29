package com.zis.trade.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.trade.entity.OrderLog;

public interface OrderLogDao extends PagingAndSortingRepository<OrderLog, Integer>, JpaSpecificationExecutor<OrderLog>{

}
