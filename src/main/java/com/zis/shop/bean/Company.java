package com.zis.shop.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "acl_company")
public class Company {

	@Id
	@GeneratedValue
	@Column(name = "company_id")
	private Integer companyId;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "contacts")
	private String contacts;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "address")
	private String address;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Column(name = "status")
	private String status;

	public Company() {
	}

	public Company(Integer companyId, String companyName, String contacts, String mobile, String address,
			Integer version, Date createTime, Date updateTime, String status) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.contacts = contacts;
		this.mobile = mobile;
		this.address = address;
		this.version = version;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", companyName=" + companyName + ", contacts=" + contacts
				+ ", mobile=" + mobile + ", address=" + address + ", version=" + version + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status + "]";
	}

	public enum CompanyStatus {

		NORMAL("normal", "正常"), DELETE("delete", "删除");

		private String value;
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private CompanyStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
