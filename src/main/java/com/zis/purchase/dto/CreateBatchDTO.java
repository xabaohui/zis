package com.zis.purchase.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class CreateBatchDTO {

	@NotBlank(message = "入库类型必须选择")
	private String bizType;
	private String purchaseOperator;
	@NotBlank(message = "入库操作员必须输入")
	private String inwarehouseOperator;
	@NotBlank(message = "库位名称必须输入")
	private String[] stockPosLabel;
	@NotNull(message = "库位容量必须输入")
	private Integer[] stockPosCapacity;
	private String memo;

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

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
}
