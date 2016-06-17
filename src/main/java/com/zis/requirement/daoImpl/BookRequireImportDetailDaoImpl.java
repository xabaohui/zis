package com.zis.requirement.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.dao.BookRequireImportDetailDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * TempBookRequireImportDetail entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.zis.requirement.bean.BookRequireImportDetail
 * @author MyEclipse Persistence Tools
 */

public class BookRequireImportDetailDaoImpl extends HibernateDaoSupport implements BookRequireImportDetailDao {
	private static final Logger log = LoggerFactory
			.getLogger(BookRequireImportDetailDaoImpl.class);
	// property constants
	public static final String BOOKID = "bookid";
	public static final String ISBN = "isbn";
	public static final String BOOK_NAME = "bookName";
	public static final String BOOK_AUTHOR = "bookAuthor";
	public static final String BOOK_EDITION = "bookEdition";
	public static final String BOOK_PUBLISHER = "bookPublisher";
	public static final String DEPART_ID = "departId";
	public static final String COLLEGE = "college";
	public static final String INSTITUTE = "institute";
	public static final String PART_NAME = "partName";
	public static final String CLASS_NUM = "classNum";
	public static final String GRADE = "grade";
	public static final String TERM = "term";
	public static final String AMOUNT = "amount";
	public static final String BATCH_ID = "batchId";
	public static final String STATUS = "status";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDetailDao#save(com.zis.requirement.bean.TempBookRequireImportDetail)
	 */
	public void save(BookRequireImportDetail transientInstance) {
		log.debug("saving TempBookRequireImportDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDetailDao#update(com.zis.requirement.bean.TempBookRequireImportDetail)
	 */
	public void update(BookRequireImportDetail transientInstance) {
		log.debug("update TempBookRequireImportDetail instance");
		try {
			getHibernateTemplate().update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(BookRequireImportDetail persistentInstance) {
		log.debug("deleting TempBookRequireImportDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDetailDao#findById(java.lang.Integer)
	 */
	public BookRequireImportDetail findById(java.lang.Integer id) {
		log.debug("getting TempBookRequireImportDetail instance with id: " + id);
		try {
			BookRequireImportDetail instance = (BookRequireImportDetail) getHibernateTemplate()
					.get(BookRequireImportDetail.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zis.requirement.daoImpl.TempBookRequireImportDetailDao#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@SuppressWarnings("unchecked")
	public List<BookRequireImportDetail> findByCriteria(DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	public List findByExample(BookRequireImportDetail instance) {
		log.debug("finding TempBookRequireImportDetail instance by example");
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
		log.debug("finding TempBookRequireImportDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TempBookRequireImportDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBookid(Object bookid) {
		return findByProperty(BOOKID, bookid);
	}

	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	public List findByBookName(Object bookName) {
		return findByProperty(BOOK_NAME, bookName);
	}

	public List findByBookAuthor(Object bookAuthor) {
		return findByProperty(BOOK_AUTHOR, bookAuthor);
	}

	public List findByBookEdition(Object bookEdition) {
		return findByProperty(BOOK_EDITION, bookEdition);
	}

	public List findByBookPublisher(Object bookPublisher) {
		return findByProperty(BOOK_PUBLISHER, bookPublisher);
	}

	public List findByDepartId(Object departId) {
		return findByProperty(DEPART_ID, departId);
	}

	public List findByCollege(Object college) {
		return findByProperty(COLLEGE, college);
	}

	public List findByInstitute(Object institute) {
		return findByProperty(INSTITUTE, institute);
	}

	public List findByPartName(Object partName) {
		return findByProperty(PART_NAME, partName);
	}

	public List findByClassNum(Object classNum) {
		return findByProperty(CLASS_NUM, classNum);
	}

	public List findByGrade(Object grade) {
		return findByProperty(GRADE, grade);
	}

	public List findByTerm(Object term) {
		return findByProperty(TERM, term);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByBatchId(Object batchId) {
		return findByProperty(BATCH_ID, batchId);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all TempBookRequireImportDetail instances");
		try {
			String queryString = "from TempBookRequireImportDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BookRequireImportDetail merge(
			BookRequireImportDetail detachedInstance) {
		log.debug("merging TempBookRequireImportDetail instance");
		try {
			BookRequireImportDetail result = (BookRequireImportDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BookRequireImportDetail instance) {
		log.debug("attaching dirty TempBookRequireImportDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BookRequireImportDetail instance) {
		log.debug("attaching clean TempBookRequireImportDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BookRequireImportDetailDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (BookRequireImportDetailDao) ctx
				.getBean("TempBookRequireImportDetailDAO");
	}
}