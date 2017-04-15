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
@Table(name = "update_item_log")
public class UpdateItemLog {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "book_id")
	private Integer bookId;

	@Column(name = "mapping_id")
	private Integer mappingId;
	
	@Column(name = "amount")
	private Integer amount;

	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private String status;

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	public UpdateItemLog() {
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

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getMappingId() {
		return mappingId;
	}

	public void setMappingId(Integer mappingId) {
		this.mappingId = mappingId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public UpdateItemLog(Integer id, Integer shopId, Integer bookId, Integer mappingId, Integer amount,
			String description, String status, Date createTime, Date updateTime, Integer version) {
		super();
		this.id = id;
		this.shopId = shopId;
		this.bookId = bookId;
		this.mappingId = mappingId;
		this.amount = amount;
		this.description = description;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.version = version;
	}

	// 系统状态
	public enum UpdateItemLogStatus {

		SUCCESS("success", "成功"), FAIL("fail", "失败");

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

		private UpdateItemLogStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
