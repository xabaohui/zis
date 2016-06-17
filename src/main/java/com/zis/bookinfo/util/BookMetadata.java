package com.zis.bookinfo.util;

import java.util.Date;

/**
 * 
 */

/**
 * ͼ����Ϣ
 * 
 * @author lvbin 2015-7-23
 */
public class BookMetadata {

	private String name;
	private String publisher;
	private String publishDate;
	private String edition;
	private String author;
	private Double price; // 标价
	private String isbnCode;
	private String bookIntro;
	private String summary;
	private String catelog;
	private Integer stock;
	private Double salesPrice; // 有路网售价
	private Integer sales;
	private String outId;
	private Date captureDate;

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

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
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

	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatelog() {
		return catelog;
	}

	public void setCatelog(String catelog) {
		this.catelog = catelog;
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

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	@Override
	public String toString() {
		return "BookMetadata [name=" + name + ", publisher=" + publisher
				+ ", publishDate=" + publishDate + ", edition=" + edition
				+ ", author=" + author + ", price=" + price + ", isbnCode="
				+ isbnCode + ", bookIntro=" + bookIntro + ", summary="
				+ summary + ", catelog=" + catelog + ", stock=" + stock
				+ ", salesPrice=" + salesPrice + ", sales=" + sales
				+ ", outId=" + outId + "]";
	}
}