package com.zis.bookinfo.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.bookinfo.bean.BookinfoAid;

public interface BookinfoAidDao {

	public void save(BookinfoAid transientInstance);

	public BookinfoAid findById(java.lang.Integer id);

	public List findByExample(BookinfoAid instance);

	public List findByGroupKey(Object groupKey);

	public void update(BookinfoAid transientInstance);

	public List findByCriteria(DetachedCriteria dc);
	
	public void delete(BookinfoAid persistentInstance);
}