package com.zis.requirement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.requirement.bean.Departmentinfo;

public interface DepartmentInfoDao {

	public abstract void save(Departmentinfo transientInstance);

	public abstract Departmentinfo findById(java.lang.Integer id);

	public abstract List<Departmentinfo> findByExample(Departmentinfo instance);

	public abstract List<Departmentinfo> findByProperty(String propertyName, Object value);

	public abstract List<Departmentinfo> findByPartName(Object partName);

	public abstract List<Departmentinfo> findByInstitute(Object institute);

	public abstract List<Departmentinfo> findByCollege(Object college);

	public abstract List<Departmentinfo> findAll();
	
	public abstract void update(Departmentinfo departmentinfo);
	
	public abstract void saveOrUpdate(Departmentinfo departmentinfo);
	
	public abstract List<Departmentinfo> findByCriteria(DetachedCriteria dc);

}