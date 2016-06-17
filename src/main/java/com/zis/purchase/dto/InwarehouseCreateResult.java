package com.zis.purchase.dto;

/**
 * 入库单创建结果
 * 
 * @author yz
 * 
 */
public class InwarehouseCreateResult {

	private boolean isSuccess;
	private Integer inwarehouseId; // 入库单ID
	private String failReason;
	private String currentPosition;// 当前可用库位

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Integer getInwarehouseId() {
		return inwarehouseId;
	}

	public void setInwarehouseId(Integer inwarehouseId) {
		this.inwarehouseId = inwarehouseId;
	}
}
