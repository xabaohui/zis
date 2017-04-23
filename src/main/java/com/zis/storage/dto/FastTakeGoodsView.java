package com.zis.storage.dto;

import java.util.List;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * 
 * @author think
 *
 */
@DataTransferObject
public class FastTakeGoodsView {
	
	private Integer skuId;
	
	private boolean success;

	private String failReason;
	
	private List<TakeGood> goods;

	private String bookInfoStr;
	
	
	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getBookInfoStr() {
		return bookInfoStr;
	}

	public void setBookInfoStr(String bookInfoStr) {
		this.bookInfoStr = bookInfoStr;
	}

	public List<TakeGood> getGoods() {
		return goods;
	}

	public void setGoods(List<TakeGood> goods) {
		this.goods = goods;
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
