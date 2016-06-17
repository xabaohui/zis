package com.zis.purchase.dto;

import java.util.Date;

import com.zis.purchase.bean.PurchaseDetail;

public class PurchaseDetailView extends PurchaseDetail {

	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private String bookEdition;
	private Date publishDate;
	private String statusDisplay;
	private boolean isNewEdition;

	public boolean getIsNewEdition() {
		return isNewEdition;
	}

	public void setNewEdition(boolean isNewEdition) {
		this.isNewEdition = isNewEdition;
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

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}
}
