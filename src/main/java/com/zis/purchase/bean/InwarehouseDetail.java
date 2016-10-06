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
 * InwarehouseDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="inwarehouse_detail")
public class InwarehouseDetail {

	// Fields

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="inwarehouse_id", nullable=false)
	private Integer inwarehouseId;

	@Column(name="biz_type", nullable=false)
	private String bizType;
	
	@Column(name="position_label", nullable=false, length=32)
	private String positionLabel;
	
	@Column(name="book_id", nullable=false)
	private Integer bookId;
	
	@Column(name="amount", nullable=false)
	private Integer amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_create", nullable=false, updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_modify", nullable=false)
	private Date gmtModify;
	
	@Version
	@Column(name="version")
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