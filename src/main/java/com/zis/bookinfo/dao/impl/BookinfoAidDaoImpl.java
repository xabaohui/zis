package com.zis.bookinfo.dao.impl;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.bookinfo.bean.BookinfoAid;
import com.zis.bookinfo.dao.BookinfoAidDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * BookinfoAid entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.bookinfo.bean.BookinfoAid
 * @author MyEclipse Persistence Tools
 */

public class BookinfoAidDaoImpl extends HibernateDaoSupport implements BookinfoAidDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookinfoAidDaoImpl.class);
	// property constants
	public static final String GROUP_KEY = "groupKey";
	public static final String SHORT_BOOK_NAME = "shortBookName";
	public static final String IDS = "ids";
	public static final String TOTAL_COUNT = "totalCount";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoAidDao#save(com.zis.bookinfo.bean.BookinfoAid)
	 */
	public void save(BookinfoAid transientInstance) {
		log.debug("saving BookinfoAid instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(BookinfoAid transientInstance) {
		log.debug("update BookinfoAid instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(BookinfoAid persistentInstance) {
		log.debug("deleting BookinfoAid instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoAidDao#findById(java.lang.Integer)
	 */
	public BookinfoAid findById(java.lang.Integer id) {
		log.debug("getting BookinfoAid instance with id: " + id);
		try {
			BookinfoAid instance = (BookinfoAid) getHibernateTemplate().get(
					"com.zis.bookinfo.bean.BookinfoAid", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoAidDao#findByExample(com.zis.bookinfo.bean.BookinfoAid)
	 */
	public List findByExample(BookinfoAid instance) {
		log.debug("finding BookinfoAid instance by example");
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
		log.debug("finding BookinfoAid instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BookinfoAid as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.bookinfo.dao.impl.BookinfoAidDao#findByGroupKey(java.lang.Object)
	 */
	public List findByGroupKey(Object groupKey) {
		return findByProperty(GROUP_KEY, groupKey);
	}

	public List findByShortBookName(Object shortBookName) {
		return findByProperty(SHORT_BOOK_NAME, shortBookName);
	}

	public List findByIds(Object ids) {
		return findByProperty(IDS, ids);
	}

	public List findByTotalCount(Object totalCount) {
		return findByProperty(TOTAL_COUNT, totalCount);
	}

	public List findAll() {
		log.debug("finding all BookinfoAid instances");
		try {
			String queryString = "from BookinfoAid";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BookinfoAid merge(BookinfoAid detachedInstance) {
		log.debug("merging BookinfoAid instance");
		try {
			BookinfoAid result = (BookinfoAid) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BookinfoAid instance) {
		log.debug("attaching dirty BookinfoAid instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BookinfoAid instance) {
		log.debug("attaching clean BookinfoAid instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookinfoAidDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (BookinfoAidDao) ctx.getBean("BookinfoAidDAO");
	}

	public List findByCriteria(DetachedCriteria dc) {
		return this.getHibernateTemplate().findByCriteria(dc);
	}
}