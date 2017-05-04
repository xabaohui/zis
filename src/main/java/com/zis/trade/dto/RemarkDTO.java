package com.zis.trade.dto;

import com.zis.trade.entity.Order;

/**
 * 备注ajax DTO
 * @author think
 *
 */
public class RemarkDTO extends Order {

	private boolean success;
	private String failReason;

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
}
