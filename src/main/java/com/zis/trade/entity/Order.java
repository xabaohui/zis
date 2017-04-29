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
 * Order entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "order")
public class Order {

	// Fields

	@Id
	@GeneratedValue
	@Column(name = "order_id", nullable = false)
	private Integer orderId;

	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "repo_id")
	private Integer repoId;

	@Column(name = "receiver_name")
	private String receiverName;

	@Column(name = "receiver_phone")
	private String receiverPhone;

	@Column(name = "receiver_addr")
	private String receiverAddr;

	@Column(name = "order_group_number")
	private String orderGroupNumber;

	@Column(name = "post_company")
	private String postCompany;

	@Column(name = "post_number")
	private String postNumber;

	@Column(name = "order_money")
	private Double orderMoney;

	@Column(name = "order_type")
	private String orderType;

	@Column(name = "storage_status")
	private String storageStatus;

	@Column(name = "post_status")
	private String postStatus;

	@Column(name = "pay_status")
	private String payStatus;

	@Column(name = "pay_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date payTime;

	@Column(name = "arrange_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date arrangeTime;

	@Column(name = "post_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date postTime;

	@Column(name = "saler_remark")
	private String salerRemark;

	@Column(name = "buyer_message")
	private String buyerMessage;

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
	public Order() {
	}

	/** full constructor */
	public Order(Integer companyId, Integer repoId, String receiverName, String receiverPhone, String receiverAddr,
			String orderGroupNumber, String postCompany, String postNumber, Double orderMoney, String orderType,
			String storageStatus, String postStatus, String payStatus, Date payTime, Date arrangeTime, Date postTime,
			String salerRemark, String buyerMessage, Date createTime, Date updateTime, Integer version) {
		this.companyId = companyId;
		this.repoId = repoId;
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverAddr = receiverAddr;
		this.orderGroupNumber = orderGroupNumber;
		this.postCompany = postCompany;
		this.postNumber = postNumber;
		this.orderMoney = orderMoney;
		this.orderType = orderType;
		this.storageStatus = storageStatus;
		this.postStatus = postStatus;
		this.payStatus = payStatus;
		this.payTime = payTime;
		this.arrangeTime = arrangeTime;
		this.postTime = postTime;
		this.salerRemark = salerRemark;
		this.buyerMessage = buyerMessage;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	// Property accessors

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRepoId() {
		return this.repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getReceiverName() {
		return this.receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return this.receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getReceiverAddr() {
		return this.receiverAddr;
	}

	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}

	public String getOrderGroupNumber() {
		return this.orderGroupNumber;
	}

	public void setOrderGroupNumber(String orderGroupNumber) {
		this.orderGroupNumber = orderGroupNumber;
	}

	public String getPostCompany() {
		return this.postCompany;
	}

	public void setPostCompany(String postCompany) {
		this.postCompany = postCompany;
	}

	public String getPostNumber() {
		return this.postNumber;
	}

	public void setPostNumber(String postNumber) {
		this.postNumber = postNumber;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStorageStatus() {
		return this.storageStatus;
	}

	public void setStorageStatus(String storageStatus) {
		this.storageStatus = storageStatus;
	}

	public String getPostStatus() {
		return this.postStatus;
	}

	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}

	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Date getPayTime() {
		return this.payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getArrangeTime() {
		return this.arrangeTime;
	}

	public void setArrangeTime(Date arrangeTime) {
		this.arrangeTime = arrangeTime;
	}

	public Date getPostTime() {
		return this.postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getSalerRemark() {
		return this.salerRemark;
	}

	public void setSalerRemark(String salerRemark) {
		this.salerRemark = salerRemark;
	}

	public String getBuyerMessage() {
		return this.buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
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