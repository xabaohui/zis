package com.zis.purchase.bean;

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
 * Requirementamount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="purchase_plan")
public class PurchasePlan {

	// Fields

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="bookId", nullable=false)
	private Integer bookId;
	
	@Column(name="isbn", nullable=false)
	private String isbn;
	
	@Column(name="bookName", nullable=false, length=100)
	private String bookName;
	
	@Column(name="bookAuthor", nullable=false, length=100)
	private String bookAuthor;
	
	@Column(name="bookPublisher", nullable=false, length=100)
	private String bookPublisher;
	
	@Column(name="bookEdition", nullable=false, length=100)
	private String bookEdition;
	
	@Column(name="requireAmount", nullable=false)
	private Integer requireAmount; // 需求量
	
	@Column(name="manual_decision", nullable=false)
	private Integer manualDecision; // 人工定义需求量
	
//	@Column(name="stockAmount", nullable=false)
//	private Integer stockAmount; // 库存量
	@Column(name = "repo_id")
	private Integer repoId;
	
	@Column(name="purchasedAmount", nullable=false)
	private Integer purchasedAmount; // 在途库存量（已采购未入库）
	
	@Column(name="status", nullable=false)
	private String status;
	
	@Column(name="flag")
	private String flag; // 标记：black黑名单，white白名单，normal正常
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="GMT_CREATE", nullable=false, updatable=false)
	private Date gmtCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="GMT_MODIFY", nullable=false)
	private Date gmtModify;
	
	@Version
	@Column(name="version", nullable=false)
	private Integer version;

	// Constructors

	/** default constructor */
	public PurchasePlan() {
	}

	/** full constructor */
	public PurchasePlan(Integer id, Integer bookId, String isbn, String bookName, String bookAuthor,
			String bookPublisher, String bookEdition, Integer requireAmount, Integer manualDecision, Integer repoId,
			Integer purchasedAmount, String status, String flag, Date gmtCreate, Date gmtModify, Integer version) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.bookEdition = bookEdition;
		this.requireAmount = requireAmount;
		this.manualDecision = manualDecision;
		this.repoId = repoId;
		this.purchasedAmount = purchasedAmount;
		this.status = status;
		this.flag = flag;
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
	
	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
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

//	public Integer getStockAmount() {
//		return stockAmount;
//	}
//
//	public void setStockAmount(Integer stockAmount) {
//		this.stockAmount = stockAmount;
//	}

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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}