package com.zis.toolkit.action;

import com.zis.purchase.bean.TempImportDetail;

public class StockPosCheckRecord extends TempImportDetail {

	private static final long serialVersionUID = 1L;
	private String bookName;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
