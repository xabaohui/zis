package com.zis.purchase.dto;

/**
 * 采购或者库存表临时导入记录
 * 
 * @author yz
 * 
 */
public class TempImportDTO {

	private String isbn;
	private Integer amount;
	private String additionalInfo;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
