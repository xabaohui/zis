package com.zis.purchase.dto;

/**
 * 库存导入DTO
 * 
 * @author yz
 * 
 */
public class StockImportDTO {

	private String isbn;
	private Integer stockBalance;
	private String failReason;

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}
}
