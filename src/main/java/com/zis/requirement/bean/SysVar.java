package com.zis.requirement.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysVar entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="sys_var")
public class SysVar {

	// Fields
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="depKey",length=100)
	private String depKey;
	@Column(name="depValue")
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