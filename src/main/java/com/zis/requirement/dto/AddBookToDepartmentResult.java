package com.zis.requirement.dto;

import java.util.ArrayList;
import java.util.List;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 添加图书到指定专业的操作结果
 * 
 * @author yz
 * 
 */
public class AddBookToDepartmentResult {

	private boolean success;
	private String failReason;
	private List<Bookinfo> bookList = new ArrayList<Bookinfo>();

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public List<Bookinfo> getBookList() {
		return bookList;
	}

	public void add(Bookinfo book) {
		bookList.add(book);
	}

	public AddBookToDepartmentResult(boolean success, String failReason) {
		this.success = success;
		this.failReason = failReason;
	}
}
