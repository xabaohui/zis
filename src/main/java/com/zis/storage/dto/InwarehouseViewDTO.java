package com.zis.storage.dto;

import com.zis.storage.entity.StorageIoBatch;

public class InwarehouseViewDTO extends StorageIoBatch{
	
	private String bizTypeDisplay;
	private String statusDisplay;
	private String[] positionLabel;
	private Integer[] capacity;

	public String getBizTypeDisplay() {
		return bizTypeDisplay;
	}

	public void setBizTypeDisplay(String bizTypeDisplay) {
		this.bizTypeDisplay = bizTypeDisplay;
	}

	public String getStatusDisplay() {
		return statusDisplay;
	}

	public void setStatusDisplay(String statusDisplay) {
		this.statusDisplay = statusDisplay;
	}

	public String[] getPositionLabel() {
		return positionLabel;
	}

	public void setPositionLabel(String[] positionLabel) {
		this.positionLabel = positionLabel;
	}

	public Integer[] getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer[] capacity) {
		this.capacity = capacity;
	}
}
