package com.zis.purchase.dto;

/**
 * 创建入库单专用DTO
 * 
 * @author yz
 * 
 */
public class InwarehouseCreateDTO {

	private String bizType; // 入库业务类型
	private String purchaseOperator; // 采购员
	private String inwarehouseOperator; // 入库员
	private String[] stockPosLabel; // 库位标签
	private Integer[] stockPosCapacity; // 库位容量
	private String memo;

	public String getPurchaseOperator() {
		return purchaseOperator;
	}

	public void setPurchaseOperator(String purchaseOperator) {
		this.purchaseOperator = purchaseOperator;
	}

	public String getInwarehouseOperator() {
		return inwarehouseOperator;
	}

	public void setInwarehouseOperator(String inwarehouseOperator) {
		this.inwarehouseOperator = inwarehouseOperator;
	}

	public String[] getStockPosLabel() {
		return stockPosLabel;
	}

	public void setStockPosLabel(String[] stockPosLabel) {
		this.stockPosLabel = stockPosLabel;
	}

	public Integer[] getStockPosCapacity() {
		return stockPosCapacity;
	}

	public void setStockPosCapacity(Integer[] stockPosCapacity) {
		this.stockPosCapacity = stockPosCapacity;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

}
