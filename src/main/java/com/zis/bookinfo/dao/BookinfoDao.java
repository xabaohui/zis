package com.zis.bookinfo.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.bookinfo.bean.Bookinfo;

public interface BookinfoDao {

	// property constants
	public static final String OUT_ID = "outId";
	public static final String ISBN = "isbn";
	public static final String BOOK_NAME = "bookName";
	public static final String BOOK_AUTHOR = "bookAuthor";
	public static final String BOOK_PUBLISHER = "bookPublisher";
	public static final String BOOK_PRICE = "bookPrice";
	public static final String BOOK_EDITION = "bookEdition";
	public static final String IS_NEW_EDITION = "isNewEdition";
	public static final String GROUP_ID = "groupId";
	public static final String RELATE_ID = "relateId";
	public static final String REPEAT_ISBN = "repeatIsbn";
	public static final String VERSION = "version";
	public static final String BOOK_STATUS = "bookStatus";

	public abstract void save(Bookinfo transientInstance);

	public abstract void delete(Bookinfo persistentInstance);

	public abstract Bookinfo findById(java.lang.Integer id);

	public abstract List<Bookinfo> findByExample(Bookinfo instance);
	
	public abstract List<Bookinfo> findByProperty(String propertyName, Object value);

	public abstract List<Bookinfo> findByOutId(Object outId);

	public abstract List<Bookinfo> findByIsbn(Object isbn);

	public abstract List<Bookinfo> findByBookName(Object bookName);

	public abstract List<Bookinfo> findByBookAuthor(Object bookAuthor);

	public abstract List<Bookinfo> findByBookPublisher(Object bookPublisher);

	public abstract List<Bookinfo> findByBookPrice(Object bookPrice);

	public abstract List<Bookinfo> findByBookEdition(Object bookEdition);

	public abstract List<Bookinfo> findByIsNewEdition(Object isNewEdition);

	public abstract List<Bookinfo> findByGroupId(Object groupId);

	public abstract List<Bookinfo> findByRelateId(Object relateId);

	public abstract List<Bookinfo> findByRepeatIsbn(Object repeatIsbn);

	public abstract List<Bookinfo> findByVersion(Object version);

	public abstract List<Bookinfo> findByBookStatus(Object bookStatus);
	
   /**
   * ͨ��hql�����в�ѯ
 * @param hql
 * @return
 */
    public abstract List<Bookinfo> findByhql(String hql);  
	 /**
		 * ͨ�����ϲ�ѯ�õ��������
		 * @param bookISBN
		 * @param bookName
		 * @param bookAuthor
		 * @return
		 */
	public abstract List<Bookinfo> findByCriteria(DetachedCriteria criteria);
	
	public abstract List<Bookinfo> findByCriteriaLimitCount(DetachedCriteria criteria, Integer limit);

	public abstract List<Bookinfo> findAll();

	public abstract Bookinfo merge(Bookinfo detachedInstance);

	public abstract void attachDirty(Bookinfo instance);

	public abstract void attachClean(Bookinfo instance);
	/**
	 * �޸�ͼ��
	 * @param instance
	 */
	public abstract void update(Bookinfo instance);

}