package com.zis.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;

public interface PurchaseDetailDao extends CrudRepository<PurchaseDetail, Integer> {
	
	/**
	 * 计算特定图书在途数量总和
	 * @param bookId
	 * @return
	 */
	@Query(value="select sum(purchasedAmount - inwarehouseAmount) from PurchaseDetail where status = '"+PurchaseDetailStatus.PURCHASED+"' and bookId = :bookId")
	Integer sumPurchasedAmount(@Param(value="bookId") Integer bookId);
	
	/**
	 * 按照操作员和状态查询
	 * @param operator
	 * @param status
	 * @return
	 */
	List<PurchaseDetail> findByOperatorAndStatus(String operator, String status);

	/**
	 * 按照操作员、状态、bookId查询
	 * @param operator
	 * @param status
	 * @return
	 */
	@Query(value = "from PurchaseDetail where operator=:opr and status=:st and bookId=:bookId")
	List<PurchaseDetail> findByOperatorAndStatusAndBookId(
			@Param(value = "opr") String operator,
			@Param(value = "st") String status,
			@Param(value = "bookId") Integer bookId);
}
