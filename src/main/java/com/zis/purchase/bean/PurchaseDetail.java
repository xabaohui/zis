package com.zis.purchase.bean;

import java.sql.Timestamp;

/**
 * Purchaseorder entity. @author MyEclipse Persistence Tools
 */

public class PurchaseDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer bookId;
	private Integer purchasedAmount;
	private Integer inwarehouseAmount;// 已入库数量
	private Integer batchId;
	private String memo;
	private String status;
	private String operator;
	private String position;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public PurchaseDetail() {
	}

	/** full constructor */
	public PurchaseDetail(Integer bookId, Integer purchasedAmount,
			Integer batchId, String memo, String status, String operator,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.bookId = bookId;
		this.purchasedAmount = purchasedAmount;
		this.batchId = batchId;
		this.memo = memo;
		this.status = status;
		this.operator = operator;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getPurchasedAmount() {
		return this.purchasedAmount;
	}

	public void setPurchasedAmount(Integer purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}

	public Integer getInwarehouseAmount() {
		return inwarehouseAmount;
	}

	public void setInwarehouseAmount(Integer inwarehouseAmount) {
		this.inwarehouseAmount = inwarehouseAmount;
	}

	public Integer getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Timestamp gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}