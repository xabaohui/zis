package com.zis.purchase.dto;

/**
 * 采购或者库存表临时导入记录
 * 
 * @author yz
 * 
 */
public class TempImportDTO {

	private String isbn;
	private String data;
	private String additionalInfo;

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
