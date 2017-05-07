package com.zis.trade.dto;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class OrderInfoDTO {
	
	private Integer shopId; // 店铺Id
	private String outOrderNumber; // 店铺订单号
	private String receiverName;
	private String receiverPhone;
	private String receiverAddr;
	private Double orderMoney; // 订单金额
	private String orderType; // 订单类型
	private String salerRemark; // 卖家备注
	private String buyerMessage; // 买家留言
	private Integer operator; // 操作员Id
	private List<SkuInfo> skus;
	
	public List<SkuInfo> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuInfo> skus) {
		this.skus = skus;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getOutOrderNumber() {
		return outOrderNumber;
	}

	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		if(StringUtils.isNotBlank(receiverPhone)){
			this.receiverPhone = receiverPhone.replace("[']", "");
		}else{
			this.receiverPhone = receiverPhone;
		}
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getSalerRemark() {
		return salerRemark;
	}

	public void setSalerRemark(String salerRemark) {
		this.salerRemark = salerRemark;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public static class SkuInfo {
		
		private String outOrderNumber;//主订单号
		private Integer itemId; // 平台商品Id
		private String itemOutNum; // 商家编码
		private Integer skuId; // 系统skuId
		private String itemName; // 商品名称
		private Integer itemCount; // 数量
		private Double itemPrice; // 单价

		
		public String getOutOrderNumber() {
			return outOrderNumber;
		}

		public void setOutOrderNumber(String outOrderNumber) {
			this.outOrderNumber = outOrderNumber;
		}

		public Integer getItemId() {
			return itemId;
		}

		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}

		public String getItemOutNum() {
			return itemOutNum;
		}

		public void setItemOutNum(String itemOutNum) {
			this.itemOutNum = itemOutNum;
		}

		public Integer getSkuId() {
			return skuId;
		}

		public void setSkuId(Integer skuId) {
			this.skuId = skuId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public Integer getItemCount() {
			return itemCount;
		}

		public void setItemCount(Integer itemCount) {
			this.itemCount = itemCount;
		}

		public Double getItemPrice() {
			return itemPrice;
		}

		public void setItemPrice(Double itemPrice) {
			this.itemPrice = itemPrice;
		}

	}
}
