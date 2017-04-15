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
 * StorageCheckDiff entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_check_diff")
public class StorageCheckDiff {

	@Id
	@GeneratedValue
	@Column(name = "check_diff_id", nullable = false)
	private Integer checkDiffId;

	@Column(name = "check_id")
	private Integer checkId;

	@Column(name = "position_id", nullable = false)
	private Integer positionId;

	@Column(name = "sku_id")
	private Integer skuId;

	@Column(name = "realamount")
	private Integer realAmount;

	@Column(name = "stockamount")
	private Integer stockAmount;

	@Column(name = "check_diff_status")
	private String checkDiffStatus;

	@Column(name = "gmt_create", length = 32, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify", length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageCheckDiff() {
	}

	/** full constructor */
	public StorageCheckDiff(Integer checkId, Integer positionId, Integer skuId, Integer realAmount,
			Integer stockAmount, String checkDiffStatus, Date gmtCreate, Date gmtModify, Integer version) {
		this.checkId = checkId;
		this.positionId = positionId;
		this.skuId = skuId;
		this.realAmount = realAmount;
		this.stockAmount = stockAmount;
		this.checkDiffStatus = checkDiffStatus;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getCheckDiffId() {
		return this.checkDiffId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getCheckDiffStatus() {
		return checkDiffStatus;
	}

	public void setCheckDiffStatus(String checkDiffStatus) {
		this.checkDiffStatus = checkDiffStatus;
	}

	public void setCheckDiffId(Integer checkDiffId) {
		this.checkDiffId = checkDiffId;
	}

	public Integer getCheckId() {
		return this.checkId;
	}

	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getRealAmount() {
		return this.realAmount;
	}

	public void setRealAmount(Integer realAmount) {
		this.realAmount = realAmount;
	}

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
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