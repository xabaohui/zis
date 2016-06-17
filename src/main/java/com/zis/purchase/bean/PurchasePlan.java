package com.zis.purchase.bean;

import java.sql.Timestamp;

/**
 * Requirementamount entity. @author MyEclipse Persistence Tools
 */

public class PurchasePlan implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer bookId;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private String bookEdition;
	private Integer requireAmount; // 需求量
	private Integer manualDecision; // 人工定义需求量
	private Integer stockAmount; // 库存量
	private Integer purchasedAmount; // 在途库存量（已采购未入库）
	private String status;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public PurchasePlan() {
	}

	/** full constructor */
	public PurchasePlan(Integer bookId, Integer requireAmount,
			Integer purchasedAmount, String bookName, String bookAuthor,
			String bookPublisher, String isbn, String bookEdition,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.bookId = bookId;
		this.requireAmount = requireAmount;
		this.purchasedAmount = purchasedAmount;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.isbn = isbn;
		this.bookEdition = bookEdition;
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

	public Integer getRequireAmount() {
		return this.requireAmount;
	}

	public void setRequireAmount(Integer requireAmount) {
		this.requireAmount = requireAmount;
	}

	public Integer getPurchasedAmount() {
		return this.purchasedAmount;
	}

	public void setPurchasedAmount(Integer purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
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

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookEdition() {
		return this.bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
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

	public Integer getStockAmount() {
		return stockAmount;
	}

	public void setStockAmount(Integer stockAmount) {
		this.stockAmount = stockAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getManualDecision() {
		return manualDecision;
	}

	public void setManualDecision(Integer manualDecision) {
		this.manualDecision = manualDecision;
	}
}