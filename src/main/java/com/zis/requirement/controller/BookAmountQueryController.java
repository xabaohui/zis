package com.zis.requirement.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.mvc.ext.WebHelper;
import com.zis.requirement.bean.BookAmount;
import com.zis.requirement.biz.BookAmountService;

@Controller
@RequestMapping(value = "/requirement")
public class BookAmountQueryController {

	@Autowired
	BookAmountService bookAmountService;

	// TODO 验证框架
	// @Validations(conversionErrorFields = {
	// @ConversionErrorFieldValidator(fieldName = "grade", key = "学年只能填数字",
	// shortCircuit = true),
	// @ConversionErrorFieldValidator(fieldName = "term", key = "学期只能填数字",
	// shortCircuit = true) })
	/**
	 * 查询教材使用量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAmountAction")
	public String getAmount(String isbn, String bookName, String school, String institute, String partName,
			Integer grade, Integer term, String operator, HttpServletRequest request, ModelMap context) {
		if (term != null) {
			if (term != 1 && term != 2) {
				// this.addFieldError("error", "学期在1和2之间");
				return "error";
			}
		}
		// TODO 创建查询条件
		// 分页查询
		Pageable page = WebHelper.buildPageRequest(request);
		Page<BookAmount> pList = this.bookAmountService.findAll(page);
		// 将返回结果存入ActionContext中
		context.put("list", pList.getContent());
		context.put("isbn", isbn);
		context.put("bookName", bookName);
		context.put("school", school);
		context.put("institute", institute);
		context.put("partName", partName);
		context.put("grade", grade);
		context.put("term", term);
		context.put("operator", operator);
		context.put("operator", operator);
		context.put("page", page.getPageNumber()+1);
		setQueryConditionToPage(isbn, bookName, school, institute, partName, grade, term, operator, request, context);
		if (pList.hasPrevious()) {
			context.put("prePage", page.previousOrFirst().getPageNumber());
		}
		if (pList.hasNext()) {
			context.put("nextPage", page.next().getPageNumber());
		}
		return "requirement/getAmount";
	}

	private void setQueryConditionToPage(String isbn, String bookName, String school, String institute, String partName,
			Integer grade, Integer term, String operator, HttpServletRequest request, ModelMap context) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(isbn)) {
			condition.append("isbn=" + isbn + "&");
		}
		if (StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if (StringUtils.isNotBlank(school)) {
			condition.append("school=" + school + "&");
		}
		if (StringUtils.isNotBlank(institute)) {
			condition.append("institute=" + institute + "&");
		}
		if (StringUtils.isNotBlank(partName)) {
			condition.append("partName=" + partName + "&");
		}
		if (grade != null) {
			condition.append("grade=" + grade + "&");
		}
		if (term != null) {
			condition.append("term=" + term + "&");
		}
		if (StringUtils.isNotBlank(operator)) {
			condition.append("operator=" + operator + "&");
		}
		context.put("queryCondition", condition.toString());
	}

	/**
	 * 查询教材使用量，学生版
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAmountForStu")
	public String getAmountForStu(String isbn, String bookName, String school, String institute, String partName,
			Integer grade, Integer term, String operator, HttpServletRequest request, ModelMap context) {
		if (StringUtils.isBlank(operator) || StringUtils.isBlank(school)) {
			return "requirement/getAmountForStu";
		}
		return "forward:/requirement/getAmountAction";
	}

	// private DetachedCriteria buildCriteria() {
	// DetachedCriteria dc = DetachedCriteria.forClass(BookAmount.class);
	// if (!StringUtils.isBlank(isbn)) {
	// dc.add(Restrictions.eq("isbn", isbn));
	// }
	// if (!StringUtils.isBlank(bookName)) {
	// dc.add(Restrictions.like("bookName", "%" + bookName + "%"));
	// }
	// if (!StringUtils.isBlank(school)) {
	// dc.add(Restrictions.like("college", "%" + school + "%"));
	// }
	// if (!StringUtils.isBlank(institute)) {
	// dc.add(Restrictions.like("institute", "%" + institute + "%"));
	// }
	// if (!StringUtils.isBlank(partName)) {
	// dc.add(Restrictions.like("partName", "%" + partName + "%"));
	// }
	// if (grade != null && grade > 0) {
	// dc.add(Restrictions.eq("grade", grade));
	// }
	// if (term != null && term > 0) {
	// dc.add(Restrictions.eq("term", term));
	// }
	// if (!StringUtils.isBlank(operator)) {
	// dc.add(Restrictions.eq("operator", operator));
	// }
	// dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute")).addOrder(Order.asc("partName"))
	// .addOrder(Order.asc("grade")).addOrder(Order.asc("term"));
	// return dc;
	// }
}
