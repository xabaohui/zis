package com.zis.trade.dto;

/**
 * 订单页查询条件
 * 
 * @author yz
 * 
 */
public class OrderQueryCondition {

	private Integer orderId;
	private String outOrderNumber;
	private String receiverName;
	private String receiverPhone;
	private String expressNumber;

	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * 系统订单Id
	 * @param orderId
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOutOrderNumber() {
		return outOrderNumber;
	}

	/**
	 * 外部订单号
	 * @param outOrderNumber
	 */
	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}

	public String getReceiverName() {
		return receiverName;
	}

	/**
	 * 收件人
	 * @param receiverName
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	/**
	 * 收件人电话
	 * @param receiverPhone
	 */
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	/**
	 * 快递单号
	 * @param expressNumber
	 */
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
}
