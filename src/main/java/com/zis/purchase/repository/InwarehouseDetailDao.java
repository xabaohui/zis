package com.zis.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.purchase.bean.InwarehouseDetail;

public interface InwarehouseDetailDao extends PagingAndSortingRepository<InwarehouseDetail, Integer>,
		JpaSpecificationExecutor<InwarehouseDetail> {

	/**
	 * 按照入库单ID集合查询入库详情
	 * 
	 * @param inwarehouseId
	 * @return
	 */
	@Query(value = "from InwarehouseDetail where inwarehouseId in (:ids)")
	List<InwarehouseDetail> findByInwarehouseIds(@Param(value = "ids") Integer[] inwarehouseIds);
}
