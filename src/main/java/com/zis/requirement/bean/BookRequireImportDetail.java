package com.zis.requirement.bean;

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
 * TempBookRequireImportDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="book_require_import_detail")
public class BookRequireImportDetail {

	// Fields
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@Column(name="bookId")
	private Integer bookid;
	
	@Column(name="isbn",length=32)
	private String isbn;
	
	@Column(name="bookName",length=128,nullable=false)
	private String bookName;
	
	@Column(name="bookAuthor",length=128)
	private String bookAuthor;
	
	@Column(name="bookEdition",length=32)
	private String bookEdition;
	
	@Column(name="bookPublisher",length=32,nullable=false)
	private String bookPublisher;
	
	@Column(name="departId")
	private Integer departId;
	
	@Column(name="college",length=64,nullable=false)
	private String college;
	
	@Column(name="institute",length=64,nullable=false)
	private String institute;
	
	@Column(name="partName",length=64)
	private String partName;
	
	@Column(name="classNum",length=64)
	private String classNum;
	
	@Column(name="grade")
	private Integer grade;
	
	@Column(name="term")
	private Integer term;
	
	@Column(name="amount")
	private Integer amount;
	
	@Column(name="batch_id",nullable=false)
	private Integer batchId;
	
	@Column(name="status",length=32,nullable=false)
	private String status;
	
	@Column(name="gmt_create",length=19,nullable=false,updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;
	
	@Column(name="gmt_modify",length=19,nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;
	
	@Version
	@Column(name="version",nullable=false)
	private Integer version;

	// Constructors

	/** default constructor */
	public BookRequireImportDetail() {
	}

	/** minimal constructor */
	public BookRequireImportDetail(String bookName, String bookPublisher,
			String college, String institute, Integer batchId, String status,
			Date gmtCreate, Date gmtModify, Integer version) {
		this.bookName = bookName;
		this.bookPublisher = bookPublisher;
		this.college = college;
		this.institute = institute;
		this.batchId = batchId;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	/** full constructor */
	public BookRequireImportDetail(Integer bookid, String isbn,
			String bookName, String bookAuthor, String bookEdition,
			String bookPublisher, Integer departId, String college,
			String institute, String partName, String classNum, Integer grade,
			Integer term, Integer amount, Integer batchId, String status,
			Date gmtCreate, Date gmtModify, Integer version) {
		this.bookid = bookid;
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookEdition = bookEdition;
		this.bookPublisher = bookPublisher;
		this.departId = departId;
		this.college = college;
		this.institute = institute;
		this.partName = partName;
		this.classNum = classNum;
		this.grade = grade;
		this.term = term;
		this.amount = amount;
		this.batchId = batchId;
		this.status = status;
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

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
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

	public String getBookEdition() {
		return this.bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public String getBookPublisher() {
		return this.bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public Integer getDepartId() {
		return this.departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getClassNum() {
		return this.classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getTerm() {
		return this.term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

}