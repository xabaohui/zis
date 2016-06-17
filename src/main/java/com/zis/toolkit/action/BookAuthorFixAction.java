package com.zis.toolkit.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书作者修复工具
 * @author yz
 *
 */
public class BookAuthorFixAction extends BaseBookFixAction {

	private static final long serialVersionUID = 5747894532711900026L;

	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 修复图书作者
	 * 
	 * @return
	 */
	public String fixBookAuthor() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookinfo.class);
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		int pageSize = 500;
		int i = 0;
		Page page = Page.createPage(0, pageSize, totalCount);
		List<String> list = new ArrayList<String>();
		while(i <= page.getTotalPageCount()) {
			List<Bookinfo> bookList = PaginationQueryUtil.paginationQuery(criteria, page);
			List<String> result = bookInfoToolkit.updateFixBookAuthor(bookList);
			list.addAll(result);
			i++;
			page = Page.createPage(i, pageSize, totalCount);
		}
		showResult(list);
		return SUCCESS;
	}

	public BookInfoToolkit getBookInfoToolkit() {
		return bookInfoToolkit;
	}

	public void setBookInfoToolkit(BookInfoToolkit bookInfoToolkit) {
		this.bookInfoToolkit = bookInfoToolkit;
	}
}
