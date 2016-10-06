package com.zis.purchase.bean;

import java.sql.Timestamp;
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
 * Storesales entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="storesales")
public class Storesales {

	// Fields

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "captureDate", nullable=false)
	private Date captureDate;

	@Column(name = "bookId", nullable=false)
	private Integer bookId;
	
	@Column(name = "sales", nullable=false)
	private Integer sales;
	
	@Column(name = "outId", nullable=false)
	private String outId;
	
	@Column(name = "isbn", nullable=false)
	private String isbn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GMT_CREATE", nullable=false, updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GMT_MODIFY", nullable=false)
	private Date gmtModify;
	
	@Version
	@Column(name = "version", nullable=false)
	private Integer version;

	// Constructors

	/** default constructor */
	public Storesales() {
	}

	/** minimal constructor */
	public Storesales(String outId, String isbn) {
		this.outId = outId;
		this.isbn = isbn;
	}

	/** full constructor */
	public Storesales(Date captureDate, Integer bookId, Integer sales,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String outId, String isbn) {
		this.captureDate = captureDate;
		this.bookId = bookId;
		this.sales = sales;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.outId = outId;
		this.isbn = isbn;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCaptureDate() {
		return this.captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getSales() {
		return this.sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
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

	public String getOutId() {
		return this.outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}