package com.zis.requirement.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.util.Page;
import com.zis.common.util.PaginationQueryUtil;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.Bookamount;

public class BookAmountQueryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String isbn;
	private String bookName;
	private String school;
	private String institute;
	private String partName;
	private Integer grade;
	private Integer term;
	private String operator;

	// 分页查询
	private Integer pageNow;
	private String pageSource; // 页面来源，点击下一页的，需要经过ISO-8859-1->UTF-8转码

	@Validations(conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "grade", key = "学年只能填数字", shortCircuit = true),
			@ConversionErrorFieldValidator(fieldName = "term", key = "学期只能填数字", shortCircuit = true) })
	/**
	 *  查询教材使用量
	 * @return
	 */
	public String getAmount() {
		// 处理GET请求中的中文字符
		preProcessGetRequestCHN();
		if (term != null) {
			if (term != 1 && term != 2) {
				this.addFieldError("error", "学期在1和2之间");
				return INPUT;
			}
		}
		DetachedCriteria criteria = buildCriteria();
		// 分页查询
		if (pageNow == null || pageNow < 1) {
			pageNow = 1;
		}
		Integer totalCount = PaginationQueryUtil.getTotalCount(criteria);
		Page page = Page
				.createPage(pageNow, Page.DEFAULT_PAGE_SIZE, totalCount);
		@SuppressWarnings("unchecked")
		List<Bookamount> list = PaginationQueryUtil.paginationQuery(criteria,
				page);
		// 将返回结果存入ActionContext中
		ActionContext context = ActionContext.getContext();
		context.put("BookAmount", list);
		context.put("isbn", isbn);
		context.put("bookName", bookName);
		context.put("school", school);
		context.put("institute", institute);
		context.put("partName", partName);
		context.put("grade", grade);
		context.put("term", term);
		context.put("operator", operator);
		setQueryConditionToPage(context);
		context.put("pageNow", pageNow);
		if (page.isHasPre()) {
			context.put("prePage", pageNow - 1);
		}
		if (page.isHasNext()) {
			context.put("nextPage", pageNow + 1);
		}
		return SUCCESS;
	}
	
	private void setQueryConditionToPage(ActionContext context) {
		StringBuilder condition = new StringBuilder();
		if(StringUtils.isNotBlank(isbn)) {
			condition.append("isbn=" + isbn + "&");
		}
		if(StringUtils.isNotBlank(bookName)) {
			condition.append("bookName=" + bookName + "&");
		}
		if(StringUtils.isNotBlank(school)) {
			condition.append("school=" + school + "&");
		}
		if(StringUtils.isNotBlank(institute)) {
			condition.append("institute=" + institute + "&");
		}
		if(StringUtils.isNotBlank(partName)) {
			condition.append("partName=" + partName + "&");
		}
		if(grade != null) {
			condition.append("grade=" + grade + "&");
		}
		if(term != null) {
			condition.append("term=" + term + "&");
		}
		if(StringUtils.isNotBlank(operator)) {
			condition.append("operator=" + operator + "&");
		}
		context.put("queryCondition", condition.toString());
	}

	/**
	 * 查询教材使用量，学生版
	 * 
	 * @return
	 */
	public String getAmountForStu() {
		if (StringUtils.isBlank(operator) || StringUtils.isBlank(school)) {
			return SUCCESS;
		}
		return getAmount();
	}

	// 参数转换，重点处理中文get请求
	private void preProcessGetRequestCHN() {
		// 仅仅对带条件的分页查询使用转化
		if (!"pagination".equals(pageSource)) {
			return;
		}
		if (StringUtils.isNotBlank(bookName)) {
			bookName = ZisUtils.convertGetRequestCHN(bookName);
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
		if (StringUtils.isNotBlank(operator)) {
			operator = ZisUtils.convertGetRequestCHN(operator);
		}
	}

	private DetachedCriteria buildCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(Bookamount.class);
		if (!StringUtils.isBlank(isbn)) {
			dc.add(Restrictions.eq("isbn", isbn));
		}
		if (!StringUtils.isBlank(bookName)) {
			dc.add(Restrictions.like("bookName", "%" + bookName + "%"));
		}
		if (!StringUtils.isBlank(school)) {
			dc.add(Restrictions.like("college", "%" + school + "%"));
		}
		if (!StringUtils.isBlank(institute)) {
			dc.add(Restrictions.like("institute", "%" + institute + "%"));
		}
		if (!StringUtils.isBlank(partName)) {
			dc.add(Restrictions.like("partName", "%" + partName + "%"));
		}
		if (grade != null && grade > 0) {
			dc.add(Restrictions.eq("grade", grade));
		}
		if (term != null && term > 0) {
			dc.add(Restrictions.eq("term", term));
		}
		if (!StringUtils.isBlank(operator)) {
			dc.add(Restrictions.eq("operator", operator));
		}
		dc.addOrder(Order.asc("college")).addOrder(Order.asc("institute"))
				.addOrder(Order.asc("partName")).addOrder(Order.asc("grade"))
				.addOrder(Order.asc("term"));
		return dc;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPageSource() {
		return pageSource;
	}

	public void setPageSource(String pageSource) {
		this.pageSource = pageSource;
	}
}
