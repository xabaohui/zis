package com.zis.bookinfo.bean;

import java.sql.Timestamp;

/**
 * ShopItemInfo entity. @author MyEclipse Persistence Tools
 */

public class ShopItemInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer bookId;
	private String isbn;
	private String shopStatus;
	private String shopName;
	private Timestamp gmtCreate;
	private Timestamp gmtModified;
	private Integer version;

	// Constructors

	/** default constructor */
	public ShopItemInfo() {
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

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getShopStatus() {
		return this.shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Timestamp gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}