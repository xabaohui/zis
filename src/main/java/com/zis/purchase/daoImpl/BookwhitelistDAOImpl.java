package com.zis.purchase.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.Bookwhitelist;
import com.zis.purchase.dao.BookwhitelistDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bookwhitelist entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.Bookwhitelist
 * @author MyEclipse Persistence Tools
 */

public class BookwhitelistDAOImpl extends HibernateDaoSupport implements BookwhitelistDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookwhitelistDAOImpl.class);
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#save(com.zis.purchase.bean.Bookwhitelist)
	 */
	public void save(Bookwhitelist transientInstance) {
		log.debug("saving Bookwhitelist instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(Bookwhitelist bl){
		getHibernateTemplate().update(bl);
	}
	
	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#delete(com.zis.purchase.bean.Bookwhitelist)
	 */
	public void delete(Bookwhitelist persistentInstance) {
		log.debug("deleting Bookwhitelist instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findById(java.lang.Integer)
	 */
	public Bookwhitelist findById(java.lang.Integer id) {
		log.debug("getting Bookwhitelist instance with id: " + id);
		try {
			Bookwhitelist instance = (Bookwhitelist) getHibernateTemplate()
					.get("com.zis.purchase.bean.Bookwhitelist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findByExample(com.zis.purchase.bean.Bookwhitelist)
	 */
	public List findByExample(Bookwhitelist instance) {
		log.debug("finding Bookwhitelist instance by example");
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
	 * @see com.zis.purchase.bean.BookwhitelistDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bookwhitelist instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Bookwhitelist as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findByBookId(java.lang.Object)
	 */
	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findByStatus(java.lang.Object)
	 */
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findByVersion(java.lang.Object)
	 */
	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Bookwhitelist instances");
		try {
			String queryString = "from Bookwhitelist";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#merge(com.zis.purchase.bean.Bookwhitelist)
	 */
	public Bookwhitelist merge(Bookwhitelist detachedInstance) {
		log.debug("merging Bookwhitelist instance");
		try {
			Bookwhitelist result = (Bookwhitelist) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#attachDirty(com.zis.purchase.bean.Bookwhitelist)
	 */
	public void attachDirty(Bookwhitelist instance) {
		log.debug("attaching dirty Bookwhitelist instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.bean.BookwhitelistDao#attachClean(com.zis.purchase.bean.Bookwhitelist)
	 */
	public void attachClean(Bookwhitelist instance) {
		log.debug("attaching clean Bookwhitelist instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookwhitelistDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (BookwhitelistDao) ctx.getBean("BookwhitelistDAO");
	}
}