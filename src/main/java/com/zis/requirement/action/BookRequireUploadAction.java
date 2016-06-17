package com.zis.requirement.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.actiontemplate.CommonImportAction;
import com.zis.requirement.biz.BookAmountService;
import com.zis.requirement.dto.BookRequireUploadDTO;

/**
 * 导入书单的Action
 * 
 * @author yz
 * 
 */
public class BookRequireUploadAction extends
		CommonImportAction<BookRequireUploadDTO> {

	private static final long serialVersionUID = -4772164336355491786L;
	private String college;
	private String operator;
	private String memo;

	private BookAmountService bookAmountService;

	@Validations(requiredFields = {
			@RequiredFieldValidator(fieldName = "excelFile", key = "文件必须输入"),
			@RequiredFieldValidator(fieldName = "college", key = "操作员必须输入"),
			@RequiredFieldValidator(fieldName = "operator", key = "操作员必须输入"),
			@RequiredFieldValidator(fieldName = "memo", key = "操作备注必须输入"), })
	@Override
	protected String initTemplatePath() {
		return "书单模板.xls";
	}

	@Override
	protected Logger initLogger() {
		return Logger.getLogger(BookRequireUploadAction.class);
	}

	@Override
	protected BookRequireUploadDTO getInstance() {
		return new BookRequireUploadDTO();
	}

	@Override
	protected String getFaildRecordMessage(BookRequireUploadDTO failRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void saveImportRecord(List<BookRequireUploadDTO> list) {
		// this.bookAmountService.saveTempBookRequireImportDetails(list,
		// college, operator,
		// memo);
	}

	@Override
	protected Map<String, Integer> initPropMapping() {
		Map<String, Integer> mapping = new HashMap<String, Integer>();
		mapping.put("isbn", 0);
		mapping.put("bookName", 1);
		mapping.put("bookAuthor", 2);
		mapping.put("bookEdition", 3);
		mapping.put("bookPublisher", 4);
		mapping.put("college", 5);
		mapping.put("institute", 6);
		mapping.put("partName", 7);
		mapping.put("grade", 8);
		mapping.put("term", 9);
		mapping.put("classNum", 10);
		mapping.put("amount", 11);
		return mapping;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setBookAmountService(BookAmountService bookAmountService) {
		this.bookAmountService = bookAmountService;
	}
}
