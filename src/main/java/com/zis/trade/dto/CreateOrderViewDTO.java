package com.zis.trade.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单模块创建订单请求参数
 * 
 * @author yz
 * 
 */
public class CreateOrderViewDTO {

	private boolean success;
	private String failReason;
	private String manualOrderType;// 手动下单类型 myself or taobao
//	@NotEmpty(message = "订单类型不能为为空")
	private String orderType; // 订单类型
	@NotNull(message = "店铺Id不能为空")
	private Integer shopId; // 店铺Id
	private Double discount;// 折扣率
	@NotEmpty(message = "店铺订单号不能为为空")
	private String outOrderNumber; // 店铺订单号
	@NotEmpty(message = "收件人不能为为空")
	private String receiverName;
	@NotEmpty(message = "收件人电话不能为为空")
	@Pattern(regexp = "^((13[0-9])|(17[0-9])|(14[0-9])|(15[0-9])|(18[0-9]))\\d{8}$", message = "手机号码格式不符")
	private String receiverPhone;
	@NotEmpty(message = "收件人地址不能为为空")
	private String receiverAddr;
	@NotNull(message = "邮费不能为空")
	private Double postage;// 邮费
	@NotNull(message = "订单金额不能为空")
	private Double orderMoney; // 订单金额
	private String salerRemark; // 卖家备注
	private String buyerMessage; // 买家留言
	private List<SkuViewInfo> skus;
	private SkuInfoViewDTO skuOld;// 回调数据使用修改和新增
	private Integer skuNumber;// subOrder序号

	public List<SkuViewInfo> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuViewInfo> skus) {
		this.skus = skus;
	}

	public SkuInfoViewDTO getSkuOld() {
		return skuOld;
	}

	public void setSkuOld(SkuInfoViewDTO skuOld) {
		this.skuOld = skuOld;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getManualOrderType() {
		return manualOrderType;
	}

	public void setManualOrderType(String manualOrderType) {
		this.manualOrderType = manualOrderType;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Integer getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(Integer skuNumber) {
		this.skuNumber = skuNumber;
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
		this.receiverPhone = receiverPhone;
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

	public static class SkuViewInfo implements Serializable {

		private static final long serialVersionUID = 968987930806022057L;
		
		private Integer skuId; // 系统skuId
		private String isbn;
		private String itemName; // 商品名称
		private Integer itemCount; // 数量
		private Double itemPrice; // 单价 计算后
		private Integer resultInt;// 返回的第几行记录

		public Integer getResultInt() {
			return resultInt;
		}

		public void setResultInt(Integer resultInt) {
			this.resultInt = resultInt;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
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
