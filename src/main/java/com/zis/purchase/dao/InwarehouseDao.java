package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.Inwarehouse;

public interface InwarehouseDao {

	public void save(Inwarehouse transientInstance);

	public Inwarehouse findById(java.lang.Integer id);

	public List findByExample(Inwarehouse instance);
	
	public List findByCriteria(DetachedCriteria criteria);
}