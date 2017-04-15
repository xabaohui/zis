package com.zis.storage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 库存系统订单表
 * 
 * @author yz
 * 
 */
@Entity
@Table(name = "storage_order")
public class StorageOrder {

	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "out_trade_no", nullable = false)
	private String outTradeNo;
	
	@Column(name = "buyer_name", nullable = false)
	private String buyerName;
	
	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "order_detail", nullable = false)
	private String orderDetail;

	@Column(name = "repo_id", nullable = false)
	private Integer repoId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "order_type", nullable = false)
	private String orderType;

	@Column(name = "trade_status", nullable = false)
	private String tradeStatus;

	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public enum OrderType {
		SELF("self", "自有"), PARTNER("partner", "代发");

		private String value;
		private String display;

		private OrderType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}

	public enum TradeStatus {
		CREATED("created", "新创建"), PROCESSING("processing", "配货中"), SENT("sent", "已出库"), CANCEL("cancel", "已取消");

		private String value;
		private String display;

		private TradeStatus(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
	}
}
