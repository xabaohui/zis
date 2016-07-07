package com.zis.bookinfo.dao;

import com.zis.bookinfo.bean.BookinfoDetail;

public interface BookinfoDetailDao {

	public void save(BookinfoDetail transientInstance);

	public void update(BookinfoDetail transientInstance);

	public BookinfoDetail findByBookId(java.lang.Integer bookId);

}