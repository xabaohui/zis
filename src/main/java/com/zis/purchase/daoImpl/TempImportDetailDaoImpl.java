package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.TempImportDetail;
import com.zis.purchase.dao.TempImportDetailDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * TempImportDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.TempImportDetail
 * @author MyEclipse Persistence Tools
 */

public class TempImportDetailDaoImpl extends HibernateDaoSupport implements TempImportDetailDao {
	private static final Logger log = LoggerFactory
			.getLogger(TempImportDetailDaoImpl.class);
	// property constants
	public static final String ISBN = "isbn";
	public static final String ORIG_ISBN = "origIsbn";
	public static final String ROW_NUM = "rowNum";
	public static final String AMOUNT = "amount";
	public static final String BOOK_ID = "bookId";
	public static final String TASK_ID = "taskId";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportDetailDao#save(com.zis.purchase.bean.TempImportDetail)
	 */
	public void save(TempImportDetail transientInstance) {
		log.debug("saving TempImportDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportDetailDao#delete(com.zis.purchase.bean.TempImportDetail)
	 */
	public void delete(TempImportDetail persistentInstance) {
		log.debug("deleting TempImportDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportDetailDao#findById(java.lang.Integer)
	 */
	public TempImportDetail findById(java.lang.Integer id) {
		log.debug("getting TempImportDetail instance with id: " + id);
		try {
			TempImportDetail instance = (TempImportDetail) getHibernateTemplate().get(TempImportDetail.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportDetailDao#findByExample(com.zis.purchase.bean.TempImportDetail)
	 */
	public List findByExample(TempImportDetail instance) {
		log.debug("finding TempImportDetail instance by example");
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
	
	public List findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TempImportDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TempImportDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	public List findByOrigIsbn(Object origIsbn) {
		return findByProperty(ORIG_ISBN, origIsbn);
	}

	public List findByRowNum(Object rowNum) {
		return findByProperty(ROW_NUM, rowNum);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportDetailDao#findByTaskId(java.lang.Object)
	 */
	public List findByTaskId(Object taskId) {
		return findByProperty(TASK_ID, taskId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all TempImportDetail instances");
		try {
			String queryString = "from TempImportDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TempImportDetail merge(TempImportDetail detachedInstance) {
		log.debug("merging TempImportDetail instance");
		try {
			TempImportDetail result = (TempImportDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TempImportDetail instance) {
		log.debug("attaching dirty TempImportDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TempImportDetail instance) {
		log.debug("attaching clean TempImportDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TempImportDetailDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (TempImportDetailDao) ctx.getBean("TempImportDetailDAO");
	}
}