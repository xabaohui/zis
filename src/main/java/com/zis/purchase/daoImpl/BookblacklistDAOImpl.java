package com.zis.purchase.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.Bookblacklist;
import com.zis.purchase.dao.BookblacklistDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bookblacklist entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.Bookblacklist
 * @author MyEclipse Persistence Tools
 */

public class BookblacklistDAOImpl extends HibernateDaoSupport implements BookblacklistDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookblacklistDAOImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void update(Bookblacklist bl){
		getHibernateTemplate().update(bl);
	}
	
	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#save(com.zis.purchase.bean.Bookblacklist)
	 */
	public void save(Bookblacklist transientInstance) {
		log.debug("saving Bookblacklist instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#delete(com.zis.purchase.bean.Bookblacklist)
	 */
	public void delete(Bookblacklist persistentInstance) {
		log.debug("deleting Bookblacklist instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findById(java.lang.Integer)
	 */
	public Bookblacklist findById(java.lang.Integer id) {
		log.debug("getting Bookblacklist instance with id: " + id);
		try {
			Bookblacklist instance = (Bookblacklist) getHibernateTemplate()
					.get("com.zis.purchase.bean.Bookblacklist", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findByExample(com.zis.purchase.bean.Bookblacklist)
	 */
	public List findByExample(Bookblacklist instance) {
		log.debug("finding Bookblacklist instance by example");
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
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bookblacklist instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Bookblacklist as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findByBookId(java.lang.Object)
	 */
	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findByStatus(java.lang.Object)
	 */
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findByVersion(java.lang.Object)
	 */
	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Bookblacklist instances");
		try {
			String queryString = "from Bookblacklist";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#merge(com.zis.purchase.bean.Bookblacklist)
	 */
	public Bookblacklist merge(Bookblacklist detachedInstance) {
		log.debug("merging Bookblacklist instance");
		try {
			Bookblacklist result = (Bookblacklist) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#attachDirty(com.zis.purchase.bean.Bookblacklist)
	 */
	public void attachDirty(Bookblacklist instance) {
		log.debug("attaching dirty Bookblacklist instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.BookblacklistDao#attachClean(com.zis.purchase.bean.Bookblacklist)
	 */
	public void attachClean(Bookblacklist instance) {
		log.debug("attaching clean Bookblacklist instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookblacklistDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (BookblacklistDao) ctx.getBean("BookblacklistDAO");
	}
}