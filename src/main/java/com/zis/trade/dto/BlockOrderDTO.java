package com.zis.trade.dto;

import com.zis.trade.entity.Order;

/**
 * 拦截ajax DTO
 * 
 * @author think
 *
 */
public class BlockOrderDTO extends Order{
	
	private boolean success;
	private String failReason;

	public boolean getSuccess() {
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
