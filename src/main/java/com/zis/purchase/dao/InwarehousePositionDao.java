package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.InwarehousePosition;

public interface InwarehousePositionDao {

	public void save(InwarehousePosition transientInstance);

	public InwarehousePosition findById(java.lang.Integer id);

	public List<InwarehousePosition> findByExample(InwarehousePosition instance);

	public List<InwarehousePosition> findByInwarehouseId(Object inwarehouseId);

	public List<InwarehousePosition> findByCriteria(DetachedCriteria criteria);
}