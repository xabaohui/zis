package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.TempImportTask;

public interface TempImportTaskDao {

	public void save(TempImportTask transientInstance);

	public void delete(TempImportTask persistentInstance);

	public TempImportTask findById(java.lang.Integer id);

	public List findByExample(TempImportTask instance);
	
	public List findByCriteria(DetachedCriteria criteria);

}