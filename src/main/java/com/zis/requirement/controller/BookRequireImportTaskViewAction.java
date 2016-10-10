package com.zis.requirement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.controllertemplate.PaginationQueryController;
import com.zis.requirement.bean.BookRequireImportTask;
import com.zis.requirement.bean.BookRequireImportTaskStatus;
import com.zis.requirement.dto.BookRequireImportTaskView;

/**
 * 已导入的书单列表
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/requirement")
public class BookRequireImportTaskViewAction extends PaginationQueryController<BookRequireImportTask> {

	@RequestMapping(value = "/viewBookRequireImportTask")
	public String executeQuery(ModelMap context, HttpServletRequest request) {
		return super.executeQuery(context, request);
	}

	@Override
	protected String setActionUrl(HttpServletRequest request) {
		return "requirement/viewBookRequireImportTask";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		return "";
	}

	@Override
	protected DetachedCriteria buildQueryCondition(HttpServletRequest request) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BookRequireImportTask.class);
		criteria.addOrder(Order.desc("gmtCreate"));
		return criteria;
	}

	@Override
	protected List<BookRequireImportTaskView> transformResult(List<BookRequireImportTask> list) {
		List<BookRequireImportTaskView> viewList = new ArrayList<BookRequireImportTaskView>();
		for (BookRequireImportTask task : list) {
			BookRequireImportTaskView view = new BookRequireImportTaskView();
			BeanUtils.copyProperties(task, view);
			view.setStatusDisplay(BookRequireImportTaskStatus.getDisplay(task.getStatus()));
			viewList.add(view);
		}
		return viewList;
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "requirement/bookRequireImportTaskList";
	}

}