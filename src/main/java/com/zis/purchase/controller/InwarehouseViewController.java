package com.zis.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.controllertemplate.PaginationQueryController;
import com.zis.purchase.bean.Inwarehouse;
import com.zis.purchase.bean.InwarehouseBizType;
import com.zis.purchase.bean.InwarehouseStatus;
import com.zis.purchase.dto.InwarehouseView;

/**
 * 入库单列表
 * 
 * @author yz
 * 
 */
@Controller
@RequestMapping(value = "/purchase")
public class InwarehouseViewController extends
		PaginationQueryController<Inwarehouse> {
	@RequestMapping(value = "/viewInwarehouseList")
	public String executeQuery(ModelMap context, HttpServletRequest request) {
		return super.executeQuery(context, request);
	}

	@Override
	protected String setActionUrl() {
		return "purchase/viewInwarehouseList";
	}

	@Override
	protected String setActionUrlQueryCondition(HttpServletRequest request) {
		return "";
	}

	@Override
	protected DetachedCriteria buildQueryCondition(HttpServletRequest request) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(Inwarehouse.class);
		criteria.add(Restrictions.ne("status", InwarehouseStatus.CANCEL));
		criteria.addOrder(Order.desc("gmtCreate"));
		return criteria;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List transformResult(List<Inwarehouse> list) {
		List<InwarehouseView> resultList = new ArrayList<InwarehouseView>();
		for (Inwarehouse in : list) {
			InwarehouseView record = new InwarehouseView();
			BeanUtils.copyProperties(in, record);
			record.setBizTypeDisplay(InwarehouseBizType.getDisplay(in
					.getBizType()));
			record.setStatusDisplay(InwarehouseStatus.getDisplay(in.getStatus()));
			resultList.add(record);
		}
		return resultList;
	}

	@Override
	protected String getFailPage() {
		return "error";
	}

	@Override
	protected String getSuccessPage(HttpServletRequest request) {
		return "purchase/inwarehouseList";
	}

}
