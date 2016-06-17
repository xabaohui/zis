package com.zis.requirement.action;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zis.common.actiontemplate.PaginationQueryAction;
import com.zis.requirement.bean.BookRequireImportDetail;
import com.zis.requirement.bean.BookRequireImportDetailStatus;

/**
 * 已导入书单的明细记录
 * @author yz
 *
 */
public class BookRequireImportDetailViewAction extends
		PaginationQueryAction<BookRequireImportDetail> {

	private static final long serialVersionUID = 1L;

	private String status;
	private Integer taskId;

	@Override
	protected String setActionUrl() {
		if (BookRequireImportDetailStatus.BOOK_NOT_MATCHED.equals(status)) {
			return "requirement/viewBookRequireImportDetailForBookNotMatched";
		} else if (BookRequireImportDetailStatus.DEPARTMENT_NOT_MATCHED.equals(status)) {
			return "requirement/viewBookRequireImportDetailForDepartmentNotMatched";
		} else {
			return "requirement/viewBookRequireImportDetailForMatched";
		}
	}

	@Override
	protected String setActionUrlQueryCondition() {
		StringBuilder condition = new StringBuilder();
		if (taskId != null) {
			condition.append("taskId=" + taskId + "&");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("status=" + status + "&");
		}
		return condition.toString();
	}

	@Override
	protected DetachedCriteria buildQueryCondition() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BookRequireImportDetail.class);
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.eq("batchId", taskId));
		criteria.addOrder(Order.asc("gmtCreate"));
		return criteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
}
