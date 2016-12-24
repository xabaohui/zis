package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.controllertemplate.PaginationQueryController;
import com.zis.common.mvc.ext.QueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.PurchaseDetail;
import com.zis.purchase.bean.PurchaseDetailStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.PurchaseDetailView;

/**
 * 采购详情查询
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class PurchaseDetailViewController extends PaginationQueryController<PurchaseDetail> {

	@Autowired
	private BookService bookService;

	@Autowired
	private DoPurchaseService doPurchaseService;
	
	@RequiresPermissions(value = {"purchase:queryPurchaseDetail"})
	@RequestMapping(value = "/queryPurchaseDetail")
	public String executeQuery(ModelMap context, HttpServletRequest request) {
		request.setAttribute("sort", new String[] { "gmtCreate" });
		return super.executeQuery(context, request);
	}

	@Override
	protected String setActionUrl(HttpServletRequest request) {
		return "purchase/queryPurchaseDetail";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		StringBuilder condition = new StringBuilder();
		String isbn = request.getParameter("isbn");
		String bookName = request.getParameter("bookName");
		String operator = request.getParameter("operator");
		String status = request.getParameter("status");
		String bookIdStr = request.getParameter("bookId");
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
		if (StringUtils.isNotBlank(bookIdStr)) {
			Integer bookId = Integer.parseInt(bookIdStr);
			condition.append("bookId=" + bookId + "&");
		}
		return condition.toString();
	}

	@Override
	protected void preProcessGetRequestCHN(HttpServletRequest request) {
		String bookName = request.getParameter("bookName");
		String operator = request.getParameter("operator");
		if (StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
		}
		if (StringUtils.isNotBlank(operator)) {
			operator = ZisUtils.convertGetRequestCHN(operator);
		}
	}

	@Override
	protected Specification<PurchaseDetail> buildQueryCondition(HttpServletRequest request) {
		String isbn = request.getParameter("isbn");
		String bookIdStr = request.getParameter("bookId");
		String bookName = request.getParameter("bookName");
		String operator = request.getParameter("operator");
		String status = request.getParameter("status");
		QueryUtil<PurchaseDetail> queryPD = new QueryUtil<PurchaseDetail>();
		if (StringUtils.isNotBlank(bookIdStr)) {
			Integer bookId = Integer.parseInt(bookIdStr);
			queryPD.eq("bookId", bookId);
		}
		// 如果输入了isbn或者图书，则先查询图书信息
		else if (StringUtils.isNotBlank(isbn) || StringUtils.isNotBlank(bookName) || StringUtils.isNotBlank(bookIdStr)) {
			QueryUtil<Bookinfo> query = new QueryUtil<Bookinfo>();
			if (StringUtils.isNotBlank(isbn)) {
				query.eq("isbn", isbn);
			}
			if (StringUtils.isNotBlank(bookName)) {
				query.like("bookName", "%" + bookName + "%");
			}
			Specification<Bookinfo> specBookInfo = query.getSpecification();
			List<Bookinfo> blist = bookService.findBySpecificationAll(specBookInfo);
			List<Integer> bookIds = new ArrayList<Integer>();
			if (blist == null || blist.isEmpty()) {
				return null;
			}
			for (Bookinfo bi : blist) {
				bookIds.add(bi.getId());
			}
			queryPD.in("bookId", bookIds);
		}
		// 附加操作员条件
		if (StringUtils.isNotBlank(operator)) {
			queryPD.eq("operator", operator);
		}
		if (StringUtils.isNotBlank(status)) {
			queryPD.eq("status", status);
		}
		queryPD.desc("gmtCreate");
		return queryPD.getSpecification();
	}

	@SuppressWarnings({ "rawtypes" })
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
			view.setStatusDisplay(PurchaseDetailStatus.getDisplay(record.getStatus()));
			resultList.add(view);
		}
		return resultList;
	}

	@Override
	protected String getFailPage() {
		return "purchase/purchaseDetail";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "purchase/purchaseDetail";
	}

	@Override
	protected Page<PurchaseDetail> buildPageList(Specification<PurchaseDetail> spec, Pageable page) {
		return this.doPurchaseService.findPurchaseDetailBySpecPage(spec, page);
	}

}
