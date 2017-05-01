package com.zis.shop.dto;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 检查网店下载数据DTO
 * @author think
 *
 */
public class CheckOutIdDTO {
	
	private Bookinfo book;
	
	private boolean isSuccess;
	
	private String failMsg;

	public Bookinfo getBook() {
		return book;
	}

	public void setBook(Bookinfo book) {
		this.book = book;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
}
