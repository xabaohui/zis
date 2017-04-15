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
@Table(name = "download_item_log")
public class DownloadItemLog {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "status")
	private String status;

	@Column(name = "description")
	private String description;

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	public DownloadItemLog() {
	}

	public DownloadItemLog(Integer id, Integer shopId, String status, String description, Date createTime,
			Date updateTime, Integer version) {
		super();
		this.id = id;
		this.shopId = shopId;
		this.status = status;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	// 系统状态
	public enum DownloadItemLogStatus {

		SUCCESS("success", "成功"), FAIL("fail", "失败"), ERROR("error", "出错");

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

		private DownloadItemLogStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
