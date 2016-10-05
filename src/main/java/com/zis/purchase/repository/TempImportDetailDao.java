package com.zis.purchase.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zis.purchase.bean.TempImportDetail;

public interface TempImportDetailDao extends CrudRepository<TempImportDetail, Integer> {

	List<TempImportDetail> findByTaskIdAndStatus(Integer taskId, String status);
}
