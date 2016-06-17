package com.zis.bookinfo.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Bookinfo entity. @author MyEclipse Persistence Tools
 */

public class Bookinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer outId;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private Date publishDate;
	private Float bookPrice;
	private String bookEdition;
	private Boolean isNewEdition;
	private String groupId;
	private String relateId;
	private Boolean repeatIsbn;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String bookStatus;

	// Constructors

	/** default constructor */
	public Bookinfo() {
	}

	/** minimal constructor */
	public Bookinfo(String isbn, String bookName, String bookAuthor,
			String bookPublisher, Float bookPrice) {
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.bookPrice = bookPrice;
	}

	/** full constructor */
	public Bookinfo(Integer outId, String isbn, String bookName,
			String bookAuthor, String bookPublisher, Date publishDate,
			Float bookPrice, String bookEdition, Boolean isNewEdition,
			String groupId, String relateId, Boolean repeatIsbn,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version,
			String bookStatus) {
		this.outId = outId;
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.publishDate = publishDate;
		this.bookPrice = bookPrice;
		this.bookEdition = bookEdition;
		this.isNewEdition = isNewEdition;
		this.groupId = groupId;
		this.relateId = relateId;
		this.repeatIsbn = repeatIsbn;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.bookStatus = bookStatus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOutId() {
		return this.outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return this.bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPublisher() {
		return this.bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Float getBookPrice() {
		return this.bookPrice;
	}

	public void setBookPrice(Float bookPrice) {
		this.bookPrice = bookPrice;
	}


	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public Boolean getIsNewEdition() {
		return this.isNewEdition;
	}

	public void setIsNewEdition(Boolean isNewEdition) {
		this.isNewEdition = isNewEdition;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getRelateId() {
		return this.relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}

	public Boolean getRepeatIsbn() {
		return this.repeatIsbn;
	}

	public void setRepeatIsbn(Boolean repeatIsbn) {
		this.repeatIsbn = repeatIsbn;
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
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getBookStatus() {
		return this.bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

}