package com.zis.requirement.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.requirement.bean.Bookamount;
import com.zis.requirement.dao.BookAmountDao;
import com.zis.requirement.dao.BookAmountDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Bookamount entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zis.requirement.bean.Bookamount
 * @author MyEclipse Persistence Tools
 */

public class BookamountDAOImpl extends HibernateDaoSupport implements
		BookAmountDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookamountDAOImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String ISBN = "isbn";
	public static final String BOOK_NAME = "bookName";
	public static final String BOOK_AUTHOR = "bookAuthor";
	public static final String BOOK_PUBLISHER = "bookPublisher";
	public static final String PART_ID = "partId";
	public static final String AMOUNT = "amount";
	public static final String VERSION = "version";
	public static final String OPERATOR = "operator";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zis.requirement.daoImpl.BookAmountDao2#save(com.zis.requirement.bean
	 * .Bookamount)
	 */
	public void save(Bookamount transientInstance) {
		log.debug("saving Bookamount instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Bookamount persistentInstance) {
		log.debug("deleting Bookamount instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zis.requirement.daoImpl.BookAmountDao2#findById(java.lang.Integer)
	 */
	public Bookamount findById(java.lang.Integer id) {
		log.debug("getting Bookamount instance with id: " + id);
		try {
			Bookamount instance = (Bookamount) getHibernateTemplate().get(
					"com.zis.requirement.bean.Bookamount", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zis.requirement.daoImpl.BookAmountDao2#findByExample(com.zis.requirement
	 * .bean.Bookamount)
	 */
	public List findByExample(Bookamount instance) {
		log.debug("finding Bookamount instance by example");
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
		log.debug("finding Bookamount instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Bookamount as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<Bookamount> findByBookId(int bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zis.requirement.daoImpl.BookAmountDao2#findByIsbn(java.lang.Object)
	 */
	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	public List findByBookName(Object bookName) {
		return findByProperty(BOOK_NAME, bookName);
	}

	public List findByBookAuthor(Object bookAuthor) {
		return findByProperty(BOOK_AUTHOR, bookAuthor);
	}

	public List findByBookPublisher(Object bookPublisher) {
		return findByProperty(BOOK_PUBLISHER, bookPublisher);
	}

	public List findByPartId(Object partId) {
		return findByProperty(PART_ID, partId);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zis.requirement.daoImpl.BookAmountDao2#findAll()
	 */
	public List findAll() {
		log.debug("finding all Bookamount instances");
		try {
			String queryString = "from Bookamount";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Bookamount merge(Bookamount detachedInstance) {
		log.debug("merging Bookamount instance");
		try {
			Bookamount result = (Bookamount) getHibernateTemplate().merge(
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
	 * @see
	 * com.zis.requirement.daoImpl.BookAmountDao2#findByCriteria(org.hibernate
	 * .criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria dc) {
		log.debug("updating Bookamount bookamount");
		try {
			return getHibernateTemplate().findByCriteria(dc);
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void attachDirty(Bookamount instance) {
		log.debug("attaching dirty Bookamount instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bookamount instance) {
		log.debug("attaching clean Bookamount instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookAmountDao getFromApplicationContext(ApplicationContext ctx) {
		return (BookAmountDao) ctx.getBean("BookAmountDao");
	}

	public List findBySql(String sql) {
		log.debug("find by sql");
		Session session = getSession();
		try {
			return session.createSQLQuery(sql).list();
		} catch (RuntimeException re) {
			log.error("find by sql failed", re);
			throw re;
		} finally {
			session.close();
		}
	}
}