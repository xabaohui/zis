package com.zis.requirement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.requirement.bean.Bookamount;

public interface BookAmountDao {

	public abstract void save(Bookamount transientInstance);

	public abstract Bookamount findById(java.lang.Integer id);

	public abstract List<Bookamount> findByExample(Bookamount instance);

	public abstract List<Bookamount> findByIsbn(Object isbn);

	public abstract List<Bookamount> findAll();

	public abstract List<Bookamount> findByCriteria(DetachedCriteria dc);

	public abstract List<Bookamount> findByBookId(int bookId);
	
	public List findBySql(String sql);
}