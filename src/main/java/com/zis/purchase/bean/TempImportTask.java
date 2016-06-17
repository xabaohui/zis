package com.zis.purchase.bean;

import java.sql.Timestamp;

/**
 * TempImportTask entity. @author MyEclipse Persistence Tools
 */

public class TempImportTask implements java.io.Serializable {

	// Fields

	private Integer id;
	private String bizType;
	private String memo;
	private Integer status;
	private Integer totalCount;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public TempImportTask() {
	}

	/** minimal constructor */
	public TempImportTask(String bizType, Integer status, Timestamp gmtCreate,
			Timestamp gmtModify, Integer version) {
		this.bizType = bizType;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	/** full constructor */
	public TempImportTask(String bizType, String memo, Integer status,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.bizType = bizType;
		this.memo = memo;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBizType() {
		return this.bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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