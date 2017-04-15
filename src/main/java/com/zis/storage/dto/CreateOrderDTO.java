package com.zis.storage.dto;

import java.util.List;

import com.zis.storage.entity.StorageOrder.OrderType;

/**
 * 创建订单请求参数
 * 
 * @author yz
 * 
 */
public class CreateOrderDTO {

	private Integer repoId; // 仓库Id
	private Integer shopId; // 店铺Id
	private String outTradeNo; // 订单号
	private OrderType orderType; // 订单类型
	private List<CreateOrderDetail> detailList; // 订单详情

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}
	
	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List<CreateOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CreateOrderDetail> detailList) {
		this.detailList = detailList;
	}

	public static class CreateOrderDetail {
		private Integer skuId;
		private Integer amount;

		public Integer getSkuId() {
			return skuId;
		}

		public void setSkuId(Integer skuId) {
			this.skuId = skuId;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}
	}
}
