package com.zis.api.response;

public class RequiredAmountQueryData {

	private Integer bookId;
	private String bookName;
	private Integer requireAmount;
	// private Integer purchasedAmount;
	private String isbn;
	private String bookAuthor;
	private String bookPublisher;
	private String bookEdition;
	private String memo = "";// XXX 测试数据，待清理

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public String getBookEdition() {
		return bookEdition;
	}

	public void setBookEdition(String bookEdition) {
		this.bookEdition = bookEdition;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getRequireAmount() {
		return requireAmount;
	}

	public void setRequireAmount(Integer requireAmount) {
		this.requireAmount = requireAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
