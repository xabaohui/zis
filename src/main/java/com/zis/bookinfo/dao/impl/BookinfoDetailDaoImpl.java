package com.zis.bookinfo.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.bookinfo.bean.BookinfoDetail;
import com.zis.bookinfo.dao.BookinfoDetailDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * BookinfoDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.demo.BookinfoDetail
 * @author MyEclipse Persistence Tools
 */

public class BookinfoDetailDaoImpl extends HibernateDaoSupport implements BookinfoDetailDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookinfoDetailDaoImpl.class);
	// property constants
	public static final String IMAGE_URL = "imageUrl";
	public static final String TAOBAO_TITLE = "taobaoTitle";
	public static final String TAOBAO_CATAGORY_ID = "taobaoCatagoryId";
	public static final String SUMMARY = "summary";
	public static final String CATALOG = "catalog";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoDetailDao#save(com.zis.bookinfo.bean.BookinfoDetail)
	 */
	@Override
	public void save(BookinfoDetail transientInstance) {
		log.debug("saving BookinfoDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoDetailDao#update(com.zis.bookinfo.bean.BookinfoDetail)
	 */
	@Override
	public void update(BookinfoDetail transientInstance) {
		log.debug("saving BookinfoDetail instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BookinfoDetail persistentInstance) {
		log.debug("deleting BookinfoDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoDetailDao#findByBookId(java.lang.Integer)
	 */
	@Override
	public BookinfoDetail findByBookId(java.lang.Integer bookId) {
		log.debug("getting BookinfoDetail instance with id: " + bookId);
		try {
			BookinfoDetail instance = (BookinfoDetail) getHibernateTemplate()
					.get(BookinfoDetail.class, bookId);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BookinfoDetail instance) {
		log.debug("finding BookinfoDetail instance by example");
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
		log.debug("finding BookinfoDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BookinfoDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByImageUrl(Object imageUrl) {
		return findByProperty(IMAGE_URL, imageUrl);
	}

	public List findByTaobaoTitle(Object taobaoTitle) {
		return findByProperty(TAOBAO_TITLE, taobaoTitle);
	}

	public List findByTaobaoCatagoryId(Object taobaoCatagoryId) {
		return findByProperty(TAOBAO_CATAGORY_ID, taobaoCatagoryId);
	}

	public List findBySummary(Object summary) {
		return findByProperty(SUMMARY, summary);
	}

	public List findByCatalog(Object catalog) {
		return findByProperty(CATALOG, catalog);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all BookinfoDetail instances");
		try {
			String queryString = "from BookinfoDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BookinfoDetail merge(BookinfoDetail detachedInstance) {
		log.debug("merging BookinfoDetail instance");
		try {
			BookinfoDetail result = (BookinfoDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BookinfoDetail instance) {
		log.debug("attaching dirty BookinfoDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BookinfoDetail instance) {
		log.debug("attaching clean BookinfoDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookinfoDetailDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (BookinfoDetailDao) ctx.getBean("BookinfoDetailDAO");
	}
}