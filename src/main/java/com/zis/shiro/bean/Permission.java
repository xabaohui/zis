package com.zis.shiro.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "acl_permission_tab")
public class Permission {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;

	@Column(name = "permission_code")
	private String permissionCode;

	@Column(name = "permission_name")
	private String permissionName;

	@Column(name = "permission_description")
	private String permissionDescription;

	private String url;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public Permission() {
	}

	public Permission(Integer id, String permissionCode, String permissionName, String permissionDescription,
			String url, String groupName, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.permissionCode = permissionCode;
		this.permissionName = permissionName;
		this.permissionDescription = permissionDescription;
		this.url = url;
		this.groupName = groupName;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDescription() {
		return permissionDescription;
	}

	public void setPermissionDescription(String permissionDescription) {
		this.permissionDescription = permissionDescription;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	@Override
	public String toString() {
		return "Permission [id=" + id + ", permissionCode=" + permissionCode + ", permissionName=" + permissionName
				+ ", permissionDescription=" + permissionDescription + ", url=" + url + ", groupName=" + groupName
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]\n";
	}
}
