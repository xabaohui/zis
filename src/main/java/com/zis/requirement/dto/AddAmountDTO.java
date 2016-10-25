package com.zis.requirement.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class AddAmountDTO {
	
	private Integer did;
	private Integer grade;
	@NotNull(message="学期不能为空")
	@Range(max=2,min=1,message="学期在1和2之间")
	@Digits (integer=2,fraction=0,message="学期只能填数字")
	private Integer term;
	@Digits (integer=100,fraction=0,message="学期只能填数字")
	private Integer amount;
	private String operator;

	public Integer getDid() {
		return did;
	}

	public void setDid(Integer did) {
		this.did = did;
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

}
