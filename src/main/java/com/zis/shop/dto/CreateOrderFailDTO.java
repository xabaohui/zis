package com.zis.shop.dto;

public class CreateOrderFailDTO {

	private boolean success;
	private String failReason;
	private String outOrderNumber;
	private String receiverName;
	private String receiverPhone;
	private String itemId;
	private String itemOutNum;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getOutOrderNumber() {
		return outOrderNumber;
	}

	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemOutNum() {
		return itemOutNum;
	}

	public void setItemOutNum(String itemOutNum) {
		this.itemOutNum = itemOutNum;
	}
}
