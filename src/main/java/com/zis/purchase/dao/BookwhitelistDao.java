package com.zis.purchase.dao;

import java.util.List;

import com.zis.purchase.bean.Bookwhitelist;

public interface BookwhitelistDao {

	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	public abstract void save(Bookwhitelist transientInstance);

	public abstract void delete(Bookwhitelist persistentInstance);

	public abstract Bookwhitelist findById(java.lang.Integer id);

	public abstract List findByExample(Bookwhitelist instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByBookId(Object bookId);

	public abstract List findByStatus(Object status);

	public abstract List findByVersion(Object version);

	public abstract List findAll();

	public abstract Bookwhitelist merge(Bookwhitelist detachedInstance);

	public abstract void attachDirty(Bookwhitelist instance);

	public abstract void attachClean(Bookwhitelist instance);
	
	public abstract void update(Bookwhitelist instance);

}