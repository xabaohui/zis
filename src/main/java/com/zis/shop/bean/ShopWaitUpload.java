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
@Table(name = "shop_wait_upload")
public class ShopWaitUpload {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "batch_id")
	private Integer batchId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "status")
	private String status;

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date crateTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	public ShopWaitUpload() {
	}

	public ShopWaitUpload(Integer id, Integer batchId, Integer shopId, String status, Date crateTime, Date updateTime,
			Integer version) {
		this.id = id;
		this.batchId = batchId;
		this.shopId = shopId;
		this.status = status;
		this.crateTime = crateTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
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

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
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

	@Override
	public String toString() {
		return "ShopWaitUpload [id=" + id + ", batchId=" + batchId + ", shopId=" + shopId + ", status=" + status
				+ ", crateTime=" + crateTime + ", updateTime=" + updateTime + ", version=" + version + "]";
	}

	// 状态
	public enum ShopWaitUpLoadStatus {

		SUCCESS("success", "成功"), WAIT("wait", "等待");

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

		private ShopWaitUpLoadStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
