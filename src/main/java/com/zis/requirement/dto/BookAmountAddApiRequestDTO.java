package com.zis.requirement.dto;

import java.util.List;

/**
 * 添加教材使用量API接口用于传输的对象
 * @author lenovo
 *
 */
public class BookAmountAddApiRequestDTO {

	private Integer departId;
	private Integer amount;
	private String operator;
	private List<Integer> bookIdList;
	private int grade;
	private int term;

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<Integer> getBookIdList() {
		return bookIdList;
	}

	public void setBookIdList(List<Integer> bookIdList) {
		this.bookIdList = bookIdList;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
}
