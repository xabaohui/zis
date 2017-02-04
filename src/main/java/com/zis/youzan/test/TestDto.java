package com.zis.youzan.test;

public class TestDto {
	private String isbn ;
	private String amount;
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "TestDto [isbn=" + isbn + ", amount=" + amount + "]";
	} 
}
