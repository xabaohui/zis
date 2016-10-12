package com.zis.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanStatus;

public interface PurchasePlanDao extends PagingAndSortingRepository<PurchasePlan, Integer> {

	/**
	 * 作废指定bookId的采购计划
	 * @param bookId
	 */
	@Modifying
	@Query(value="update PurchasePlan set status='"+PurchasePlanStatus.USELESS+"', version=version+1, gmtModify = now() where status='"+PurchasePlanStatus.NORMAL+"' and bookId=:bookId")
	void updateToUselessByBookId(@Param(value="bookId")Integer bookId);
	
	/**
	 * 按照bookId查询采购计划，限定有效的记录
	 * @param bookId
	 * @return
	 */
	@Query(value="from PurchasePlan where bookId=:bookId and status='"+PurchasePlanStatus.NORMAL+"'")
	PurchasePlan findByBookId(@Param(value="bookId") Integer bookId);
	
	/**
	 * 按照bookId查询采购计划，查询所有状态
	 * @param bookId
	 * @return
	 */
	@Query(value="from PurchasePlan where bookId=:bookId")
	List<PurchasePlan> findByBookIdForAll(@Param(value="bookId") Integer bookId);
	
	/**
	 * 按照ISBN查询采购计划，限定有效的记录
	 * @return
	 */
	@Query(value="from PurchasePlan where isbn = :isbn and status='"+PurchasePlanStatus.NORMAL+"'")
	List<PurchasePlan> findByIsbn(@Param(value="isbn") String isbn);
	
	/**
	 * 查询采购计划，方便重新计算在途库存<p/>
	 * 查询status=NORMAL, purchasedAmount>0的记录
	 * @return
	 */
	@Query(value="from PurchasePlan where purchasedAmount > 0 and status='"+PurchasePlanStatus.NORMAL+"'")
	List<PurchasePlan> findForRecalcOnwayStock();
	
}
