package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.trade.entity.Order;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

	Order findByOrderIdAndCompanyId(Integer orderId, Integer companyId);
	
	List<Order> findByShopIdAndReceiverNameAndReceiverPhoneAndReceiverAddr(Integer shopId, String receiverName, String receiverPhone, String receiverAddr);
	
	List<Order> findByRepoIdAndExpressNumberAndExpressStatus(Integer repoId, String expressNumber, String expressStatus);
	
	@Modifying
	@Query("update Order set storageStatus='pickup_finish' where repoId=:repoId and orderGroupNumber in (:gn)")
	int updateStorageStatusToFinishByRepoIdAndOrderGroupNumbers(@Param("repoId")Integer repoId, @Param("gn")List<String> orderGroupNumber);
}
