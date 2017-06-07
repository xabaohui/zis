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

	Page<Order> findByCompanyIdAndPayStatusOrderByUpdateTimeDesc(Integer companyId, String payStatus, Pageable page);

	Page<Order> findByCompanyIdAndExpressStatus(Integer companyId, String expressStatus, Pageable page);

	Page<Order> findByCompanyIdAndExpressStatusAndStorageStatusNotInAndPayStatusNotInOrderByUpdateTimeDesc(
			Integer companyId, String expressStatus, List<String> storageStatusList, List<String> payStatus,
			Pageable page);

	Page<Order> findByCompanyIdAndStorageStatusAndPayStatusNotInOrderByUpdateTimeDesc(Integer companyId,
			String storageStatus, List<String> payStatus, Pageable page);

	Page<Order> findByCompanyIdAndStorageStatusInAndPayStatusNotInOrderByUpdateTimeDesc(Integer companyId,
			List<String> storageStatusList, List<String> payStatus, Pageable page);

	Order findByIdAndCompanyId(Integer id, Integer companyId);

	List<Order> findByShopIdAndOrderGroupNumber(Integer shopId, String orderGroupNumber);

	Page<Order> findByCompanyIdOrderByUpdateTimeDesc(Integer companyId, Pageable page);

	List<Order> findByCompanyIdAndOrderGroupNumberInOrderByUpdateTimeDesc(Integer companyId,
			List<String> orderGroupNumbers);

	// XXX 临时方法，如果订单数据模糊化的问题搞定了，此方法可删除
	List<Order> findByOrderGroupNumberIn(List<String> orderGroupNumbers);

	Order findByOrderGroupNumberAndPayStatusNotIn(String orderGroupNumber, List<String> payStatus);

	List<Order> findByShopIdAndReceiverNameAndReceiverPhoneAndReceiverAddr(Integer shopId, String receiverName,
			String receiverPhone, String receiverAddr);

	List<Order> findByRepoIdAndExpressNumberAndExpressStatus(Integer repoId, String expressNumber, String expressStatus);

	List<Order> findByRepoIdAndExpressNumber(Integer repoId, String expressNumber);

	@Modifying
	@Query("update Order set storageStatus='pickup_finish' where storageStatus='pickup' and id in (:ids)")
	int updateStorageStatusToFinishByIds(@Param("ids") List<Integer> orderIds);
}
