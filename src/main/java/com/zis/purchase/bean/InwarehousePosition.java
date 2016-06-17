package com.zis.purchase.bean;

import java.sql.Timestamp;

/**
 * InwarehousePosition entity. @author MyEclipse Persistence Tools
 */

public class InwarehousePosition implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer inwarehouseId;
	private String positionLabel;
	private Integer capacity;
	private Integer currentAmount;
	private boolean isFull;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public InwarehousePosition() {
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

	public String getPositionLabel() {
		return this.positionLabel;
	}

	public void setPositionLabel(String positionLabel) {
		this.positionLabel = positionLabel;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(Integer currentAmount) {
		this.currentAmount = currentAmount;
	}

	public boolean getIsFull() {
		return this.isFull;
	}

	public void setIsFull(boolean isFull) {
		this.isFull = isFull;
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