package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.InwarehouseDetail;

public interface InwarehouseDetailDao {

	public void save(InwarehouseDetail transientInstance);

	public InwarehouseDetail findById(java.lang.Integer id);

	public List findByExample(InwarehouseDetail instance);

	public List findByInwarehouseId(Object inwarehouseId);

	public List findByCriteria(DetachedCriteria criteria);

	public void delete(InwarehouseDetail detail);
}