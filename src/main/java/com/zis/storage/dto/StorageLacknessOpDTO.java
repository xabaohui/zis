package com.zis.storage.dto;

import com.zis.storage.entity.StorageIoDetail;

/**
 * 
 * @author yz
 * 
 */
public class StorageLacknessOpDTO extends StorageIoDetail {

	private boolean lacknessMatchNewPos; // 缺货记录是否匹配其他库存
	private String lackOutTradeNo; // 缺货记录所属订单
	private boolean hasNext; // 是否有下一个待取件的记录

	/**
	 * 当执行部分/全部缺货操作时，系统会尝试将缺货记录匹配其他库存，如果匹配成功，返回true
	 * 
	 * @return
	 */
	public boolean getLacknessMatchNewPos() {
		return lacknessMatchNewPos;
	}

	public void setLacknessMatchNewPos(boolean lacknessMatchNewPos) {
		this.lacknessMatchNewPos = lacknessMatchNewPos;
	}

	public String getLackOutTradeNo() {
		return lackOutTradeNo;
	}

	public void setLackOutTradeNo(String lackOutTradeNo) {
		this.lackOutTradeNo = lackOutTradeNo;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
}