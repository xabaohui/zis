package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.PurchasePlan;

public interface PurchasePlanDao {

	public abstract void save(PurchasePlan transientInstance);

	public abstract PurchasePlan findById(java.lang.Integer id);

	public abstract List<PurchasePlan> findByExample(PurchasePlan instance);

	public abstract PurchasePlan findByBookId(Integer bookId);

	public abstract List<PurchasePlan> findAll();

	public abstract List<PurchasePlan> findbyCriteria(DetachedCriteria dc);

	public abstract void update(PurchasePlan ra);
	
	public void attachDirty(PurchasePlan instance);
}