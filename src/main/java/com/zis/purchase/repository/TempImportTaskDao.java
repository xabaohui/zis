package com.zis.purchase.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.purchase.bean.TempImportTask;

public interface TempImportTaskDao extends PagingAndSortingRepository<TempImportTask, Integer> {

	
}
