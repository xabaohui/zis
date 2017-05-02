package com.zis.common.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

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
	public static final String SEQ_ORDER_GROUP_NUM = "order_group_number";

	private static EntityManagerFactory entityManagerFactory;

	private static void initEntityManagerFactory() {
//		SequenceCreator.class.getClassLoader().
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		ApplicationContext app = (ApplicationContext) request
//				.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		entityManagerFactory = (EntityManagerFactory) app.getBean("entityManagerFactory");
		entityManagerFactory = (EntityManagerFactory) SpringContextHolder.getBean("entityManagerFactory");
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
