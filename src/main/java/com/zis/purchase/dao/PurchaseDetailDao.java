package com.zis.purchase.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.purchase.bean.PurchaseDetail;

public interface PurchaseDetailDao {

	public abstract void save(PurchaseDetail transientInstance);

	public abstract void delete(PurchaseDetail persistentInstance);

	public abstract PurchaseDetail findById(java.lang.Integer id);

	public abstract List<PurchaseDetail> findByExample(PurchaseDetail instance);

	public abstract List<PurchaseDetail> findByProperty(String propertyName, Object value);

	public abstract List<PurchaseDetail> findByBookId(Object bookId);

	public abstract List<PurchaseDetail> findByPurchasedAmount(Object purchasedAmount);

	public abstract List<PurchaseDetail> findByBatchId(Object batchId);

	public abstract List<PurchaseDetail> findByMemo(Object memo);

	public abstract List<PurchaseDetail> findByStatus(Object status);

	public abstract List<PurchaseDetail> findByOperator(Object operator);

	public abstract List<PurchaseDetail> findByVersion(Object version);

	public abstract List<PurchaseDetail> findAll();

	public abstract PurchaseDetail merge(PurchaseDetail detachedInstance);

	public abstract void attachDirty(PurchaseDetail instance);

	public abstract void attachClean(PurchaseDetail instance);
	
	public abstract List<PurchaseDetail> findByCriteria(DetachedCriteria dc);

}