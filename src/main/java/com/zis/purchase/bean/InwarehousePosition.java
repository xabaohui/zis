package com.zis.purchase.bean;

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
 * InwarehousePosition entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="inwarehouse_position")
public class InwarehousePosition {

	// Fields

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="inwarehouse_id", nullable=false)
	private Integer inwarehouseId;
	
	@Column(name="position_label", nullable=false, length=32)
	private String positionLabel;
	
	@Column(name="capacity", nullable=false)
	private Integer capacity;
	
	@Column(name="current_amount", nullable=false)
	private Integer currentAmount;
	
	@Column(name="is_full", nullable=false)
	private boolean isFull;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_create", nullable=false, updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_modify", nullable=false)
	private Date gmtModify;
	
	@Version
	@Column(name="", nullable=false)
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

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}