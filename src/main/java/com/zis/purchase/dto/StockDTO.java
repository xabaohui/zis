package com.zis.purchase.dto;

public class StockDTO {

	private Integer bookId;
	private Integer stockBalance;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}
}