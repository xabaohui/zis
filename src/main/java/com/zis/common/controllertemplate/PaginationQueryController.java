package com.zis.common.controllertemplate;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;

/**
 * 分页查询Action基类
 * 
 * @author yz
 * 
 */
public abstract class PaginationQueryController<T> {

	public static final String PAGE_SOURCE_DEFAULT_VALUE = "pagination";

	protected Logger logger = Logger.getLogger(PaginationQueryController.class);

	// 传入跳转地址 错误地址
	@SuppressWarnings("rawtypes")
	public String executeQuery(ModelMap context, HttpServletRequest request, Page<T> pageList, Pageable page) {
		if (pageList == null || page == null) {
			return getFailPage();
		}
		doBeforeQuery(request);
		// 分页查询
		List<T> list = pageList.getContent();
		// 转换结果
		List resultList = transformResult(list);
		// 设置页面参数
		context.put(setResultListLabel(), resultList);
		context.put("actionUrl", setActionUrl(request));
		context.put("queryCondition", setActionUrlQueryCondition(request));
		context.put("page", page.getPageNumber());
		if (pageList.hasPrevious()) {
			context.put("prePage", page.previousOrFirst().getPageNumber());
		}
		if (pageList.hasNext()) {
			context.put("nextPage", page.next().getPageNumber());
		}
		// 设置其他页面参数
		doBeforeReturn(context, request);

		return getSuccessPage(request);
		// TODO 验证框架
		// this.addActionError(e.getMessage());
		// logger.error("查询临时导入记录时出错，" + e.getMessage(), e); // TODO 验证框架
		// this.addActionError(e.getMessage());
		// logger.error("查询临时导入记录时出错，" + e.getMessage(), e);

	}

	/**
	 * 设置失败跳转路径
	 */
	protected abstract String getFailPage();

	/**
	 * 设置成功跳转路径
	 */
	protected abstract String getSuccessPage(HttpServletRequest request);

	/**
	 * 参数转换，重点处理中文get请求，由子类进行实现
	 */
	protected void preProcessGetRequestCHN(HttpServletRequest request) {

	}

	protected void doBeforeQuery(HttpServletRequest request) {
	}

	/**
	 * 主方法执行完成后，页面开始渲染之前执行的方法，由子类进行扩展
	 */
	protected void doBeforeReturn(ModelMap context, HttpServletRequest request) {
	}

	/**
	 * 设置分页查询跳转目标url
	 */
	protected abstract String setActionUrl(HttpServletRequest request);

	/**
	 * 设置分页查询条件，确保翻页过程中查询条件不丢失
	 */
	protected abstract String setActionUrlQueryCondition(HttpServletRequest request);

	/**
	 * 对查询结果集进行转换，以适应页面展示的要求，默认实现是不转换，可由子类进行扩展
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
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
	protected abstract DetachedCriteria buildQueryCondition(HttpServletRequest request);

}
