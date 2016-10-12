package com.zis.requirement.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.controllertemplate.PaginationQueryController;
import com.zis.common.mvc.ext.WebHelper;
import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.bean.BookRequireImportDetailStatus;
import com.zis.requirement.biz.BookRequireImportBO;

/**
 * 已导入书单的明细记录
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/requirement")
public class BookRequireImportDetailViewController extends PaginationQueryController<BookRequireImportDetail> {

	@Autowired
	private BookRequireImportBO bookRequireImportBO;

	@RequestMapping(value = "/viewBookRequireImportDetailForMatched")
	public String executeQuery(ModelMap context, HttpServletRequest request) {
		// TODO 设置查询条件
		Pageable page = WebHelper.buildPageRequest(request);
		Page<BookRequireImportDetail> pageList = this.bookRequireImportBO.findAllBookRequireImportDetail(page);
		return super.executeQuery(context, request, pageList, page);
	}

	@Override
	protected String setActionUrl(HttpServletRequest request) {
		return "requirement/viewBookRequireImportDetailForMatched";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		String status = request.getParameter("status");
		StringBuilder condition = new StringBuilder();
		String taskIdStr = request.getParameter("taskId");
		Integer taskId;
		if (StringUtils.isNumeric(taskIdStr)) {
			taskId = Integer.parseInt(taskIdStr);
			condition.append("taskId=" + taskId + "&");
		} else {
			throw new IllegalArgumentException("taskId is error");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("status=" + status + "&");
		}
		return condition.toString();
	}

	@Override
	protected DetachedCriteria buildQueryCondition(HttpServletRequest request) {
		String status = request.getParameter("status");
		String taskIdStr = request.getParameter("taskId");
		Integer taskId;
		if (StringUtils.isNumeric(taskIdStr)) {
			taskId = Integer.parseInt(taskIdStr);
		} else {
			throw new IllegalArgumentException("taskId is error");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(BookRequireImportDetail.class);
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.eq("batchId", taskId));
		criteria.addOrder(Order.asc("gmtCreate"));
		return criteria;
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		String status = request.getParameter("status");
		if (BookRequireImportDetailStatus.BOOK_NOT_MATCHED.equals(status)) {
			return "requirement/bookRequireImportDetailForBookNotMatched";
		} else if (BookRequireImportDetailStatus.DEPARTMENT_NOT_MATCHED.equals(status)) {
			return "requirement/bookRequireImportDetailForDepartmentNotMatched";
		} else {
			return "requirement/bookRequireImportDetailForMatched";
		}
	}
}
