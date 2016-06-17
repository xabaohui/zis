package com.zis.requirement.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.Departmentinfo;

public class DepartmentInfoQueryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String school;
	private String institute;
	private String partName;
	// 分页查询
	private Integer pageNow;
	private String pageSource; // 页面来源，点击下一页的，需要经过ISO-8859-1->UTF-8转码

	/**
	 * 按条件查询院校信息
	 * 
	 * @return
	 */
	public String findInfo() {
		// 处理GET请求中的中文字符
		preProcessGetRequestCHN();
		DetachedCriteria criteria = buildCriteria();
		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page
				.createPage(pageNow, Page.DEFAULT_PAGE_SIZE, totalCount);
		@SuppressWarnings("unchecked")
		List<Departmentinfo> list = PaginationQueryUtil.paginationQuery(
				criteria, page);
		// 将返回结果存入ActionContext中
		ActionContext ctx = ActionContext.getContext();
		ctx.put("AllSchoolInfo", list);
		ctx.put("school", school);
		ctx.put("institute", institute);
		ctx.put("partName", partName);
		ctx.put("pageNow", pageNow);
		if (page.isHasPre()) {
			ctx.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			ctx.put("nextPage", pageNow + 1);
		}
		return SUCCESS;
	}

	/**
	 * 查询所有院校信息
	 * 
	 * @return
	 */
	public String getAllInfo() {
		// 处理GET请求中的中文字符
		preProcessGetRequestCHN();
		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		DetachedCriteria criteria = DetachedCriteria
				.forClass(Departmentinfo.class);
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page
				.createPage(pageNow, Page.DEFAULT_PAGE_SIZE, totalCount);
		@SuppressWarnings("unchecked")
		List<Departmentinfo> list = PaginationQueryUtil.paginationQuery(
				criteria, page);
		// 将返回结果存入ActionContext中
		ActionContext ctx = ActionContext.getContext();
		ctx.put("AllSchoolInfo", list);
		ctx.put("school", school);
		ctx.put("institute", institute);
		ctx.put("partName", partName);
		ctx.put("pageNow", pageNow);
		if (page.isHasPre()) {
			ctx.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			ctx.put("nextPage", pageNow + 1);
		}
		return SUCCESS;
	}

	// 参数转换，重点处理中文get请求
	private void preProcessGetRequestCHN() {
		// 仅仅对带条件的分页查询使用转化
		if (!"pagination".equals(pageSource)) {
			return;
		}
		if (StringUtils.isNotBlank(school)) {
			school = ZisUtils.convertGetRequestCHN(school);
		}
		if (StringUtils.isNotBlank(institute)) {
			institute = ZisUtils.convertGetRequestCHN(institute);
		}
		if (StringUtils.isNotBlank(partName)) {
			partName = ZisUtils.convertGetRequestCHN(partName);
		}
	}

	/**
	 * 加criteria条件
	 * 
	 * @param school
	 * @param institute
	 * @param partName
	 * @return
	 */
	private DetachedCriteria buildCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(Departmentinfo.class);
		if (!StringUtils.isBlank(school)) {
			dc.add(Restrictions.like("college", "%" + school + "%"));
		}
		if (!StringUtils.isBlank(institute)) {
			dc.add(Restrictions.like("institute", "%" + institute + "%"));
		}
		if (!StringUtils.isBlank(partName)) {
			dc.add(Restrictions.like("partName", "%" + partName + "%"));
		}
		dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"))
				.addOrder(Order.asc("partName"));
		return dc;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public String getPageSource() {
		return pageSource;
	}

	public void setPageSource(String pageSource) {
		this.pageSource = pageSource;
	}

}
