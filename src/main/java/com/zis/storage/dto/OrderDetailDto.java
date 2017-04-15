package com.zis.storage.dto;

/**
 * 将json转换为bean
 * 
 * @author think
 * 
 */
public class OrderDetailDto {

	private String bookTitle;
	private Integer bookAmount;

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Integer getBookAmount() {
		return bookAmount;
	}

	public void setBookAmount(Integer bookAmount) {
		this.bookAmount = bookAmount;
	}
}
