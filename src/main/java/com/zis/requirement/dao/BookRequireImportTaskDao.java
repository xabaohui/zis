package com.zis.requirement.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.requirement.bean.BookRequireImportTask;

public interface BookRequireImportTaskDao {

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#save(com.zis.requirement.bean.TempBookRequireImport)
	 */
	public abstract void save(BookRequireImportTask transientInstance);

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#save(com.zis.requirement.bean.TempBookRequireImport)
	 */
	public abstract void update(BookRequireImportTask transientInstance);

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#findById(java.lang.Integer)
	 */
	public abstract BookRequireImportTask findById(java.lang.Integer id);

	public abstract List<BookRequireImportTask> findByCriteria(
			DetachedCriteria criteria);

}