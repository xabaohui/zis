package com.zis.requirement.daoImpl;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.dao.DepartmentInfoDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * Departmentinfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.zis.requirement.bean.Departmentinfo
 * @author MyEclipse Persistence Tools
 */

public class DepartmentinfoDAOImpl extends HibernateDaoSupport implements DepartmentInfoDao {
	private static final Logger log = LoggerFactory
			.getLogger(DepartmentinfoDAOImpl.class);
	// property constants
	public static final String PART_NAME = "partName";
	public static final String INSTITUTE = "institute";
	public static final String COLLEGE = "college";
	public static final String VERSION = "version";
	public static final String YEARS = "years";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#save(com.bean.Departmentinfo)
	 */
	public void save(Departmentinfo transientInstance) {
		log.debug("saving Departmentinfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void saveOrUpdate(Departmentinfo transientInstance) {
		log.debug("saving or updating Departmentinfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(transientInstance);
			log.debug("save or updating successful");
		} catch (RuntimeException re) {
			log.error("save or updating failed", re);
			throw re;
		}
	} 

	public void delete(Departmentinfo persistentInstance) {
		log.debug("deleting Departmentinfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findById(java.lang.Integer)
	 */
	public Departmentinfo findById(java.lang.Integer id) {
		log.debug("getting Departmentinfo instance with id: " + id);
		try {
			Departmentinfo instance = (Departmentinfo) getHibernateTemplate()
					.get("com.zis.requirement.bean.Departmentinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findByExample(com.bean.Departmentinfo)
	 */
	@SuppressWarnings("unchecked")
	public List<Departmentinfo> findByExample(Departmentinfo instance) {
		log.debug("finding Departmentinfo instance by example");
		try {
			List<Departmentinfo> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Departmentinfo instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from Departmentinfo as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findByPartName(java.lang.Object)
	 */
	public List findByPartName(Object partName) {
		return findByProperty(PART_NAME, partName);
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findByInstitute(java.lang.Object)
	 */
	public List findByInstitute(Object institute) {
		return findByProperty(INSTITUTE, institute);
	}

	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findByCollege(java.lang.Object)
	 */
	public List findByCollege(Object college) {
		return findByProperty(COLLEGE, college);
	}

	public List findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}


	/* (non-Javadoc)
	 * @see com.dao.DepartmentInfoDaox#findAll()
	 */
	public List findAll() {
		log.debug("finding all Departmentinfo instances");
		try {
			String queryString = "from Departmentinfo";
			@SuppressWarnings("unchecked")
			List<Departmentinfo> list = getHibernateTemplate().find(queryString);
			/*Collections.sort(list, new Comparator<Departmentinfo>() {
	            public int compare(Departmentinfo o1, Departmentinfo o2) {
	                return o1.getCollege().compareTo(o2.getCollege());
	            }
	        });
			Collections.sort(list, new Comparator<Departmentinfo>() {
	            public int compare(Departmentinfo o1, Departmentinfo o2) {
	                return o1.getInstitute().compareTo(o2.getInstitute());
	            }
	        });
			Collections.sort(list, new Comparator<Departmentinfo>() {
	            public int compare(Departmentinfo o1, Departmentinfo o2) {
	                return o1.getPartName().compareTo(o2.getPartName());
	            }
	        });*/
			return list;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Departmentinfo merge(Departmentinfo detachedInstance) {
		log.debug("merging Departmentinfo instance");
		try {
			Departmentinfo result = (Departmentinfo) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Departmentinfo instance) {
		log.debug("attaching dirty Departmentinfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Departmentinfo instance) {
		log.debug("attaching clean Departmentinfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public List findByCriteria(DetachedCriteria dc){
		log.debug("updating Departmentinfo departmentinfo");
		try {
			return getHibernateTemplate().findByCriteria(dc);
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public void update(Departmentinfo departmentinfo){
		log.debug("updating Departmentinfo departmentinfo");
		try {
			getHibernateTemplate().update(departmentinfo);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public static DepartmentInfoDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (DepartmentInfoDao) ctx.getBean("DepartmentinfoDAO");
	}
}