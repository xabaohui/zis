package com.zis.common.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Sequence生成器
 * 
 * @author yz
 * 
 */
// FIXME 改成JPA 修改成功
public class SequenceCreator {

	public static final String SEQ_BOOK_GROUP_ID = "book_group_id";
	public static final String SEQ_BOOK_RELATE_ID = "book_relate_id";

	private static EntityManagerFactory entityManagerFactory;

	private static void initEntityManagerFactory() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		ApplicationContext app = (ApplicationContext) request
				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		// ServletContext context =
		// request.getSession().getServletContext();//方式2
		// WebApplicationContext attr = (WebApplicationContext) context
		// .getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.zis-main");
		entityManagerFactory = (EntityManagerFactory) app.getBean("entityManagerFactory");
	}

	/**
	 * 生成序列号
	 * 
	 * @param sequenceName
	 *            序列号的名字
	 * @return
	 */
	public static Integer getSequence(String sequenceName) {
		if (StringUtils.isBlank(sequenceName)) {
			throw new RuntimeException("illegal argument, for input null");
		}
		if (entityManagerFactory == null) {
			initEntityManagerFactory();
		}
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String sql = "select _nextval('" + sequenceName + "')";
		entityManager.getTransaction().begin();
		Query query = entityManager.createNativeQuery(sql);
		entityManager.getTransaction().commit();
		Integer seq = (Integer) query.getSingleResult();
		entityManager.close();
		entityManager = null;//更高效的使用垃圾回收机制
		if (seq == null) {
			throw new RuntimeException("no such sequence defined, sequenceName=" + sequenceName);
		}
		return seq;
	}
}
