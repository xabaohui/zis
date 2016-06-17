package com.zis.toolkit.action;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书名称修复工具
 * @author yz
 *
 */
public class BookNameFixAction extends BaseBookFixAction {

	private static final long serialVersionUID = 5747894532711900026L;

	private String startLabel;
	private String keyword;

	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 修复图书名称
	 * 
	 * @return
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "startLabel", trim = true, key = "起始字符不能为空"),
			@RequiredStringValidator(fieldName = "keyword", trim = true, key = "关键字不能为空"), })
	public String fixBookName() {
		List<String> list = bookInfoToolkit.updateFixBookName(startLabel, keyword);
		showResult(list);
		return SUCCESS;
	}

	public String getStartLabel() {
		return startLabel;
	}

	public void setStartLabel(String startLabel) {
		this.startLabel = startLabel;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public BookInfoToolkit getBookInfoToolkit() {
		return bookInfoToolkit;
	}

	public void setBookInfoToolkit(BookInfoToolkit bookInfoToolkit) {
		this.bookInfoToolkit = bookInfoToolkit;
	}
}
