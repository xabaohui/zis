package com.zis.storage.dto;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class TakeGood {

	private String posLabel;

	private Integer amount;

	public String getPosLabel() {
		return posLabel;
	}

	public void setPosLabel(String posLabel) {
		this.posLabel = posLabel;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}