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
public class InwarehouseDetailController extends PaginationQueryController<InwarehouseDetail> {

	/**
	 * 
	 */
	@Autowired
	private BookService bookService;
	@Autowired
	private DoPurchaseService doPurchaseService;

	@RequiresPermissions(value = {"purchase:viewInwarehouseDetail"})
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

	@SuppressWarnings({ "rawtypes" })
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
		if (StringUtils.isBlank(idStr)) {
			throw new IllegalArgumentException("inwarehouseId is null");
		}
		Integer inwarehouseId = Integer.parseInt(idStr);
		InwarehouseView view = this.doPurchaseService.findInwarehouseViewById(inwarehouseId);
		context.put("inwarehouse", view);
	}

	@Override
	protected Specification<InwarehouseDetail> buildQueryCondition(HttpServletRequest request) {
		QueryUtil<InwarehouseDetail> query = new QueryUtil<InwarehouseDetail>();
		String idStr = request.getParameter("inwarehouseId");
		if (StringUtils.isBlank(idStr)) {
			throw new IllegalArgumentException("inwarehouseId is null");
		}
		Integer inwarehouseId = Integer.parseInt(idStr);
		query.eq("inwarehouseId", inwarehouseId);
		return query.getSpecification();
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "purchase/inwarehouseDetail";
	}

	@Override
	protected Page<InwarehouseDetail> buildPageList(Specification<InwarehouseDetail> spec, Pageable page) {
		return this.doPurchaseService.findInwarehouseDetailBySpecPage(spec, page);
	}
}