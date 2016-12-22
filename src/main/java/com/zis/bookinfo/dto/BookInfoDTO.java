package com.zis.bookinfo.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.zis.bookinfo.bean.Bookinfo;
import com.zis.bookinfo.bean.BookinfoDetail;

public class BookInfoDTO extends Bookinfo{

	private Integer id;
	@Min(value = 1, message = "外部ID必须大于0")
	private Integer outId;
	@NotBlank(message = "isbn不能为空")
	@Length(max = 30, message = "isbn不能超过30个字符")
	private String isbn;
	@NotBlank(message = "书名不能为空")
	@Length(max = 128, message = "书名不能超过128个字符")
	private String bookName;
	@NotBlank(message = "作者不能为空")
	@Length(max = 50, message = "作者不能超过50个字符")
	private String bookAuthor;
	@NotBlank(message = "出版社不能为空")
	@Length(max = 30, message = "出版社不能超过30个字符")
	private String bookPublisher;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishDate;
	@NotNull(message = "价格不能为空")
	private Double bookPrice;
	@NotBlank(message = "版次不能为空")
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
	
	private String urlType;

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

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
}
