package com.zis.requirement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.requirement.bean.BookAmount;

public interface BookAmountDao {

	public abstract void save(BookAmount transientInstance);

	public abstract BookAmount findById(java.lang.Integer id);

	public abstract List<BookAmount> findByExample(BookAmount instance);

	public abstract List<BookAmount> findByIsbn(Object isbn);

	public abstract List<BookAmount> findAll();

	public abstract List<BookAmount> findByCriteria(DetachedCriteria dc);

	public abstract List<BookAmount> findByBookId(int bookId);
	
	public List findBySql(String sql);
}