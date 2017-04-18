package com.zis.storage.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 创建订单请求参数(页面传入)
 * 
 * @author yz
 * 
 */
public class CreateOrderInputDTO {

	@NotNull(message = "店铺不能为空")
	private Integer shopId; // 店铺Id
	@NotEmpty(message = "订单号不能为空")
	private String outTradeNo; // 订单号
	@NotEmpty(message = "收件人姓名不能为空")
	private String buyerName; // 收件人姓名
	@NotEmpty(message = "订单详情不能为空")
	private List<CreateOrderDetailInput> dList; // 订单详情

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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public List<CreateOrderDetailInput> getdList() {
		return dList;
	}

	public void setdList(List<CreateOrderDetailInput> dList) {
		this.dList = dList;
	}

	public static class CreateOrderDetailInput {

		@NotNull(message = "图书Id不能为空")
		private Integer skuId;
		@NotNull(message = "图书数量不能为空")
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
