package com.zis.trade.entity;

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
 * OrderOuter entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "order_outer")
public class OrderOuter {

	// Fields

	@Id
	@GeneratedValue
	@Column(name = "out_order_id", nullable = false)
	private Integer outOrderId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "shop_name")
	private String shopName;

	@Column(name = "p_name")
	private String pName;

	@Column(name = "order_data")
	private String OrderData;

	@Column(name = "out_order_number")
	private String outOrderNumber;

	@Column(name = "order_group_number")
	private String orderGroupNumber;

	@Column(name = "create_time", updatable = false, length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time", length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	// Constructors

	/** default constructor */
	public OrderOuter() {
	}

	// Property accessors

	public String getOrderData() {
		return OrderData;
	}

	public void setOrderData(String orderData) {
		OrderData = orderData;
	}

	public Integer getOutOrderId() {
		return this.outOrderId;
	}

	public void setOutOrderId(Integer outOrderId) {
		this.outOrderId = outOrderId;
	}

	public Integer getShopId() {
		return this.shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getOutOrderNumber() {
		return this.outOrderNumber;
	}

	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}

	public String getOrderGroupNumber() {
		return this.orderGroupNumber;
	}

	public void setOrderGroupNumber(String orderGroupNumber) {
		this.orderGroupNumber = orderGroupNumber;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}