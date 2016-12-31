package com.zis.purchase.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.bean.TempImportTaskStatus;

public interface TempImportTaskDao extends PagingAndSortingRepository<TempImportTask, Integer> {
	
	/**
	 * 分页查询 以status 降序 gmtCreate 升序 并且 status 不为TempImportTaskStatus.CANCEL 
	 * @param page
	 * @return
	 */
	@Query(value = "select b from TempImportTask b where status <> 9 order by gmtCreate desc")
	Page<TempImportTask> findAllTempImportTask(Pageable page);
}
