package com.zis.requirement.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

import com.zis.common.actiontemplate.PaginationQueryAction;
import com.zis.requirement.bean.BookRequireImportTask;
import com.zis.requirement.bean.BookRequireImportTaskStatus;
import com.zis.requirement.dto.BookRequireImportTaskView;

/**
 * 已导入的书单列表
 * @author yz
 *
 */
public class BookRequireImportTaskViewAction extends PaginationQueryAction<BookRequireImportTask>{

	private static final long serialVersionUID = 1L;

	@Override
	protected String setActionUrl() {
		return "requirement/viewBookRequireImportTask";
	}

	@Override
	protected String setActionUrlQueryCondition() {
		return "";
	}

	@Override
	protected DetachedCriteria buildQueryCondition() {
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
}