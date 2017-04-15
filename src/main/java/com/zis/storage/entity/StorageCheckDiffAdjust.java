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
 * StorageCheckDiffAdjust entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_check_diff_adjust")
public class StorageCheckDiffAdjust {

	@Id
	@GeneratedValue
	@Column(name = "adjust_id")
	private Integer adjustId;

	@Column(name = "check_id")
	private Integer checkId;

	@Column(name = "sku_id")
	private Integer skuId;

	@Column(name = "adjust_num")
	private Integer adjustNum;

	@Column(name = "operator")
	private Integer operator;

	@Column(name = "gmt_create", length = 32, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify", length = 32)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "memo", length = 512)
	private String memo;

	// Constructors

	/** default constructor */
	public StorageCheckDiffAdjust() {
	}

	/** minimal constructor */
	public StorageCheckDiffAdjust(Integer adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public StorageCheckDiffAdjust(Integer adjustId, Integer checkId, Integer skuId, Integer adjustNum,
			Integer operator, Date gmtCreate, Date gmtModify, Integer version, String memo) {
		this.adjustId = adjustId;
		this.checkId = checkId;
		this.skuId = skuId;
		this.adjustNum = adjustNum;
		this.operator = operator;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(Integer adjustId) {
		this.adjustId = adjustId;
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

	public Integer getAdjustNum() {
		return this.adjustNum;
	}

	public void setAdjustNum(Integer adjustNum) {
		this.adjustNum = adjustNum;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
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

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}