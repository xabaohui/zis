package com.zis.bookinfo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * Bookinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bookinfo")
@DataTransferObject
public class Bookinfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -5701295820763705669L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="outId")
	private Integer outId;
	
	@Column(name="ISBN")
	private String isbn;
	
	@Column(name="bookName")
	private String bookName;
	
	@Column(name="bookAuthor")
	private String bookAuthor;
	
	@Column(name="bookPublisher")
	private String bookPublisher;
	
	@Column(name="publishDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishDate;
	
	@Column(name="bookPrice", precision=10, scale=2)
	private Double bookPrice;
	
	@Column(name="bookEdition")
	private String bookEdition;
	
	@Column(name="isNewEdition")
	private Boolean isNewEdition;
	
	@Column(name="groupId")
	private String groupId;
	
	@Column(name="relateId")
	private String relateId;
	
	@Column(name="repeatISBN")
	private Boolean repeatIsbn;
	
	@Column(name="GMT_CREATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;
	
	@Column(name="GMT_MODIFY")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;
	
	@Version
	@Column(name="Version")
	private Integer version;
	
	@Column(name="bookStatus")
	private String bookStatus;

	// Constructors

	/** default constructor */
	public Bookinfo() {
	}

	/** minimal constructor */
	public Bookinfo(String isbn, String bookName, String bookAuthor,
			String bookPublisher, Double bookPrice) {
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.bookPrice = bookPrice;
	}

	/** full constructor */
	public Bookinfo(Integer outId, String isbn, String bookName,
			String bookAuthor, String bookPublisher, Date publishDate,
			Double bookPrice, String bookEdition, Boolean isNewEdition,
			String groupId, String relateId, Boolean repeatIsbn,
			Date gmtCreate, Date gmtModify, Integer version,
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

	public Double getBookPrice() {
		return this.bookPrice;
	}

	public void setBookPrice(Double bookPrice) {
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