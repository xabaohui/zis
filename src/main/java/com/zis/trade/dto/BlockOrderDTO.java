package com.zis.trade.dto;

import com.zis.trade.entity.Order;

public class BlockOrderDTO extends Order{
	private boolean success;
	private String failMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailMessage() {
		return failMessage;
	}

	public void setFailMessage(String failMessage) {
		this.failMessage = failMessage;
	}
}
