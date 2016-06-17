package com.zis.common.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Sequence生成器
 * 
 * @author yz
 * 
 */
public class SequenceCreator {

	public static final String SEQ_BOOK_GROUP_ID = "book_group_id";
	public static final String SEQ_BOOK_RELATE_ID = "book_relate_id";

	private static SessionFactory sessionFactory;

	private static void initSessionFactory() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}

	/**
	 * 生成序列号
	 * @param sequenceName 序列号的名字
	 * @return
	 */
	public static Integer getSequence(String sequenceName) {
		if (StringUtils.isBlank(sequenceName)) {
			throw new RuntimeException("illegal argument, for input null");
		}
		if(sessionFactory == null) {
			initSessionFactory();
		}
		String sql = "select _nextval('" + sequenceName + "')";
		Session session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery(sql);
		Integer seq = (Integer) query.uniqueResult();
		session.close();
		if (seq == null) {
			throw new RuntimeException(
					"no such sequence defined, sequenceName=" + sequenceName);
		}
		return seq;
	}
}
