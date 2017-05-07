package com.zis.trade.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.trade.entity.Order;

public interface OrderDao extends PagingAndSortingRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

	Page<Order> findByCompanyIdAndPayStatus(Integer companyId, String payStatus, Pageable page);

	Page<Order> findByCompanyIdAndExpressStatus(Integer companyId, String expressStatus, Pageable page);
	
	Page<Order> findByCompanyIdAndStorageStatus(Integer companyId, String storageStatus, Pageable page);
	
	Order findByIdAndCompanyId(Integer id, Integer companyId);
	
	List<Order> findByShopIdAndOrderGroupNumber(Integer shopId, String orderGroupNumber);

	List<Order> findByCompanyIdAndOrderGroupNumberIn(Integer companyId, List<String> orderGroupNumbers);

	// XXX 临时方法，如果订单数据模糊化的问题搞定了，此方法可删除
	List<Order> findByOrderGroupNumberIn(List<String> orderGroupNumbers);
	
	List<Order> findByShopIdAndReceiverNameAndReceiverPhoneAndReceiverAddr(Integer shopId, String receiverName, String receiverPhone, String receiverAddr);
	
	List<Order> findByRepoIdAndExpressNumberAndExpressStatus(Integer repoId, String expressNumber, String expressStatus);
	
	@Modifying
	@Query("update Order set storageStatus='pickup_finish' where repoId=:repoId and orderGroupNumber in (:gn)")
	int updateStorageStatusToFinishByRepoIdAndOrderGroupNumbers(@Param("repoId")Integer repoId, @Param("gn")List<String> orderGroupNumber);
}
