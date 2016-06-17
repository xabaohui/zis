package com.zis.purchase.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.actiontemplate.PaginationQueryAction;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.dto.PurchaseDetailView;

/**
 * 采购详情查询
 * 
 * @author yz
 * 
 */
public class PurchaseDetailViewAction extends
		PaginationQueryAction<PurchaseDetail> {

	private static final long serialVersionUID = 8908583764200653613L;
	private Integer bookId;
	private String isbn;
	private String bookName;
	private String operator;
	private String status;

	private BookService bookService;

	@Override
	protected String setActionUrl() {
		return "purchase/queryPurchaseDetail";
	}

	@Override
	protected String setActionUrlQueryCondition() {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(isbn)) {
			condition.append("isbn=" + isbn + "&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if (StringUtils.isNotBlank(operator)) {
			condition.append("operator=" + operator + "&");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("status=" + status + "&");
		}
		if(bookId != null) {
			condition.append("bookId=" + bookId + "&");
		}
		return condition.toString();
	}
	
	@Override
	protected void preProcessGetRequestCHN() {
		if (StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
		}
		if (StringUtils.isNotBlank(operator)) {
			operator = ZisUtils.convertGetRequestCHN(operator);
		}
	}

	@Override
	protected DetachedCriteria buildQueryCondition() {
		DetachedCriteria dc = DetachedCriteria.forClass(PurchaseDetail.class);
		if (bookId != null) {
			dc.add(Restrictions.eq("bookId", bookId));
		}
		// 如果输入了isbn或者图书，则先查询图书信息
		else if (StringUtils.isNotBlank(isbn)
				|| StringUtils.isNotBlank(bookName) || bookId != null) {
			DetachedCriteria bookCriteria = DetachedCriteria
					.forClass(Bookinfo.class);
			if (StringUtils.isNotBlank(isbn)) {
				bookCriteria.add(Restrictions.eq("isbn", isbn));
			}
			if (StringUtils.isNotBlank(bookName)) {
				bookCriteria.add(Restrictions.like("bookName", "%" + bookName
						+ "%"));
			}
			List<Bookinfo> blist = bookService.findBookByCriteria(bookCriteria);
			List<Integer> bookIds = new ArrayList<Integer>();
			if (blist == null || blist.isEmpty()) {
				return null;
			}
			for (Bookinfo bi : blist) {
				bookIds.add(bi.getId());
			}
			dc.add(Restrictions.in("bookId", bookIds));
		}
		// 附加操作员条件
		if (StringUtils.isNotBlank(operator)) {
			dc.add(Restrictions.eq("operator", operator));
		}
		if (StringUtils.isNotBlank(status)) {
			dc.add(Restrictions.eq("status", status));
		}
		dc.addOrder(Order.desc("gmtCreate"));
		return dc;
	}

	@Override
	protected List transformResult(List<PurchaseDetail> list) {
		List<PurchaseDetailView> resultList = new ArrayList<PurchaseDetailView>();
		for (PurchaseDetail record : list) {
			PurchaseDetailView view = new PurchaseDetailView();
			Bookinfo book = this.bookService.findBookById(record.getBookId());
			if (book != null) {
				BeanUtils.copyProperties(book, view);
				view.setNewEdition(book.getIsNewEdition());
			}
			BeanUtils.copyProperties(record, view);
			view.setStatusDisplay(PurchaseDetailStatus.getDisplay(record
					.getStatus()));
			resultList.add(view);
		}
		return resultList;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}
