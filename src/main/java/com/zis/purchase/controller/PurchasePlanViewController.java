package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.service.BookService;
import com.zis.common.controllertemplate.PaginationQueryController;
import com.zis.common.util.ZisUtils;
import com.zis.purchase.bean.PurchasePlan;
import com.zis.purchase.bean.PurchasePlanStatus;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.PurchasePlanView;

/**
 * 查看采购计划列表
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value="/purchase")
public class PurchasePlanViewController extends
		PaginationQueryController<PurchasePlan> {

	@Autowired
	private BookService bookService;
	@Autowired
	private DoPurchaseService doPurchaseService;
	
	@RequestMapping(value="/queryPurchasePlan")
	public String executeQuery(ModelMap context,HttpServletRequest request){
		return super.executeQuery(context, request);
	}

	@Override
	protected String setActionUrl() {
		return "purchase/queryPurchasePlan";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		StringBuilder condition = new StringBuilder();
		String isbn=request.getParameter("isbn");
		String bookName=request.getParameter("bookName");
		String strictBookName=request.getParameter("strictBookName");
		String bookAuthor=request.getParameter("bookAuthor");
		String bookPublisher=request.getParameter("bookPublisher");
		String minPlanAmountStr=request.getParameter("minPlanAmount");
		String maxPlanAmountStr=request.getParameter("minPlanAmount");
		if (StringUtils.isNotBlank(isbn)) {
			condition.append("isbn=" + isbn + "&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if(StringUtils.isNotBlank(strictBookName) && Boolean.valueOf(strictBookName) == true) {
			condition.append("strictBookName=true&");
		}
		if (StringUtils.isNotBlank(bookAuthor)) {
			condition.append("bookAuthor=" + bookAuthor + "&");
		}
		if (StringUtils.isNotBlank(bookPublisher)) {
			condition.append("bookPublisher=" + bookPublisher + "&");
		}
		if(StringUtils.isNotBlank(minPlanAmountStr)) {
			Integer minPlanAmount=Integer.parseInt(minPlanAmountStr);
			condition.append("minPlanAmount=" + minPlanAmount + "&");
		}
		if(StringUtils.isNotBlank(maxPlanAmountStr)) {
			Integer maxPlanAmount=Integer.parseInt(maxPlanAmountStr);
			condition.append("maxPlanAmount=" + maxPlanAmount + "&");
		}
		return condition.toString();
	}
	
	@Override
	protected void preProcessGetRequestCHN(HttpServletRequest request) {
		String bookName=request.getParameter("bookName");
		String bookAuthor=request.getParameter("bookAuthor");
		String bookPublisher=request.getParameter("bookPublisher");
		if (StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
		}
		if (StringUtils.isNotBlank(bookAuthor)) {
			bookAuthor = ZisUtils.convertGetRequestCHN(bookAuthor);
		}
		if (StringUtils.isNotBlank(bookPublisher)) {
			bookPublisher = ZisUtils.convertGetRequestCHN(bookPublisher);
		}
	}

	@Override
	protected DetachedCriteria buildQueryCondition(HttpServletRequest request) {
		DetachedCriteria dc = DetachedCriteria.forClass(PurchasePlan.class);
		String isbn=request.getParameter("isbn");
		String bookName=request.getParameter("bookName");
		String strictBookName=request.getParameter("strictBookName");
		String bookAuthor=request.getParameter("bookAuthor");
		String bookPublisher=request.getParameter("bookPublisher");
		String minPlanAmountStr=request.getParameter("minPlanAmount");
		String maxPlanAmountStr=request.getParameter("minPlanAmount");
		if (StringUtils.isNotBlank(isbn)) {
			dc.add(Restrictions.eq("isbn", isbn));
		}
		if (StringUtils.isNotBlank(bookName)) {
			if(StringUtils.isNotBlank(strictBookName) && Boolean.valueOf(strictBookName) == true) {
				dc.add(Restrictions.eq("bookName", bookName));
			} else {
				dc.add(Restrictions.like("bookName", "%" + bookName + "%"));
			}
		}
		if (StringUtils.isNotBlank(bookAuthor)) {
			dc.add(Restrictions.like("bookAuthor", "%" + bookAuthor + "%"));
		}
		if (StringUtils.isNotBlank(bookPublisher)) {
			dc.add(Restrictions.like("bookPublisher", "%" + bookPublisher + "%"));
		}
		if (StringUtils.isNotBlank(minPlanAmountStr) && StringUtils.isNotBlank(maxPlanAmountStr)) {
			Integer minPlanAmount=Integer.parseInt(minPlanAmountStr);
			Integer maxPlanAmount=Integer.parseInt(maxPlanAmountStr);
			dc.add(Restrictions.between("requireAmount", minPlanAmount, maxPlanAmount));
		} else if(StringUtils.isNotBlank(minPlanAmountStr)) {
			Integer minPlanAmount=Integer.parseInt(minPlanAmountStr);
			dc.add(Restrictions.ge("requireAmount", minPlanAmount));
		} else if(StringUtils.isNotBlank(maxPlanAmountStr)) {
			Integer maxPlanAmount=Integer.parseInt(maxPlanAmountStr);
			dc.add(Restrictions.le("requireAmount", maxPlanAmount));
		}
		dc.add(Restrictions.eq("status", PurchasePlanStatus.NORMAL));
		return dc;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List transformResult(List<PurchasePlan> list) {
		List<PurchasePlanView> resultList = new ArrayList<PurchasePlanView>();
		for (PurchasePlan record : list) {
			PurchasePlanView view = new PurchasePlanView();
			BeanUtils.copyProperties(record, view);
			Bookinfo book = this.bookService.findBookById(record.getBookId());
			// 是否是最新版
			if(book != null && book.getIsNewEdition() != null) {
				view.setNewEdition(book.getIsNewEdition());
			}
			// 是否一码多书
			if(book != null && book.getRepeatIsbn() != null) {
				view.setRepeatIsbn(book.getRepeatIsbn());
			}
			// 是否是人工定量优先
			if(record.getManualDecision() > 0 && doPurchaseService.isAllowManualDecisionForPurchasePlan()) {
				view.setManualDecisionFlag(true);
			}
			// 计算仍需采购量
			view.setStillRequireAmount(doPurchaseService.calculateStillRequireAmount(record));
			view.setPublishDate(book.getPublishDate());
			if(book.getBookPrice() != null && book.getBookPrice() > 0) {
				view.setBookPrice((float) Math.ceil(book.getBookPrice() * 0.2f));
			}
			resultList.add(view);
		}
		return resultList;
	}


	@Override
	protected String getFailPage() {
		return "purchase/purchasePlan";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "purchase/purchasePlan";
	}
}
