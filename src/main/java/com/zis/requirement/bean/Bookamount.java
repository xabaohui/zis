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
 * BookAmount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bookamount")
public class BookAmount {

	// Fields
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "bookId", nullable = false)
	private Integer bookId;

	@Column(name = "ISBN", nullable = false, length = 50)
	private String isbn;

	@Column(name = "bookName", nullable = false, length = 50)
	private String bookName;

	@Column(name = "bookAuthor", nullable = false, length = 50)
	private String bookAuthor;

	@Column(name = "bookPublisher", nullable = false)
	private String bookPublisher;

	@Column(name = "partId", nullable = false)
	private Integer partId;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "GMT_CREATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "GMT_MODIFY")
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "operator", length = 110)
	private String operator;

	@Column(name = "college", length = 100)
	private String college;

	@Column(name = "institute", length = 100)
	private String institute;

	@Column(name = "partName", length = 100)
	private String partName;

	@Column(name = "grade")
	private Integer grade;

	@Column(name = "term")
	private Integer term;

	// Constructors

	/** default constructor */
	public BookAmount() {
	}

	/** minimal constructor */
	public BookAmount(Integer bookId, String isbn, String bookName,
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
	public BookAmount(Integer bookId, String isbn, String bookName,
			String bookAuthor, String bookPublisher, Integer partId,
			Integer amount, Date gmtCreate, Date gmtModify,
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
		return "BookAmount [id=" + id + ", bookId=" + bookId + ", isbn=" + isbn
				+ ", bookName=" + bookName + ", bookAuthor=" + bookAuthor
				+ ", bookPublisher=" + bookPublisher + ", partId=" + partId
				+ ", amount=" + amount + ", gmtCreate=" + gmtCreate
				+ ", gmtModify=" + gmtModify + ", version=" + version
				+ ", operator=" + operator + ", college=" + college
				+ ", institute=" + institute + ", partName=" + partName
				+ ", grade=" + grade + ", term=" + term + "]";
	}

}