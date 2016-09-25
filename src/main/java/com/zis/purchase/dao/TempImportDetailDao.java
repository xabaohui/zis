package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.TempImportDetail;

public interface TempImportDetailDao {

	public abstract void save(TempImportDetail transientInstance);

	public abstract void delete(TempImportDetail persistentInstance);

	public abstract TempImportDetail findById(java.lang.Integer id);

	public abstract List<TempImportDetail> findByExample(TempImportDetail instance);

	public abstract List<TempImportDetail> findByTaskId(Object taskId);
	
	public abstract List<TempImportDetail> findByCriteria(DetachedCriteria criteria);
}