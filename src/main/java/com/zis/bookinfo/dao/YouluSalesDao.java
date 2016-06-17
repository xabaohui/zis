package com.zis.bookinfo.dao;

import java.util.List;

import com.zis.bookinfo.bean.YouluSales;

public interface YouluSalesDao {

	public abstract void save(YouluSales transientInstance);

	public abstract YouluSales findById(java.lang.Integer id);

	public abstract List findByExample(YouluSales instance);

	public abstract List findByBookId(Object bookId);

	public abstract List findByOutId(Object outId);

}