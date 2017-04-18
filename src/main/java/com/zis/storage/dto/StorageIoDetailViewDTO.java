package com.zis.storage.dto;

import com.zis.storage.entity.StorageIoDetail;

public class StorageIoDetailViewDTO extends StorageIoDetail {

	private String displayType; // 类型（用于展示）
	private String displayAmount; // 变动数量（用于展示）
	private String operatorName; // 操作员姓名
	
	/**
	 * 初始化数据
	 */
	public void init() {
		final String type = getIoDetailType();
		if(type != null) {
			displayType = IoType.getIoType(type).getDisplay();
		}
		if(IoType.OUT.getValue().equals(type)) {
			displayAmount = "-" + getAmount();
		} else {
			displayAmount = "+" + getAmount();
		}
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDisplayAmount() {
		return displayAmount;
	}

	public void setDisplayAmount(String displayAmount) {
		this.displayAmount = displayAmount;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
}
