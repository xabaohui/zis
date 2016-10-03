package com.zis.bookinfo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BookInfoDTO {

	private Integer id;
	private Integer outId;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date publishDate;
	private Double bookPrice;
	private String bookEdition;
	private Boolean isNewEdition;
	private Boolean repeatIsbn;
	private String bookStatus;
	private String operateType;
	// 图书详情
	private String imageUrl;
	private String taobaoTitle;
	private Integer taobaoCatagoryId;
	private String summary;
	private String catalog;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOutId() {
		return outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Double getBookPrice() {
		return bookPrice;
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
		return isNewEdition;
	}

	public void setIsNewEdition(Boolean isNewEdition) {
		this.isNewEdition = isNewEdition;
	}

	public Boolean getRepeatIsbn() {
		return repeatIsbn;
	}

	public void setRepeatIsbn(Boolean repeatIsbn) {
		this.repeatIsbn = repeatIsbn;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTaobaoTitle() {
		return taobaoTitle;
	}

	public void setTaobaoTitle(String taobaoTitle) {
		this.taobaoTitle = taobaoTitle;
	}

	public Integer getTaobaoCatagoryId() {
		return taobaoCatagoryId;
	}

	public void setTaobaoCatagoryId(Integer taobaoCatagoryId) {
		this.taobaoCatagoryId = taobaoCatagoryId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
