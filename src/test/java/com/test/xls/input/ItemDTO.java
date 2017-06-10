package com.test.xls.input;

public class ItemDTO {

	private Integer skuId;

	private String outNum;

	private String posLable;

	private Integer amount;

	private Integer operator = 0;

	private Integer repoId = 2;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getOutNum() {
		return outNum;
	}

	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}

	public String getPosLable() {
		return posLable;
	}

	public void setPosLable(String posLable) {
		this.posLable = posLable;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getOperator() {
		return operator;
	}

	public Integer getRepoId() {
		return repoId;
	}
}
