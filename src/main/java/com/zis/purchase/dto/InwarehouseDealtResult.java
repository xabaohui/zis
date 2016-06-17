package com.zis.purchase.dto;

/**
 * 入库操作结果
 * 
 * @author yz
 * 
 */
public class InwarehouseDealtResult {

	private boolean success;
	private String failReason;
	private boolean positionChange; // 当前库位是否发生变化
	private String prePosLabel; // 上一个库位标签
	private String curPosLabel; // 入库商品所在库位标签
	private Integer totalAmount;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getPositionChange() {
		return positionChange;
	}

	public void setPositionChange(boolean positionChange) {
		this.positionChange = positionChange;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getPrePosLabel() {
		return prePosLabel;
	}

	public void setPrePosLabel(String prePosLabel) {
		this.prePosLabel = prePosLabel;
	}

	public String getCurPosLabel() {
		return curPosLabel;
	}

	public void setCurPosLabel(String curPosLabel) {
		this.curPosLabel = curPosLabel;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
}