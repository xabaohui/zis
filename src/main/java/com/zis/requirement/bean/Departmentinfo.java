package com.zis.requirement.bean;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Departmentinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="departmentinfo")
public class Departmentinfo {

	// Fields
	@Id
	@GeneratedValue
	@Column(name="dId")
	private Integer did;
	@Column(name="partName",length=30,nullable=false)
	private String partName;
	@Column(name="institute",length=30,nullable=false)
	private String institute;
	@Column(name="college",length=30,nullable=false)
	private String college;
	@Column(name="years",nullable=false)
	private Integer years;
	@Column(name="GMT_CREATE",length=19,updatable=false)
	private Timestamp gmtCreate;
	@Column(name="GMT_MODIFY",length=19)
	private Timestamp gmtModify;
	@Column(name="version")
	private Integer version;

	// Constructors

	/** default constructor */
	public Departmentinfo() {
	}

	/** minimal constructor */
	public Departmentinfo(String partName, String institute, String college,
			Integer years) {
		this.partName = partName;
		this.institute = institute;
		this.college = college;
		this.years = years;
	}

	/** full constructor */
	public Departmentinfo(String partName, String institute, String college,
			Integer years, Timestamp gmtCreate,
			Timestamp gmtModify, Integer version) {
		this.partName = partName;
		this.institute = institute;
		this.college = college;
		this.years = years;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getDid() {
		return this.did;
	}

	public void setDid(Integer did) {
		this.did = did;
	}

	public String getPartName() {
		return this.partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
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