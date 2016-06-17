package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.dao.InwarehouseDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Inwarehouse entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.Inwarehouse
 * @author MyEclipse Persistence Tools
 */

public class InwarehouseDaoImpl extends HibernateDaoSupport implements
		InwarehouseDao {
	private static final Logger log = LoggerFactory
			.getLogger(InwarehouseDaoImpl.class);
	// property constants
	public static final String PURCHASE_OPERATOR = "purchaseOperator";
	public static final String INWAREHOUSE_OPERATOR = "inwarehouseOperator";
	public static final String STATUS = "status";
	public static final String AMOUNT = "amount";
	public static final String MEMO = "memo";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zis.purchase.dao.InwarehouseDao#save(com.zis.purchase.bean.Inwarehouse
	 * )
	 */
	public void save(Inwarehouse transientInstance) {
		log.debug("saving Inwarehouse instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Inwarehouse persistentInstance) {
		log.debug("deleting Inwarehouse instance");
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
	 * @see com.zis.purchase.dao.InwarehouseDao#findById(java.lang.Integer)
	 */
	public Inwarehouse findById(java.lang.Integer id) {
		log.debug("getting Inwarehouse instance with id: " + id);
		try {
			Inwarehouse instance = (Inwarehouse) getHibernateTemplate().get(
					Inwarehouse.class, id);
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
	 * com.zis.purchase.dao.InwarehouseDao#findByExample(com.zis.purchase.bean
	 * .Inwarehouse)
	 */
	public List findByExample(Inwarehouse instance) {
		log.debug("finding Inwarehouse instance by example");
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
		log.debug("finding Inwarehouse instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Inwarehouse as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPurchaseOperator(Object purchaseOperator) {
		return findByProperty(PURCHASE_OPERATOR, purchaseOperator);
	}

	public List findByInwarehouseOperator(Object inwarehouseOperator) {
		return findByProperty(INWAREHOUSE_OPERATOR, inwarehouseOperator);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByMemo(Object memo) {
		return findByProperty(MEMO, memo);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all Inwarehouse instances");
		try {
			String queryString = "from Inwarehouse";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Inwarehouse merge(Inwarehouse detachedInstance) {
		log.debug("merging Inwarehouse instance");
		try {
			Inwarehouse result = (Inwarehouse) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Inwarehouse instance) {
		log.debug("attaching dirty Inwarehouse instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Inwarehouse instance) {
		log.debug("attaching clean Inwarehouse instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InwarehouseDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (InwarehouseDao) ctx.getBean("InwarehouseDAO");
	}
}