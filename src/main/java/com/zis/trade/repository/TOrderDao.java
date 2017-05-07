package com.zis.trade.repository;

import org.springframework.data.repository.CrudRepository;

import com.zis.trade.entity.TOrder;

public interface TOrderDao extends CrudRepository<TOrder, Integer> {

}
