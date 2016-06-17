package com.zis.bookinfo.bean;

import java.sql.Timestamp;

/**
 * YouluSales entity. @author MyEclipse Persistence Tools
 */

public class YouluSales implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer bookId;
	private Integer outId;
	private Integer stockBalance;
	private Double bookPrice;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public YouluSales() {
	}

	/** minimal constructor */
	public YouluSales(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}

	/** full constructor */
	public YouluSales(Integer bookId, Integer outId, Integer stockBalance,
			Double bookPrice, Timestamp gmtCreate, Timestamp gmtModify,
			Integer version) {
		this.bookId = bookId;
		this.outId = outId;
		this.stockBalance = stockBalance;
		this.bookPrice = bookPrice;
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

	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getOutId() {
		return this.outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public Integer getStockBalance() {
		return this.stockBalance;
	}

	public void setStockBalance(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}

	public Double getBookPrice() {
		return this.bookPrice;
	}

	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
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