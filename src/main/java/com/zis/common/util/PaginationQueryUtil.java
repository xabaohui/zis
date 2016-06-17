package com.zis.common.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 分页工具类
 * @author yz
 *
 */
public class PaginationQueryUtil {
	
	private static SessionFactory sessionFactory;
	
	private static void initSessionFactory() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	/**
	 * 分页查询
	 * @param detachedCriteria 查询条件
	 * @param page 页面信息，包含当前页数、总页数、总记录数等信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List paginationQuery(DetachedCriteria detachedCriteria, Page page) {
		if(sessionFactory == null) {
			initSessionFactory();
		}
		Session session = sessionFactory.openSession();
		Criteria c = detachedCriteria.getExecutableCriteria(session);
		c.setFirstResult(page.getBeginIndex());
		c.setMaxResults(page.getPageSize());
		List resultList = c.list();
		session.close();
		return resultList;
	}
	
	/**
	 * 获取记录总数
	 * @param detachedCriteria
	 * @return
	 */
	public static Integer getTotalCount(DetachedCriteria detachedCriteria) {
		if(sessionFactory == null) {
			initSessionFactory();
		}
		Session session = sessionFactory.openSession();
		Criteria c = detachedCriteria.getExecutableCriteria(session);
		//Integer result = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
		Integer result = ((Long) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		c.setProjection(null);
		session.close();
		return result;
	}
}
