package com.zis.bookinfo.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.bookinfo.bean.YouluSales;
import com.zis.bookinfo.dao.YouluSalesDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * YouluSales entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zis.bookinfo.bean.YouluSales
 * @author MyEclipse Persistence Tools
 */

public class YouluSalesDAOImpl extends HibernateDaoSupport implements YouluSalesDao {
	private static final Logger log = LoggerFactory
			.getLogger(YouluSalesDAOImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String OUT_ID = "outId";
	public static final String STOCK_BALANCE = "stockBalance";
	public static final String BOOK_PRICE = "bookPrice";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.YouluSalesDao#save(com.zis.bookinfo.bean.YouluSales)
	 */
	public void save(YouluSales transientInstance) {
		log.debug("saving YouluSales instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(YouluSales persistentInstance) {
		log.debug("deleting YouluSales instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.YouluSalesDao#findById(java.lang.Integer)
	 */
	public YouluSales findById(java.lang.Integer id) {
		log.debug("getting YouluSales instance with id: " + id);
		try {
			YouluSales instance = (YouluSales) getHibernateTemplate().get(
					"com.zis.bookinfo.dao.YouluSales", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.YouluSalesDao#findByExample(com.zis.bookinfo.bean.YouluSales)
	 */
	public List findByExample(YouluSales instance) {
		log.debug("finding YouluSales instance by example");
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
		log.debug("finding YouluSales instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from YouluSales as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.YouluSalesDao#findByBookId(java.lang.Object)
	 */
	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.YouluSalesDao#findByOutId(java.lang.Object)
	 */
	public List findByOutId(Object outId) {
		return findByProperty(OUT_ID, outId);
	}

	public List findByStockBalance(Object stockBalance) {
		return findByProperty(STOCK_BALANCE, stockBalance);
	}

	public List findByBookPrice(Object bookPrice) {
		return findByProperty(BOOK_PRICE, bookPrice);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all YouluSales instances");
		try {
			String queryString = "from YouluSales";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public YouluSales merge(YouluSales detachedInstance) {
		log.debug("merging YouluSales instance");
		try {
			YouluSales result = (YouluSales) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(YouluSales instance) {
		log.debug("attaching dirty YouluSales instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(YouluSales instance) {
		log.debug("attaching clean YouluSales instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static YouluSalesDao getFromApplicationContext(ApplicationContext ctx) {
		return (YouluSalesDao) ctx.getBean("YouluSalesDAO");
	}
}