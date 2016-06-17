package com.zis.bookinfo.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.bookinfo.service.BookService;

public class AddYouLuDataAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private BookService bookService;
	private Integer startId;
	private Integer finalId;
	private String operateType;

	@Validations(intRangeFields = {
			@IntRangeFieldValidator(fieldName = "startId", min = "1", key = "开始ID不能小于1"),
			@IntRangeFieldValidator(fieldName = "finalId", min = "1", key = "结束ID不能小于1") }, requiredFields = {
			@RequiredFieldValidator(fieldName = "startId", key = "开始ID不能为空"),
			@RequiredFieldValidator(fieldName = "finalId", key = "结束ID不能为空") })
	public String addYouLuData() {
		if (startId <= finalId) {
			Integer activeTaskCount = bookService
					.asynchronousCaptureBookInfoFromYouLuNet(startId, finalId, operateType);
			ActionContext context = ActionContext.getContext();
			if (activeTaskCount <= 1)
				activeTaskCount = 1;
			context.put("activeTaskCount", activeTaskCount + "");
		}
		return SUCCESS;
	}

	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public Integer getFinalId() {
		return finalId;
	}

	public void setFinalId(Integer finalId) {
		this.finalId = finalId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public BookService getBookService() {
		return bookService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

}
