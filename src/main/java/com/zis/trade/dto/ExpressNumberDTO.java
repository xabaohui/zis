package com.zis.trade.dto;

/**
 * 快递单号DTO，用于快递单号回填
 * @author yz
 *
 */
public class ExpressNumberDTO {

	private Integer orderId;
	private String expressNumber;
	private String expressCompany;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
}
