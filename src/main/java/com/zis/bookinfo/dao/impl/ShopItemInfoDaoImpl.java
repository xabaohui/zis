package com.zis.bookinfo.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.bookinfo.bean.ShopItemInfo;
import com.zis.bookinfo.dao.ShopItemInfoDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * ShopItemInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.bookinfo.bean.ShopItemInfo
 * @author MyEclipse Persistence Tools
 */

public class ShopItemInfoDaoImpl extends HibernateDaoSupport implements ShopItemInfoDao {
	private static final Logger log = LoggerFactory
			.getLogger(ShopItemInfoDaoImpl.class);
	// property constants
	public static final String OUT_ID = "outId";
	public static final String BOOK_ID = "bookId";
	public static final String ISBN = "isbn";
	public static final String SHOP_STATUS = "shopStatus";
	public static final String SHOP_NAME = "shopName";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(ShopItemInfo transientInstance) {
		log.debug("saving ShopItemInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(ShopItemInfo transientInstance) {
		log.debug("saving ShopItemInfo instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ShopItemInfo persistentInstance) {
		log.debug("deleting ShopItemInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.ShopItemInfoDao#findById(java.lang.Integer)
	 */
	public ShopItemInfo findById(java.lang.Integer id) {
		log.debug("getting ShopItemInfo instance with id: " + id);
		try {
			ShopItemInfo instance = (ShopItemInfo) getHibernateTemplate().get(
					"com.zis.bookinfo.dao.ShopItemInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.ShopItemInfoDao#findByExample(com.zis.bookinfo.bean.ShopItemInfo)
	 */
	public List findByExample(ShopItemInfo instance) {
		log.debug("finding ShopItemInfo instance by example");
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
		log.debug("finding ShopItemInfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ShopItemInfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOutId(Object outId) {
		return findByProperty(OUT_ID, outId);
	}

	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	public List findByShopStatus(Object shopStatus) {
		return findByProperty(SHOP_STATUS, shopStatus);
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.ShopItemInfoDao#findByShopName(java.lang.Object)
	 */
	public List findByShopName(Object shopName) {
		return findByProperty(SHOP_NAME, shopName);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all ShopItemInfo instances");
		try {
			String queryString = "from ShopItemInfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ShopItemInfo merge(ShopItemInfo detachedInstance) {
		log.debug("merging ShopItemInfo instance");
		try {
			ShopItemInfo result = (ShopItemInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ShopItemInfo instance) {
		log.debug("attaching dirty ShopItemInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ShopItemInfo instance) {
		log.debug("attaching clean ShopItemInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ShopItemInfoDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (ShopItemInfoDao) ctx.getBean("ShopItemInfoDAO");
	}

	public List findbyCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}
}