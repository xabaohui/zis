package com.zis.shiro.bean;

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
@Table(name = "acl_role_tab")
public class Role {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_description")
	private String roleDescription;

	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Column(name = "create_username")
	private String createUserName;
	
	@Version
	@Column(name="Version")
	private Integer version;
	
	public Role() {
	}

	public Role(Integer id, String roleName, String roleDescription, String roleCode, Date createTime, Date updateTime,
			String createUserName, Integer version) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
		this.roleCode = roleCode;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.createUserName = createUserName;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roleDescription=" + roleDescription + ", roleCode="
				+ roleCode + ", createTime=" + createTime + ", updateTime=" + updateTime + ", createUserName="
				+ createUserName + ", version=" + version + "]";
	}
}
