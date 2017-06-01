package com.zis.storage.dto;

import javax.validation.constraints.NotNull;

public class InwarehouseCreateDto{
	
	private Integer ioBatchId;
	@NotNull(message = "库位名称必须输入")
	private String[] stockPosLabel;
	@NotNull(message = "库位容量必须输入")
	private Integer[] stockPosCapacity;
	@NotNull(message = "备注必须输入")
	private String memo;
	private String bizType;
	private String purchaseOperator;
	
	public String getPurchaseOperator() {
		return purchaseOperator;
	}

	public void setPurchaseOperator(String purchaseOperator) {
		this.purchaseOperator = purchaseOperator;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getIoBatchId() {
		return ioBatchId;
	}

	public void setIoBatchId(Integer ioBatchId) {
		this.ioBatchId = ioBatchId;
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
}
