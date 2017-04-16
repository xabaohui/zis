package com.zis.storage.dto;

import com.zis.storage.entity.StorageIoBatch;

public class StorageIoBatchDTO extends StorageIoBatch {

	private String zhCnStatus;

	private String realName;
	
	private String zhCnType;
	
	public String getZhCnType() {
		return zhCnType;
	}

	public void setZhCnType(String zhCnType) {
		this.zhCnType = zhCnType;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getZhCnStatus() {
		return zhCnStatus;
	}

	public void setZhCnStatus(String zhCnStatus) {
		this.zhCnStatus = zhCnStatus;
	}

}
