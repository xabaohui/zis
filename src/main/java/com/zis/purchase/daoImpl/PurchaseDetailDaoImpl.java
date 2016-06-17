package com.zis.purchase.daoImpl;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.dao.PurchaseDetailDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Purchaseorder entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.PurchaseDetail
 * @author MyEclipse Persistence Tools
 */

public class PurchaseDetailDaoImpl extends HibernateDaoSupport implements PurchaseDetailDao{
	private static final Logger log = LoggerFactory
			.getLogger(PurchaseDetailDaoImpl.class);
	// property constants
	public static final String BOOK_ID = "bookId";
	public static final String PURCHASED_AMOUNT = "purchasedAmount";
	public static final String BATCH_ID = "batchId";
	public static final String MEMO = "memo";
	public static final String STATUS = "status";
	public static final String OPERATOR = "operator";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#save(com.zis.purchase.bean.Purchaseorder)
	 */
	public void save(PurchaseDetail transientInstance) {
		log.debug("saving Purchaseorder instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#delete(com.zis.purchase.bean.Purchaseorder)
	 */
	public void delete(PurchaseDetail persistentInstance) {
		log.debug("deleting Purchaseorder instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findById(java.lang.Integer)
	 */
	public PurchaseDetail findById(java.lang.Integer id) {
		log.debug("getting Purchaseorder instance with id: " + id);
		try {
			PurchaseDetail instance = (PurchaseDetail) getHibernateTemplate()
					.get("com.zis.purchase.bean.Purchaseorder", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByExample(com.zis.purchase.bean.Purchaseorder)
	 */
	public List findByExample(PurchaseDetail instance) {
		log.debug("finding Purchaseorder instance by example");
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
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Purchaseorder instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Purchaseorder as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByCriteria(DetachedCriteria dc){
		return getHibernateTemplate().findByCriteria(dc);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByBookId(java.lang.Object)
	 */
	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByPurchasedAmount(java.lang.Object)
	 */
	public List findByPurchasedAmount(Object purchasedAmount) {
		return findByProperty(PURCHASED_AMOUNT, purchasedAmount);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByBatchId(java.lang.Object)
	 */
	public List findByBatchId(Object batchId) {
		return findByProperty(BATCH_ID, batchId);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByMemo(java.lang.Object)
	 */
	public List findByMemo(Object memo) {
		return findByProperty(MEMO, memo);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByStatus(java.lang.Object)
	 */
	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByOperator(java.lang.Object)
	 */
	public List findByOperator(Object operator) {
		return findByProperty(OPERATOR, operator);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findByVersion(java.lang.Object)
	 */
	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all Purchaseorder instances");
		try {
			String queryString = "from Purchaseorder";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#merge(com.zis.purchase.bean.Purchaseorder)
	 */
	public PurchaseDetail merge(PurchaseDetail detachedInstance) {
		log.debug("merging Purchaseorder instance");
		try {
			PurchaseDetail result = (PurchaseDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#attachDirty(com.zis.purchase.bean.Purchaseorder)
	 */
	public void attachDirty(PurchaseDetail instance) {
		log.debug("attaching dirty Purchaseorder instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.daoImpl.PurchaseorderDao#attachClean(com.zis.purchase.bean.Purchaseorder)
	 */
	public void attachClean(PurchaseDetail instance) {
		log.debug("attaching clean Purchaseorder instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PurchaseDetailDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (PurchaseDetailDao) ctx.getBean("PurchaseorderDAO");
	}
}