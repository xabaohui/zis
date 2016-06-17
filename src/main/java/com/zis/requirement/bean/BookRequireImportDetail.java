package com.zis.requirement.bean;

import java.sql.Timestamp;

/**
 * TempBookRequireImportDetail entity. @author MyEclipse Persistence Tools
 */

public class BookRequireImportDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer bookid;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookEdition;
	private String bookPublisher;
	private Integer departId;
	private String college;
	private String institute;
	private String partName;
	private String classNum;
	private Integer grade;
	private Integer term;
	private Integer amount;
	private Integer batchId;
	private String status;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public BookRequireImportDetail() {
	}

	/** minimal constructor */
	public BookRequireImportDetail(String bookName, String bookPublisher,
			String college, String institute, Integer batchId, String status,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
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
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
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

}