package com.zis.common.actiontemplate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;

/**
 * 分页查询Action基类
 * 
 * @author yz
 * 
 */
public abstract class PaginationQueryAction<T> extends ActionSupport {

	private Integer pageNow;
	private String pageSource;

	public static final String PAGE_SOURCE_DEFAULT_VALUE = "pagination";

	protected Logger logger = Logger.getLogger(PaginationQueryAction.class);

	public String executeQuery() {
		try {
			if (pageNow == null || pageNow < 1) {
				pageNow = 1;
			}
			if (PAGE_SOURCE_DEFAULT_VALUE.equals(pageSource)) {
				preProcessGetRequestCHN();
			}
			doBeforeQuery();
			// 分页查询
			DetachedCriteria queryCondition = buildQueryCondition();
			int totalCount = PaginationQueryUtil.getTotalCount(queryCondition);
			Page page = Page.createPage(pageNow, Page.DEFAULT_PAGE_SIZE,
					totalCount);
			List<T> list = PaginationQueryUtil.paginationQuery(queryCondition, page);
			// 转换结果
			List resultList = transformResult(list);
			// 设置页面参数
			ActionContext context = ActionContext.getContext();
			context.put(setResultListLabel(), resultList);
			context.put("actionUrl", setActionUrl());
			context.put("queryCondition", setActionUrlQueryCondition());
			context.put("pageNow", pageNow);
			if (page.isHasPre()) {
				context.put("prePage", pageNow - 1);
			}
			if (page.isHasNext()) {
				context.put("nextPage", pageNow + 1);
			}
			// 设置其他页面参数
			doBeforeReturn();
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			logger.error("查询临时导入记录时出错，" + e.getMessage(), e);
			return INPUT;
		}
	}

	/**
	 * 参数转换，重点处理中文get请求，由子类进行实现
	 */
	protected void preProcessGetRequestCHN() {

	}

	protected void doBeforeQuery() {
	}

	/**
	 * 主方法执行完成后，页面开始渲染之前执行的方法，由子类进行扩展
	 */
	protected void doBeforeReturn() {
	}

	/**
	 * 设置分页查询跳转目标url
	 */
	protected abstract String setActionUrl();

	/**
	 * 设置分页查询条件，确保翻页过程中查询条件不丢失
	 */
	protected abstract String setActionUrlQueryCondition();

	/**
	 * 对查询结果集进行转换，以适应页面展示的要求，默认实现是不转换，可由子类进行扩展
	 * 
	 * @param list
	 * @return
	 */
	protected List transformResult(List<T> list) {
		return list;
	}

	/**
	 * 结果列表标签，供jsp页面调用，默认值是resultList，子类可以重写
	 */
	protected String setResultListLabel() {
		return "resultList";
	}

	/**
	 * 设置查询条件
	 * 
	 * @return
	 */
	protected abstract DetachedCriteria buildQueryCondition();

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
