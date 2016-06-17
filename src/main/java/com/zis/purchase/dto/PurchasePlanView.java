package com.zis.purchase.dto;

import java.util.Date;

import com.zis.purchase.bean.PurchasePlan;

public class PurchasePlanView extends PurchasePlan {

	private static final long serialVersionUID = -1040252572666322485L;
	private Date publishDate;
	private Float bookPrice;
	private boolean isNewEdition; // 是否是最新版
	private boolean repeatIsbn;
	private boolean manualDecisionFlag; // 是否是人工定量
	private Integer stillRequireAmount; // 还需采购的数量

	public boolean getIsNewEdition() {
		return isNewEdition;
	}

	public void setNewEdition(boolean isNewEdition) {
		this.isNewEdition = isNewEdition;
	}

	public boolean getRepeatIsbn() {
		return repeatIsbn;
	}

	public void setRepeatIsbn(boolean repeatIsbn) {
		this.repeatIsbn = repeatIsbn;
	}

	public boolean isManualDecisionFlag() {
		return manualDecisionFlag;
	}

	public void setManualDecisionFlag(boolean manualDecisionFlag) {
		this.manualDecisionFlag = manualDecisionFlag;
	}

	public Integer getStillRequireAmount() {
		return stillRequireAmount;
	}

	public void setStillRequireAmount(Integer stillRequireAmount) {
		this.stillRequireAmount = stillRequireAmount;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Float bookPrice) {
		this.bookPrice = bookPrice;
	}
}
