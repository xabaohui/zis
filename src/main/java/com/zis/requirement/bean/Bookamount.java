package com.zis.requirement.bean;

import java.sql.Timestamp;

/**
 * Bookamount entity. @author MyEclipse Persistence Tools
 */

public class Bookamount implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer bookId;
	private String isbn;
	private String bookName;
	private String bookAuthor;
	private String bookPublisher;
	private Integer partId;
	private Integer amount;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;
	private String operator;
	private String college;
	private String institute;
	private String partName;
	private Integer grade;
	private Integer term;

	// Constructors

	/** default constructor */
	public Bookamount() {
	}

	/** minimal constructor */
	public Bookamount(Integer bookId, String isbn, String bookName,
			String bookAuthor, String bookPublisher, Integer partId,
			Integer amount) {
		this.bookId = bookId;
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.partId = partId;
		this.amount = amount;
	}

	/** full constructor */
	public Bookamount(Integer bookId, String isbn, String bookName,
			String bookAuthor, String bookPublisher, Integer partId,
			Integer amount, Timestamp gmtCreate, Timestamp gmtModify,
			Integer version, String operator) {
		this.bookId = bookId;
		this.isbn = isbn;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookPublisher = bookPublisher;
		this.partId = partId;
		this.amount = amount;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
		this.operator = operator;
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

	public Integer getPartId() {
		return this.partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "Bookamount [id=" + id + ", bookId=" + bookId + ", isbn=" + isbn
				+ ", bookName=" + bookName + ", bookAuthor=" + bookAuthor
				+ ", bookPublisher=" + bookPublisher + ", partId=" + partId
				+ ", amount=" + amount + ", gmtCreate=" + gmtCreate
				+ ", gmtModify=" + gmtModify + ", version=" + version
				+ ", operator=" + operator + ", college=" + college
				+ ", institute=" + institute + ", partName=" + partName
				+ ", grade=" + grade + ", term=" + term + "]";
	}

}