package com.zis.trade.dto;

import java.util.List;

import com.zis.storage.entity.StorageRepoInfo;

/**
 * 订单分配仓库DTO
 * 
 * @author think
 * 
 */
public class ArrangeOrderToRepoDTO {

	private Integer orderId;
	private List<StorageRepoInfo> repoList;
	private String forwardUrl;
	private boolean success;
	private String failReason;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<StorageRepoInfo> getRepoList() {
		return repoList;
	}

	public void setRepoList(List<StorageRepoInfo> repoList) {
		this.repoList = repoList;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

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
