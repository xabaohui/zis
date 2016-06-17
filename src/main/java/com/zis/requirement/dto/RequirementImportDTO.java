package com.zis.requirement.dto;

/**
 * 图书需求表格导入过程中保存数据的对象
 * 
 * @author yz
 * 
 */
public class RequirementImportDTO {

	private String college;
	private String institute;
	private String partName;
	private Integer count;
	private Integer grade;
	private Integer term;
	private String isbn;
	private String bookName;
	private String failReason;

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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
	public String toString() {
		return "RequirementImportDTO [college=" + college + ", institute="
				+ institute + ", partName=" + partName + ", count=" + count
				+ ", grade=" + grade + ", term=" + term + ", isbn=" + isbn
				+ ", bookName=" + bookName + ", failReason=" + failReason + "]";
	}
}
