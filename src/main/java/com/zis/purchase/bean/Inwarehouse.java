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
 * Inwarehouse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="inwarehouse")
public class Inwarehouse {

	// Fields

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="biz_type", nullable=false)
	private String bizType;
	
	@Column(name="inwarehouse_operator", nullable=false)
	private String inwarehouseOperator;
	
	@Column(name="source")
	private String source;
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="amount", nullable=false)
	private Integer amount;
	
	@Column(name="memo", length=128)
	private String memo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_create", updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_modify")
	private Date gmtModify;
	
	@Version
	@Column(name="version")
	private Integer version;

	// Constructors

	/** default constructor */
	public Inwarehouse() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInwarehouseOperator() {
		return this.inwarehouseOperator;
	}

	public void setInwarehouseOperator(String inwarehouseOperator) {
		this.inwarehouseOperator = inwarehouseOperator;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}