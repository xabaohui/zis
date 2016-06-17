package com.zis.requirement.dao;

import java.util.List;

import com.zis.requirement.bean.SysVar;

public interface SysVarDao {

	public abstract void save(SysVar transientInstance);

	public abstract void delete(SysVar persistentInstance);

	public abstract SysVar findById(java.lang.Integer id);

	public abstract List findByExample(SysVar instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByDepKey(Object depKey);

	public abstract List findByDepValue(Object depValue);

	public abstract List findAll();

	public abstract SysVar merge(SysVar detachedInstance);

	public abstract void attachDirty(SysVar instance);

	public abstract void attachClean(SysVar instance);
	
	public abstract void update(SysVar detachedInstance);

}