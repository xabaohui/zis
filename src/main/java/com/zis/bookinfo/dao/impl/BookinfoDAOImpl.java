package com.zis.bookinfo.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.dao.BookinfoDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bookinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zis.bookinfo.bean.Bookinfo
 * @author MyEclipse Persistence Tools
 */
public class BookinfoDAOImpl extends HibernateDaoSupport implements BookinfoDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookinfoDAOImpl.class);

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#save(com.zis.bean.Bookinfo)
	 */
	public void save(Bookinfo transientInstance) {
		log.debug("saving Bookinfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#delete(com.zis.bean.Bookinfo)
	 */
	public void delete(Bookinfo persistentInstance) {
		log.debug("deleting Bookinfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findById(java.lang.Integer)
	 */
	public Bookinfo findById(java.lang.Integer id) {
		try {
			Bookinfo instance = (Bookinfo) getHibernateTemplate().get(
					"com.zis.bookinfo.bean.Bookinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByExample(com.zis.bean.Bookinfo)
	 */
	public List findByExample(Bookinfo instance) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByProperty(java.lang.String,
	 * java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bookinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bookinfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByOutId(java.lang.Object)
	 */
	public List findByOutId(Object outId) {
		return findByProperty(OUT_ID, outId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByIsbn(java.lang.Object)
	 */
	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookName(java.lang.Object)
	 */
	public List findByBookName(Object bookName) {
		return findByProperty(BOOK_NAME, bookName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookAuthor(java.lang.Object)
	 */
	public List findByBookAuthor(Object bookAuthor) {
		return findByProperty(BOOK_AUTHOR, bookAuthor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookPublisher(java.lang.Object)
	 */
	public List findByBookPublisher(Object bookPublisher) {
		return findByProperty(BOOK_PUBLISHER, bookPublisher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookPrice(java.lang.Object)
	 */
	public List findByBookPrice(Object bookPrice) {
		return findByProperty(BOOK_PRICE, bookPrice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookEdition(java.lang.Object)
	 */
	public List findByBookEdition(Object bookEdition) {
		return findByProperty(BOOK_EDITION, bookEdition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByIsNewEdition(java.lang.Object)
	 */
	public List findByIsNewEdition(Object isNewEdition) {
		return findByProperty(IS_NEW_EDITION, isNewEdition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByGroupId(java.lang.Object)
	 */
	public List findByGroupId(Object groupId) {
		return findByProperty(GROUP_ID, groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByRelateId(java.lang.Object)
	 */
	public List findByRelateId(Object relateId) {
		return findByProperty(RELATE_ID, relateId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByRepeatIsbn(java.lang.Object)
	 */
	public List findByRepeatIsbn(Object repeatIsbn) {
		return findByProperty(REPEAT_ISBN, repeatIsbn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByVersion(java.lang.Object)
	 */
	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findByBookStatus(java.lang.Object)
	 */
	public List findByBookStatus(Object bookStatus) {
		return findByProperty(BOOK_STATUS, bookStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findBooks(org.hibernate.Criteria)
	 */
	public List findByCriteria(DetachedCriteria criteria) {
		try {
			return this.getHibernateTemplate().findByCriteria(criteria);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Bookinfo> findByCriteriaLimitCount(DetachedCriteria criteria,
			Integer limit) {
		Session session = this.getSession();
		Criteria c = criteria.getExecutableCriteria(session);
		c.setMaxResults(limit);
		List<Bookinfo> list = c.list();
		session.close();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Bookinfo instances");
		try {
			String queryString = "from Bookinfo";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#merge(com.zis.bean.Bookinfo)
	 */
	public Bookinfo merge(Bookinfo detachedInstance) {
		log.debug("merging Bookinfo instance");
		try {
			Bookinfo result = (Bookinfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#attachDirty(com.zis.bean.Bookinfo)
	 */
	public void attachDirty(Bookinfo instance) {
		log.debug("attaching dirty Bookinfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.dao.impl.BookinfoDao#attachClean(com.zis.bean.Bookinfo)
	 */
	public void attachClean(Bookinfo instance) {
		log.debug("attaching clean Bookinfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookinfoDao getFromApplicationContext(ApplicationContext ctx) {
		return (BookinfoDao) ctx.getBean("BookinfoDAO");
	}

	public void update(Bookinfo instance) {
		log.debug("update Bookinfo instance");
		try {
			getHibernateTemplate().update(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findByhql(String sql) {

		List list = this.getHibernateTemplate().find(sql);
		return list;
	}
}