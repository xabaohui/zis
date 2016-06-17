package com.zis.purchase.bean;

import java.sql.Timestamp;

/**
 * InwarehouseDetail entity. @author MyEclipse Persistence Tools
 */

public class InwarehouseDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer inwarehouseId;
	private String bizType;
	private String positionLabel;
	private Integer bookId;
	private Integer amount;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public InwarehouseDetail() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInwarehouseId() {
		return this.inwarehouseId;
	}

	public void setInwarehouseId(Integer inwarehouseId) {
		this.inwarehouseId = inwarehouseId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPositionLabel() {
		return this.positionLabel;
	}

	public void setPositionLabel(String positionLabel) {
		this.positionLabel = positionLabel;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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