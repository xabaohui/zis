package com.zis.purchase.dto;

import com.zis.purchase.bean.TempImportTask;

/**
 * tempImportTask视图，展示层用
 * @author yz
 *
 */
public class TempImportTaskView extends TempImportTask {

	private String bizTypeDisplay;
	private String statusDisplay;

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
}
