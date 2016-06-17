package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.Storesales;
import com.zis.purchase.dao.StoreSalesDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Storesales entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.Storesales
 * @author MyEclipse Persistence Tools
 */

public class StoresalesDAOImpl extends HibernateDaoSupport implements StoreSalesDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoresalesDAOImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String SALES = "sales";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#save(com.zis.purchase.bean.Storesales)
	 */
	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.StoreSalesDao#save(com.zis.purchase.bean.Storesales)
	 */
	public void save(Storesales transientInstance) {
		log.debug("saving Storesales instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#delete(com.zis.purchase.bean.Storesales)
	 */
	public void delete(Storesales persistentInstance) {
		log.debug("deleting Storesales instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findById(java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.StoreSalesDao#findById(java.lang.Integer)
	 */
	public Storesales findById(java.lang.Integer id) {
		log.debug("getting Storesales instance with id: " + id);
		try {
			Storesales instance = (Storesales) getHibernateTemplate().get(
					"com.zis.purchase.bean.Storesales", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findByExample(com.zis.purchase.bean.Storesales)
	 */
	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.StoreSalesDao#findByExample(com.zis.purchase.bean.Storesales)
	 */
	public List findByExample(Storesales instance) {
		log.debug("finding Storesales instance by example");
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

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Storesales instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Storesales as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findByBookId(java.lang.Object)
	 */
	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.StoreSalesDao#findByBookId(java.lang.Object)
	 */
	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findBySales(java.lang.Object)
	 */
	public List findBySales(Object sales) {
		return findByProperty(SALES, sales);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Storesales instances");
		try {
			String queryString = "from Storesales";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#merge(com.zis.purchase.bean.Storesales)
	 */
	public Storesales merge(Storesales detachedInstance) {
		log.debug("merging Storesales instance");
		try {
			Storesales result = (Storesales) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#attachDirty(com.zis.purchase.bean.Storesales)
	 */
	public void attachDirty(Storesales instance) {
		log.debug("attaching dirty Storesales instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.StoresalesDao#attachClean(com.zis.purchase.bean.Storesales)
	 */
	public void attachClean(Storesales instance) {
		log.debug("attaching clean Storesales instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}