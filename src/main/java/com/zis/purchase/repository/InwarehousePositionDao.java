package com.zis.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zis.purchase.bean.InwarehousePosition;

public interface InwarehousePositionDao extends CrudRepository<InwarehousePosition, Integer> {

	/**
	 * 查询所有可用库位，按照ID排序
	 * @param inwarehouseId
	 * @return
	 */
	@Query(value="from InwarehousePosition where inwarehouseId = :inId and isFull = false order by id asc")
	List<InwarehousePosition> findAvailablePosition(@Param(value="inId")Integer inwarehouseId);
}
