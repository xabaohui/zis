package com.zis.purchase.action;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.zis.common.actiontemplate.PaginationQueryAction;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.Bookwhitelist;

/**
 * 采购白名单
 * @author yz
 *
 */
public class WhiteListViewAction extends PaginationQueryAction<Bookwhitelist> {

	private static final long serialVersionUID = 1L;
	private String isbn;
	private String bookName;

	@Override
	protected String setActionUrl() {
		return "purchase/queryWhiteList";
	}

	@Override
	protected String setActionUrlQueryCondition() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isNotBlank(isbn)) {
			builder.append("isbn=").append(isbn).append("&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			builder.append("bookName=").append(bookName).append("&");
		}
		return builder.toString();
	}
	
	@Override
	protected void preProcessGetRequestCHN() {
		if(StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
		}
	}

	@Override
	protected DetachedCriteria buildQueryCondition() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bookwhitelist.class);
		if (StringUtils.isNotBlank(isbn)) {
			criteria.add(Restrictions.eq("isbn", isbn));
		}
		if (StringUtils.isNotBlank(bookName)) {
			criteria.add(Restrictions.like("bookName", "%" + bookName + "%"));
		}
		return criteria;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
