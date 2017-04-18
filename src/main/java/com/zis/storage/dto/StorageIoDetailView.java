package com.zis.storage.dto;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.storage.entity.StorageIoDetail;

/**
 * 库存变动明细表(页面展示用)
 */
public class StorageIoDetailView extends StorageIoDetail {
	
	private Bookinfo book;
	private String zhCnStatus;
	private String zhCnType;
	private String realName;

	public Bookinfo getBook() {
		return book;
	}

	public void setBook(Bookinfo book) {
		this.book = book;
	}

	public String getZhCnStatus() {
		return zhCnStatus;
	}

	public void setZhCnStatus(String zhCnStatus) {
		this.zhCnStatus = zhCnStatus;
	}

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
}