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
 * OrderDetail entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "order_detail")
public class OrderDetail  {

	// Fields
	
	@Id
	@GeneratedValue
	@Column(name = "order_detail_id", nullable = false)
	private Integer orderDetailId;
	
	@Column(name = "item_id")
	private Integer itemId;
	
	@Column(name = "sku_id")
	private Integer skuId;
	
	@Column(name = "item_name")
	private String itemName;
	
	@Column(name = "item_count")
	private Integer itemCount;
	
	@Column(name = "item_price")
	private Double itemPrice;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "order_id")
	private Integer orderId;
	
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
	public OrderDetail() {
	}

	/** full constructor */
	public OrderDetail(Integer itemId, Integer skuId, String itemName, Integer itemCount, Double itemPrice,
			String status, Integer orderId, Date createTime, Date updateTime, Integer version) {
		this.itemId = itemId;
		this.skuId = skuId;
		this.itemName = itemName;
		this.itemCount = itemCount;
		this.itemPrice = itemPrice;
		this.status = status;
		this.orderId = orderId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	// Property accessors

	public Integer getOrderDetailId() {
		return this.orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemCount() {
		return this.itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Double getItemPrice() {
		return this.itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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