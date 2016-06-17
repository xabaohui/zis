package com.zis.toolkit.action;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书名称修复工具-批量替换
 * 
 * @author yz
 * 
 */
public class BookNameReplaceAction extends BaseBookFixAction {

	private static final long serialVersionUID = 5747894532711900026L;

	private String orig;
	private String replace;

	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 修复图书名称
	 * 
	 * @return
	 */
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "orig", trim = true, key = "原始内容不能为空")
			})
	public String batchReplaceBookName() {
		List<String> list = bookInfoToolkit.updateBatchReplaceBookName(orig, replace);
		showResult(list);
		return SUCCESS;
	}

	public String getOrig() {
		return orig;
	}

	public void setOrig(String orig) {
		this.orig = orig;
	}

	public String getReplace() {
		return replace;
	}

	public void setReplace(String replace) {
		this.replace = replace;
	}

	public BookInfoToolkit getBookInfoToolkit() {
		return bookInfoToolkit;
	}

	public void setBookInfoToolkit(BookInfoToolkit bookInfoToolkit) {
		this.bookInfoToolkit = bookInfoToolkit;
	}
}
