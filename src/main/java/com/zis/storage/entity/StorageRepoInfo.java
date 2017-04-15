package com.zis.storage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * 仓库信息
 */
@Entity
@Table(name = "storage_repo_info")
public class StorageRepoInfo {

	@Id
	@GeneratedValue
	@Column(name = "repo_id")
	private Integer repoId;

	@Column(name = "name")
	private String name;

	@Column(name = "owner_id")
	private Integer ownerId;

	@Column(name = "status")
	private String status;

	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	// Constructors

	/** default constructor */
	public StorageRepoInfo() {
	}

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * 仓库状态
	 * @author yz
	 *
	 */
	public enum Status {
		
		AVAILABLE("available", "可用"),
		LOCKED("locked", "锁定"),
		DELETE("delete", "删除");
		
		private String value;
		private String display;

		private Status(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}
		
		public String getDisplay() {
			return display;
		}
	}

}