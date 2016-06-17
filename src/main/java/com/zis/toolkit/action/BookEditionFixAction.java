package com.zis.toolkit.action;

import java.util.List;

import com.zis.toolkit.service.BookInfoToolkit;

/**
 * 图书版次修复工具
 * @author yz
 *
 */
public class BookEditionFixAction extends BaseBookFixAction {

	private static final long serialVersionUID = 5747894532711900026L;

	private BookInfoToolkit bookInfoToolkit;

	/**
	 * 批量修复版次：删除书名中“修订版”、“修订本”，并追加到版次最后
	 * 
	 * @return
	 */
	public String batchFixEditionByBookName() {
		List<String> list = bookInfoToolkit.updateFixEditionByBookName("修订版");
		List<String> list2 = bookInfoToolkit.updateFixEditionByBookName("修订本");
		list.addAll(list2);
		showResult(list);
		return SUCCESS;
	}
	
	/**
	 * 批量修复版次：使用书名中的“第X版”代替版次的值
	 * @return
	 */
	public String batchReplaceEditionByBookName() {
		List<String> list = bookInfoToolkit.updateReplaceEditionByBookName();
		showResult(list);
		return SUCCESS;
	}

	public BookInfoToolkit getBookInfoToolkit() {
		return bookInfoToolkit;
	}

	public void setBookInfoToolkit(BookInfoToolkit bookInfoToolkit) {
		this.bookInfoToolkit = bookInfoToolkit;
	}
}
