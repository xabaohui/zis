package com.zis.requirement.daoImpl;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.requirement.bean.SysVar;
import com.zis.requirement.dao.SysVarDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * SysVar entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.zis.requirement.bean.SysVar
 * @author MyEclipse Persistence Tools
 */

public class SysVarDAOImpl extends HibernateDaoSupport implements SysVarDao {
	private static final Logger log = LoggerFactory.getLogger(SysVarDAOImpl.class);
	// property constants
	public static final String DEP_KEY = "depKey";
	public static final String DEP_VALUE = "depValue";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#save(com.zis.requirement.bean.SysVar)
	 */
	public void save(SysVar transientInstance) {
		log.debug("saving SysVar instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#delete(com.zis.requirement.bean.SysVar)
	 */
	public void delete(SysVar persistentInstance) {
		log.debug("deleting SysVar instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#findById(java.lang.Integer)
	 */
	public SysVar findById(java.lang.Integer id) {
		log.debug("getting SysVar instance with id: " + id);
		try {
			SysVar instance = (SysVar) getHibernateTemplate().get(
					"com.zis.requirement.bean.SysVar", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void update(SysVar instance){
		getHibernateTemplate().saveOrUpdate(instance);
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#findByExample(com.zis.requirement.bean.SysVar)
	 */
	public List findByExample(SysVar instance) {
		log.debug("finding SysVar instance by example");
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
	 * @see com.zis.requirement.bean.SysVarDAO#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding SysVar instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysVar as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#findByDepKey(java.lang.Object)
	 */
	public List findByDepKey(Object depKey) {
		return findByProperty(DEP_KEY, depKey);
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#findByDepValue(java.lang.Object)
	 */
	public List findByDepValue(Object depValue) {
		return findByProperty(DEP_VALUE, depValue);
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#findAll()
	 */
	public List findAll() {
		log.debug("finding all SysVar instances");
		try {
			String queryString = "from SysVar";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#merge(com.zis.requirement.bean.SysVar)
	 */
	public SysVar merge(SysVar detachedInstance) {
		log.debug("merging SysVar instance");
		try {
			SysVar result = (SysVar) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#attachDirty(com.zis.requirement.bean.SysVar)
	 */
	public void attachDirty(SysVar instance) {
		log.debug("attaching dirty SysVar instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zis.requirement.bean.SysVarDAO#attachClean(com.zis.requirement.bean.SysVar)
	 */
	public void attachClean(SysVar instance) {
		log.debug("attaching clean SysVar instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SysVarDao getFromApplicationContext(ApplicationContext ctx) {
		return (SysVarDao) ctx.getBean("SysVarDAO");
	}
}