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
	private Integer outOrderId; // 外部订单Id（相当于订单模块Id）
//	private String outTradeNo; // 订单号
	private String buyerName; // 收件人姓名
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

	public void setOutOrderId(Integer outOrderId) {
		this.outOrderId = outOrderId;
	}
	
	public Integer getOutOrderId() {
		return outOrderId;
	}
	
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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
