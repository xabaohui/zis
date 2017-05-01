package com.zis.shop.dto;

/**
 * 下载失败记录DTO
 * @author think
 *
 */
public class ShopDownLoadFailDTO {

	private String title;

	private String outerId;

	private String failReason;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}
