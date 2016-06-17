package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.TempImportDetail;

public interface TempImportDetailDao {

	public abstract void save(TempImportDetail transientInstance);

	public abstract void delete(TempImportDetail persistentInstance);

	public abstract TempImportDetail findById(java.lang.Integer id);

	public abstract List findByExample(TempImportDetail instance);

	public abstract List findByTaskId(Object taskId);
	
	public abstract List findByCriteria(DetachedCriteria criteria);
}