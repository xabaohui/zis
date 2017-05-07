package com.zis.trade.dto;

import java.util.List;

import com.zis.bookinfo.bean.Bookinfo;

public class CreateOrderQuerySkuInfoViewDTO {

	private boolean success;
	private String failReason;
	private List<SkuInfo> skuList;

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

	public List<SkuInfo> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuInfo> skuList) {
		this.skuList = skuList;
	}

	public static class SkuInfo extends Bookinfo {

		private static final long serialVersionUID = -1583735268136423891L;
		private Integer bookAmount;

		public Integer getBookAmount() {
			return bookAmount;
		}

		public void setBookAmount(Integer bookAmount) {
			this.bookAmount = bookAmount;
		}

	}
}
