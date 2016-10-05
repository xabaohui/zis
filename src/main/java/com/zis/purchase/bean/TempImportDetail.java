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
 * TempImportDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="temp_import_detail")
public class TempImportDetail {

	// Fields
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "isbn")
	private String isbn;
	
	@Column(name = "orig_isbn", nullable=false)
	private String origIsbn;
	
	@Column(name = "data", nullable=false)
	private String data;
	
	@Column(name = "book_id", nullable=false)
	private Integer bookId;
	
	@Column(name = "task_id", nullable=false)
	private Integer taskId;
	
	@Column(name = "status", nullable=false)
	private String status;
	
	@Column(name = "additional_info")
	private String additionalInfo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_create", nullable=false, updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_modify", nullable=false)
	private Date gmtModify;
	
	@Version
	@Column(name = "version", nullable=false)
	private Integer version;

	// Constructors

	/** default constructor */
	public TempImportDetail() {
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getOrigIsbn() {
		return this.origIsbn;
	}

	public void setOrigIsbn(String origIsbn) {
		this.origIsbn = origIsbn;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}