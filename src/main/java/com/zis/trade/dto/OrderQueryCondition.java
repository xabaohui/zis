package com.zis.trade.dto;

import org.apache.commons.lang3.StringUtils;

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
		if(StringUtils.isNotBlank(outOrderNumber)){
			return outOrderNumber.trim();
		}else{
			return outOrderNumber;
		}
	}

	/**
	 * 外部订单号
	 * @param outOrderNumber
	 */
	public void setOutOrderNumber(String outOrderNumber) {
		if(StringUtils.isNotBlank(outOrderNumber)){
			this.outOrderNumber = outOrderNumber.trim();
		}else{
			this.outOrderNumber = outOrderNumber;
		}
	}

	public String getReceiverName() {
		if(StringUtils.isNotBlank(receiverName)){
			return receiverName.trim();
		}else{
			return receiverName;
		}
	}

	/**
	 * 收件人
	 * @param receiverName
	 */
	public void setReceiverName(String receiverName) {
		if(StringUtils.isNotBlank(receiverName)){
			this.receiverName = receiverName.trim();
		}else{
			this.receiverName = receiverName;
		}
	}

	public String getReceiverPhone() {
		if(StringUtils.isNotBlank(receiverPhone)){
			return receiverPhone.trim();
		}else{
			return receiverPhone;
		}
	}

	/**
	 * 收件人电话
	 * @param receiverPhone
	 */
	public void setReceiverPhone(String receiverPhone) {
		if(StringUtils.isNotBlank(receiverPhone)){
			this.receiverPhone = receiverPhone.trim();
		}else{
			this.receiverPhone = receiverPhone;
		}
	}

	public String getExpressNumber() {
		if(StringUtils.isNotBlank(expressNumber)){
			return expressNumber.trim();
		}else{
			return expressNumber;
		}
	}

	/**
	 * 快递单号
	 * @param expressNumber
	 */
	public void setExpressNumber(String expressNumber) {
		if(StringUtils.isNotBlank(expressNumber)){
			this.expressNumber = expressNumber.trim();
		}else{
			this.expressNumber = expressNumber;
		}
	}
}
