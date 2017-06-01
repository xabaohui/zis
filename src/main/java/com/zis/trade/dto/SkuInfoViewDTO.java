package com.zis.trade.dto;

public class SkuInfoViewDTO {
	
	private Integer skuId; // 系统skuId
	private String isbn;
	private String itemName; // 商品名称
	private Integer itemCount; // 数量
	private Double zisPrice;
	private Double itemPrice; // 单价 计算后
	private Integer resultInt;// 返回的第几行记录

	public Double getZisPrice() {
		return zisPrice;
	}

	public void setZisPrice(Double zisPrice) {
		this.zisPrice = zisPrice;
	}

	public Integer getResultInt() {
		return resultInt;
	}

	public void setResultInt(Integer resultInt) {
		this.resultInt = resultInt;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
}
