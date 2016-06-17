package com.zis.requirement.bean;

import java.sql.Timestamp;

/**
 * TempBookRequireImport entity. @author MyEclipse Persistence Tools
 */

public class BookRequireImportTask implements java.io.Serializable {

	// Fields

	private Integer id;
	private String college;
	private String operator;
	private String memo;
	private Integer totalCount;
	private String status;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public BookRequireImportTask() {
	}

	/** minimal constructor */
	public BookRequireImportTask(String operator, String memo, String status,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.operator = operator;
		this.memo = memo;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	/** full constructor */
	public BookRequireImportTask(String operator, String memo,
			Integer totalCount, String status, Timestamp gmtCreate,
			Timestamp gmtModify, Integer version) {
		this.operator = operator;
		this.memo = memo;
		this.totalCount = totalCount;
		this.status = status;
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

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
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

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}
}