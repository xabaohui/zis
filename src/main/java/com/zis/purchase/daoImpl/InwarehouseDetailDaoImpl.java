package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.dao.InwarehouseDetailDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * InwarehouseDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.InwarehouseDetail
 * @author MyEclipse Persistence Tools
 */

public class InwarehouseDetailDaoImpl extends HibernateDaoSupport implements InwarehouseDetailDao {
	private static final Logger log = LoggerFactory
			.getLogger(InwarehouseDetailDaoImpl.class);
	// property constants
	public static final String INWAREHOUSE_ID = "inwarehouseId";
	public static final String PURCHASE_ORDER_ID = "purchaseOrderId";
	public static final String POSITION_LABEL = "positionLabel";
	public static final String BOOK_ID = "bookId";
	public static final String ISBN = "isbn";
	public static final String AMOUNT = "amount";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehouseDetailDao#save(com.zis.purchase.bean.InwarehouseDetail)
	 */
	public void save(InwarehouseDetail transientInstance) {
		log.debug("saving InwarehouseDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InwarehouseDetail persistentInstance) {
		log.debug("deleting InwarehouseDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehouseDetailDao#findById(java.lang.Integer)
	 */
	public InwarehouseDetail findById(java.lang.Integer id) {
		log.debug("getting InwarehouseDetail instance with id: " + id);
		try {
			InwarehouseDetail instance = (InwarehouseDetail) getHibernateTemplate()
					.get(InwarehouseDetail.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehouseDetailDao#findByExample(com.zis.purchase.bean.InwarehouseDetail)
	 */
	public List findByExample(InwarehouseDetail instance) {
		log.debug("finding InwarehouseDetail instance by example");
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
	
	public List findByCriteria(DetachedCriteria criteria) {
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding InwarehouseDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InwarehouseDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehouseDetailDao#findByInwarehouseId(java.lang.Object)
	 */
	public List findByInwarehouseId(Object inwarehouseId) {
		return findByProperty(INWAREHOUSE_ID, inwarehouseId);
	}

	public List findByPurchaseOrderId(Object purchaseOrderId) {
		return findByProperty(PURCHASE_ORDER_ID, purchaseOrderId);
	}

	public List findByPositionLabel(Object positionLabel) {
		return findByProperty(POSITION_LABEL, positionLabel);
	}

	public List findByBookId(Object bookId) {
		return findByProperty(BOOK_ID, bookId);
	}

	public List findByIsbn(Object isbn) {
		return findByProperty(ISBN, isbn);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all InwarehouseDetail instances");
		try {
			String queryString = "from InwarehouseDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InwarehouseDetail merge(InwarehouseDetail detachedInstance) {
		log.debug("merging InwarehouseDetail instance");
		try {
			InwarehouseDetail result = (InwarehouseDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InwarehouseDetail instance) {
		log.debug("attaching dirty InwarehouseDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InwarehouseDetail instance) {
		log.debug("attaching clean InwarehouseDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InwarehouseDetailDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (InwarehouseDetailDao) ctx.getBean("InwarehouseDetailDAO");
	}
}