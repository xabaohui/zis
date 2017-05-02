package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.trade.entity.Order;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer>, JpaSpecificationExecutor<Order>{

	List<Order> findByShopIdAndReceiverNameAndReceiverPhoneAndReceiverAddr(Integer shopId, String receiverName, String receiverPhone, String receiverAddr);
	
	List<Order> findByRepoIdAndExpressNumberAndExpressStatus(Integer repoId, String expressNumber, String expressStatus);
}
