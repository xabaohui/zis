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
 * 库存变动批次表
 */
@Entity
@Table(name = "storage_io_batch")
public class StorageIoBatch {

	@Id
	@GeneratedValue
	@Column(name = "batch_id")
	private Integer batchId;

	@Column(name = "repo_id", nullable = false)
	private Integer repoId;

	@Column(name = "memo", nullable = false)
	private String memo;

	@Column(name = "operator")
	private Integer operator;

	@Column(name = "biz_type", nullable = false)
	private String bizType;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "status", nullable = false)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_create", nullable = false, updatable = false)
	private Date gmtCreate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "gmt_modify", nullable = false)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	// Property accessors

	public String getBizType() {
		return this.bizType;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 业务类型
	 * 
	 * @author yz
	 * 
	 */
	public enum BizType {

		IN_BATCH("in_batch", "批量入库"), IN_DAILY("in_daily", "日常入库"), OUT_BATCH("out_batch", "批量出库"), OUT_DAILY("out_daily", "日常入库");

		private String value;
		private String display;

		private BizType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
		
		public static BizType getBizType(String value) {
			for (BizType bizType : BizType.values()) {
				if(bizType.getValue().equals(value)) {
					return bizType;
				}
			}
			return null;
		}
		
		public static boolean isInStorage(String bizType) {
			BizType type = getBizType(bizType);
			return isInStorage(type);
		}
		
		public static boolean isInStorage(BizType bizType) {
			switch (bizType) {
			case IN_BATCH:
			case IN_DAILY:
				return true;
			case OUT_BATCH:
			case OUT_DAILY:
				return false;
			default:
				throw new RuntimeException("bizType不合法:" + bizType);
			}
		}
		
		public static boolean isDailyStorage(String bizType) {
			BizType type = getBizType(bizType);
			return isDailyStorage(type);
		}
		
		public static boolean isDailyStorage(BizType bizType) {
			switch (bizType) {
			case IN_BATCH:
			case OUT_BATCH:
				return false;
			case IN_DAILY:
			case OUT_DAILY:
				return true;
			default:
				throw new RuntimeException("bizType不合法:" + bizType);
			}
		}
	}

	/**
	 * 状态
	 * 
	 * @author yz
	 * 
	 */
	public enum Status {
		CREATED("created", "已创建"), FINISH("finish", "已完成"), CANCEL("cancel", "已取消");

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