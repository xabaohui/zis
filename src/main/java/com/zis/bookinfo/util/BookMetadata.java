package com.zis.bookinfo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * 图书元数据，描述图书基本属性的所有字段
 * 
 * @author lvbin 2015-7-23
 */
@DataTransferObject
public class BookMetadata {

	private String name;
	private String publisher;
	private Date publishDate;
	private String publishDateStr;
	private String edition;
	private String author;
	private Double price; // 定价
	private String isbnCode;
	private String summary;
	private String catalog;
	private String imageUrl;
	private Integer stock;
	private Double salesPrice; // 售价
	private Integer sales; // 销量
	private String outId; // 商品Id
	private String source; // 商品来源

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		this.publishDateStr = new SimpleDateFormat("yyyy-MM-dd").format(publishDate);
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(String isbnCode) {
		this.isbnCode = isbnCode;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPublishDateStr() {
		return publishDateStr;
	}

	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}

	@Override
	public String toString() {
		return "BookMetadata [name=" + name + ", publisher=" + publisher
				+ ", publishDate=" + publishDateStr + ", edition=" + edition
				+ ", author=" + author + ", price=" + price + ", isbnCode="
				+ isbnCode + ", imageUrl=" + imageUrl + ", stock=" + stock
				+ ", salesPrice=" + salesPrice + ", sales=" + sales
				+ ", outId=" + outId + ", source=" + source + "]";
	}
}