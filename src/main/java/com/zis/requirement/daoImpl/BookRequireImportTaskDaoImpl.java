package com.zis.requirement.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.requirement.bean.BookRequireImportTask;
import com.zis.requirement.dao.BookRequireImportTaskDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * TempBookRequireImport entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.requirement.bean.BookRequireImportTask
 * @author MyEclipse Persistence Tools
 */

public class BookRequireImportTaskDaoImpl extends HibernateDaoSupport implements BookRequireImportTaskDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookRequireImportTaskDaoImpl.class);
	// property constants
	public static final String OPERATOR = "operator";
	public static final String MEMO = "memo";
	public static final String TOTAL_COUNT = "totalCount";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#save(com.zis.requirement.bean.TempBookRequireImport)
	 */
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#save(com.zis.requirement.bean.TempBookRequireImport)
	 */
	public void save(BookRequireImportTask transientInstance) {
		log.debug("saving TempBookRequireImport instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#save(com.zis.requirement.bean.TempBookRequireImport)
	 */
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#update(com.zis.requirement.bean.TempBookRequireImport)
	 */
	public void update(BookRequireImportTask transientInstance) {
		log.debug("update TempBookRequireImport instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(BookRequireImportTask persistentInstance) {
		log.debug("deleting TempBookRequireImport instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#findById(java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#findById(java.lang.Integer)
	 */
	public BookRequireImportTask findById(java.lang.Integer id) {
		log.debug("getting TempBookRequireImport instance with id: " + id);
		try {
			BookRequireImportTask instance = (BookRequireImportTask) getHibernateTemplate()
					.get(BookRequireImportTask.class,
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDao#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<BookRequireImportTask> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public List findByExample(BookRequireImportTask instance) {
		log.debug("finding TempBookRequireImport instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TempBookRequireImport instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TempBookRequireImport as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	public List findByMemo(Object memo) {
		return findByProperty(MEMO, memo);
	}

	public List findByTotalCount(Object totalCount) {
		return findByProperty(TOTAL_COUNT, totalCount);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all TempBookRequireImport instances");
		try {
			String queryString = "from TempBookRequireImport";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BookRequireImportTask merge(BookRequireImportTask detachedInstance) {
		log.debug("merging TempBookRequireImport instance");
		try {
			BookRequireImportTask result = (BookRequireImportTask) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BookRequireImportTask instance) {
		log.debug("attaching dirty TempBookRequireImport instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BookRequireImportTask instance) {
		log.debug("attaching clean TempBookRequireImport instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}