package com.zis.purchase.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.purchase.bean.Inwarehouse;

public interface InwarehouseDao extends PagingAndSortingRepository<Inwarehouse, Integer> {

}
