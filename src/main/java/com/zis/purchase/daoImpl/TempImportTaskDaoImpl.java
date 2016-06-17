package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.TempImportTask;
import com.zis.purchase.dao.TempImportTaskDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * TempImportTask entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.TempImportTask
 * @author MyEclipse Persistence Tools
 */

public class TempImportTaskDaoImpl extends HibernateDaoSupport implements TempImportTaskDao {
	private static final Logger log = LoggerFactory
			.getLogger(TempImportTaskDaoImpl.class);
	// property constants
	public static final String BIZ_TYPE = "bizType";
	public static final String MEMO = "memo";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportTaskDao#save(com.zis.purchase.bean.TempImportTask)
	 */
	public void save(TempImportTask transientInstance) {
		log.debug("saving TempImportTask instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportTaskDao#delete(com.zis.purchase.bean.TempImportTask)
	 */
	public void delete(TempImportTask persistentInstance) {
		log.debug("deleting TempImportTask instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportTaskDao#findById(java.lang.Integer)
	 */
	public TempImportTask findById(java.lang.Integer id) {
		log.debug("getting TempImportTask instance with id: " + id);
		try {
			TempImportTask instance = (TempImportTask) getHibernateTemplate().get(TempImportTask.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.TempImportTaskDao#findByExample(com.zis.purchase.bean.TempImportTask)
	 */
	public List findByExample(TempImportTask instance) {
		log.debug("finding TempImportTask instance by example");
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
		log.debug("finding TempImportTask instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TempImportTask as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBizType(Object bizType) {
		return findByProperty(BIZ_TYPE, bizType);
	}

	public List findByMemo(Object memo) {
		return findByProperty(MEMO, memo);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all TempImportTask instances");
		try {
			String queryString = "from TempImportTask";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TempImportTask merge(TempImportTask detachedInstance) {
		log.debug("merging TempImportTask instance");
		try {
			TempImportTask result = (TempImportTask) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TempImportTask instance) {
		log.debug("attaching dirty TempImportTask instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TempImportTask instance) {
		log.debug("attaching clean TempImportTask instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TempImportTaskDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (TempImportTaskDao) ctx.getBean("TempImportTaskDAO");
	}

	public List findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}
}