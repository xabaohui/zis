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
import com.zis.purchase.bean.InwarehouseDetail;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.purchase.dto.InwarehouseDetailView;
import com.zis.purchase.dto.InwarehouseView;

/**
 * 入库单明细
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class InwarehouseDetailController extends
		PaginationQueryController<InwarehouseDetail> {

	/**
	 * 
	 */
	@Autowired
	private BookService bookService;
	@Autowired
	private DoPurchaseService doPurchaseService;

	@RequestMapping(value = "/viewInwarehouseDetail")
	public String executeQuery(ModelMap context, HttpServletRequest request) {
		return super.executeQuery(context, request);
	}

	@Override
	protected String setActionUrl(HttpServletRequest request) {
		return "purchase/viewInwarehouseDetail";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		String inwarehouseId = request.getParameter("inwarehouseId");
		return "inwarehouseId=" + inwarehouseId + "&";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List transformResult(List<InwarehouseDetail> list) {
		// 结果集转换，方便页面展示
		List<InwarehouseDetailView> viewList = new ArrayList<InwarehouseDetailView>();
		for (InwarehouseDetail detail : list) {
			Bookinfo book = this.bookService.findBookById(detail.getBookId());
			InwarehouseDetailView view = new InwarehouseDetailView();
			BeanUtils.copyProperties(book, view);
			BeanUtils.copyProperties(detail, view);
			viewList.add(view);
		}
		return viewList;
	}

	@Override
	protected void doBeforeReturn(ModelMap context, HttpServletRequest request) {
		String idStr = request.getParameter("inwarehouseId");
		if(StringUtils.isBlank(idStr)) {
			throw new IllegalArgumentException("inwarehouseId is null");
		}
		Integer inwarehouseId = Integer.parseInt(idStr);
		InwarehouseView view = this.doPurchaseService
				.findInwarehouseViewById(inwarehouseId);
		context.put("inwarehouse", view);
	}

	@Override
	protected DetachedCriteria buildQueryCondition(HttpServletRequest request) {
		String idStr = request.getParameter("inwarehouseId");
		if(StringUtils.isBlank(idStr)) {
			throw new IllegalArgumentException("inwarehouseId is null");
		}
		Integer inwarehouseId = Integer.parseInt(idStr);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(InwarehouseDetail.class);
		criteria.add(Restrictions.eq("inwarehouseId", inwarehouseId));
		return criteria;
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "purchase/inwarehouseDetail";
	}
}