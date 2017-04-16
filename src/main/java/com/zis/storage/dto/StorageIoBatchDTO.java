package com.zis.storage.dto;

import com.zis.storage.entity.StorageIoBatch;

public class StorageIoBatchDTO extends StorageIoBatch {

	private String zhCnStatus;

	public String getZhCnStatus() {
		return zhCnStatus;
	}

	public void setZhCnStatus(String zhCnStatus) {
		this.zhCnStatus = zhCnStatus;
	}

}
