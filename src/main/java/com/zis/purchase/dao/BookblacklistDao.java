package com.zis.purchase.dao;

import java.util.List;

import com.zis.purchase.bean.Bookblacklist;

public interface BookblacklistDao {

	public abstract void save(Bookblacklist transientInstance);

	public abstract void delete(Bookblacklist persistentInstance);

	public abstract Bookblacklist findById(java.lang.Integer id);

	public abstract List findByExample(Bookblacklist instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByBookId(Object bookId);

	public abstract List findByStatus(Object status);

	public abstract List findByVersion(Object version);

	public abstract List findAll();

	public abstract Bookblacklist merge(Bookblacklist detachedInstance);

	public abstract void attachDirty(Bookblacklist instance);

	public abstract void attachClean(Bookblacklist instance);
	
	public abstract void update(Bookblacklist instance);

}