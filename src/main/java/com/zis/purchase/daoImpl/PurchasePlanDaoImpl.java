package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.dao.PurchasePlanDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Requirementamount entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.PurchasePlan
 * @author MyEclipse Persistence Tools
 */

public class PurchasePlanDaoImpl extends HibernateDaoSupport implements PurchasePlanDao {
	private static final Logger log = LoggerFactory
			.getLogger(PurchasePlanDaoImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String REQUIRE_AMOUNT = "requireAmount";
	public static final String PURCHASED_AMOUNT = "purchasedAmount";
	public static final String BOOK_NAME = "bookName";
	public static final String BOOK_AUTHOR = "bookAuthor";
	public static final String BOOK_PUBLISHER = "bookPublisher";
	public static final String ISBN = "isbn";
	public static final String BOOK_EDITION = "bookEdition";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#save(com.zis.purchase.bean.Requirementamount)
	 */
	public void save(PurchasePlan transientInstance) {
		log.debug("saving Requirementamount instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#delete(com.zis.purchase.bean.Requirementamount)
	 */
	public void delete(PurchasePlan persistentInstance) {
		log.debug("deleting Requirementamount instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findById(java.lang.Integer)
	 */
	public PurchasePlan findById(java.lang.Integer id) {
		log.debug("getting Requirementamount instance with id: " + id);
		try {
			PurchasePlan instance = (PurchasePlan) getHibernateTemplate()
					.get("com.zis.purchase.bean.Requirementamount", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByExample(com.zis.purchase.bean.Requirementamount)
	 */
	public List findByExample(PurchasePlan instance) {
		log.debug("finding Requirementamount instance by example");
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
	
	public List findbyCriteria(DetachedCriteria dc){
		return getHibernateTemplate().findByCriteria(dc);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Requirementamount instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Requirementamount as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByBookId(java.lang.Object)
	 */
	public PurchasePlan findByBookId(Integer bookId) {
		Session session = this.getSession();
		Criteria criteria = session.createCriteria(PurchasePlan.class);
		criteria.add(Restrictions.eq(BOOK_ID, bookId));
		criteria.add(Restrictions.eq("status", PurchasePlanStatus.NORMAL));
		List list = criteria.list();
		session.close();
		if(list == null || list.isEmpty()) {
			return null;
		}
		if(list.size() > 1) {
			throw new RuntimeException("数据错误，bookId=" + bookId
					+ "对应多条采购计划");
		}
		return (PurchasePlan) list.get(0);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByRequireAmount(java.lang.Object)
	 */
	public List findByRequireAmount(Object requireAmount) {
		return findByProperty(REQUIRE_AMOUNT, requireAmount);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByPurchasedAmount(java.lang.Object)
	 */
	public List findByPurchasedAmount(Object purchasedAmount) {
		return findByProperty(PURCHASED_AMOUNT, purchasedAmount);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByBookName(java.lang.Object)
	 */
	public List findByBookName(Object bookName) {
		return findByProperty(BOOK_NAME, bookName);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByBookAuthor(java.lang.Object)
	 */
	public List findByBookAuthor(Object bookAuthor) {
		return findByProperty(BOOK_AUTHOR, bookAuthor);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByBookPublisher(java.lang.Object)
	 */
	public List findByBookPublisher(Object bookPublisher) {
		return findByProperty(BOOK_PUBLISHER, bookPublisher);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByIsbn(java.lang.Object)
	 */
	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByBookEdition(java.lang.Object)
	 */
	public List findByBookEdition(Object bookEdition) {
		return findByProperty(BOOK_EDITION, bookEdition);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findByVersion(java.lang.Object)
	 */
	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Requirementamount instances");
		try {
			String queryString = "from Requirementamount";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#merge(com.zis.purchase.bean.Requirementamount)
	 */
	public PurchasePlan merge(PurchasePlan detachedInstance) {
		log.debug("merging Requirementamount instance");
		try {
			PurchasePlan result = (PurchasePlan) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#attachDirty(com.zis.purchase.bean.Requirementamount)
	 */
	public void attachDirty(PurchasePlan instance) {
		log.debug("attaching dirty Requirementamount instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.RequirementamountDao#attachClean(com.zis.purchase.bean.Requirementamount)
	 */
	public void attachClean(PurchasePlan instance) {
		log.debug("attaching clean Requirementamount instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PurchasePlanDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (PurchasePlanDao) ctx.getBean("RequirementamountDAO");
	}

	public void update(PurchasePlan ra) {
		getHibernateTemplate().update(ra);
	}
}