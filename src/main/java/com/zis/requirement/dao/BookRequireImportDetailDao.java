package com.zis.requirement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.requirement.bean.BookRequireImportDetail;

public interface BookRequireImportDetailDao {

	public abstract void save(BookRequireImportDetail transientInstance);

	public abstract void update(BookRequireImportDetail transientInstance);

	public abstract BookRequireImportDetail findById(java.lang.Integer id);

	@SuppressWarnings("unchecked")
	public abstract List<BookRequireImportDetail> findByCriteria(
			DetachedCriteria criteria);

}