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
 * StorageCheckPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "storage_check_plan")
public class StorageCheckPlan {

	@Id
	@GeneratedValue
	@Column(name = "plan_id")
	private Integer planId;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "status", length = 128)
	private String status;

	@Column(name = "pos_count")
	private Integer posCount;

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
	public StorageCheckPlan() {
	}

	/** full constructor */
	public StorageCheckPlan(Integer userId, String status, Integer posCount, Date gmtCreate, Date gmtModify,
			Integer version, String memo) {
		this.userId = userId;
		this.status = status;
		this.posCount = posCount;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.memo = memo;
	}

	// Property accessors

	public Integer getPlanId() {
		return this.planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPosCount() {
		return this.posCount;
	}

	public void setPosCount(Integer posCount) {
		this.posCount = posCount;
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