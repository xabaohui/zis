package com.zis.requirement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.bean.BookRequireImportDetailStatus;

public interface BookRequireImportDetailDao extends
		PagingAndSortingRepository<BookRequireImportDetail, Integer> {

	/**
	 * 根据batchId查询 状态限定为book_not_matched
	 * 
	 * @param batchId
	 * @return
	 */
	@Query("from BookRequireImportDetail where status = '"
			+ BookRequireImportDetailStatus.BOOK_NOT_MATCHED
			+ "' and  batchId = :batchId")
	List<BookRequireImportDetail> findByBatchId(@Param(value = "batchId") Integer batchId);
}