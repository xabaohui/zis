package com.zis.requirement.bean;

/**
 * SysVar entity. @author MyEclipse Persistence Tools
 */

public class SysVar implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String depKey;
	private Integer depValue;

	// Constructors

	/** default constructor */
	public SysVar() {
	}

	/** full constructor */
	public SysVar(String depKey, Integer depValue) {
		this.depKey = depKey;
		this.depValue = depValue;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepKey() {
		return this.depKey;
	}

	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}

	public Integer getDepValue() {
		return this.depValue;
	}

	public void setDepValue(Integer depValue) {
		this.depValue = depValue;
	}

}