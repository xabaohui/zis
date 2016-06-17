package com.zis.purchase.daoImpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.purchase.bean.InwarehousePosition;
import com.zis.purchase.dao.InwarehousePositionDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * InwarehousePosition entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.purchase.bean.InwarehousePosition
 * @author MyEclipse Persistence Tools
 */

public class InwarehousePositionDaoImpl extends HibernateDaoSupport implements InwarehousePositionDao {
	private static final Logger log = LoggerFactory
			.getLogger(InwarehousePositionDaoImpl.class);
	// property constants
	public static final String INWAREHOUSE_ID = "inwarehouseId";
	public static final String POSITION_LABEL = "positionLabel";
	public static final String CAPACITY = "capacity";
	public static final String CURRENT_AMOUNT = "currentAmount";
	public static final String IS_FULL = "isFull";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehousePositionDao#save(com.zis.purchase.bean.InwarehousePosition)
	 */
	public void save(InwarehousePosition transientInstance) {
		log.debug("saving InwarehousePosition instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InwarehousePosition persistentInstance) {
		log.debug("deleting InwarehousePosition instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehousePositionDao#findById(java.lang.Integer)
	 */
	public InwarehousePosition findById(java.lang.Integer id) {
		log.debug("getting InwarehousePosition instance with id: " + id);
		try {
			InwarehousePosition instance = (InwarehousePosition) getHibernateTemplate()
					.get(InwarehousePosition.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehousePositionDao#findByExample(com.zis.purchase.bean.InwarehousePosition)
	 */
	public List findByExample(InwarehousePosition instance) {
		log.debug("finding InwarehousePosition instance by example");
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
		log.debug("finding InwarehousePosition instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InwarehousePosition as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.purchase.dao.InwarehousePositionDao#findByInwarehouseId(java.lang.Object)
	 */
	public List findByInwarehouseId(Object inwarehouseId) {
		return findByProperty(INWAREHOUSE_ID, inwarehouseId);
	}

	public List findByPositionLabel(Object positionLabel) {
		return findByProperty(POSITION_LABEL, positionLabel);
	}

	public List findByCapacity(Object capacity) {
		return findByProperty(CAPACITY, capacity);
	}

	public List findByCurrentAmount(Object currentAmount) {
		return findByProperty(CURRENT_AMOUNT, currentAmount);
	}

	public List findByIsFull(Object isFull) {
		return findByProperty(IS_FULL, isFull);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List findAll() {
		log.debug("finding all InwarehousePosition instances");
		try {
			String queryString = "from InwarehousePosition";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InwarehousePosition merge(InwarehousePosition detachedInstance) {
		log.debug("merging InwarehousePosition instance");
		try {
			InwarehousePosition result = (InwarehousePosition) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InwarehousePosition instance) {
		log.debug("attaching dirty InwarehousePosition instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InwarehousePosition instance) {
		log.debug("attaching clean InwarehousePosition instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InwarehousePositionDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (InwarehousePositionDao) ctx.getBean("InwarehousePositionDAO");
	}
}