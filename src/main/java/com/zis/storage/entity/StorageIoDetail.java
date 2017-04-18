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
 * 库存变动明细表
 */
@Entity
@Table(name = "storage_io_detail")
public class StorageIoDetail {
	
	public static final String SORT_POS_ID = "posId";
	public static final String SORT_CREATE_TIME = "gmtCreate";

	// Fields
	@Id
	@GeneratedValue
	@Column(name = "detail_id")
	private Integer detailId;

	@Column(name = "batch_id")
	private Integer batchId;

	@Column(name = "repo_id", nullable = false, updatable = false)
	private Integer repoId;

	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "pos_id", nullable = false, updatable = false)
	private Integer posId;
	
	@Column(name = "pos_label", nullable = false, updatable = false)
	private String posLabel;

	@Column(name = "sku_id", nullable = false, updatable = false)
	private Integer skuId;
	
	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	// 出库/入库操作完成后，库位中该商品剩余库存量
	@Column(name = "balance")
	private Integer balance;

	@Column(name = "operator", nullable = false)
	private Integer operator;

	@Column(name = "io_detail_type", nullable = false)
	private String ioDetailType;

	@Column(name = "detail_status", nullable = false)
	private String detailStatus;

	@Column(name = "gmt_create", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtCreate;

	@Column(name = "gmt_modify")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gmtModify;

	@Version
	@Column(name = "version")
	private Integer version;

	// Property accessors

	public Integer getDetailId() {
		return this.detailId;
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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}
	
	public String getPosLabel() {
		return posLabel;
	}

	public void setPosLabel(String posLabel) {
		this.posLabel = posLabel;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public String getIoDetailType() {
		return ioDetailType;
	}

	public void setIoDetailType(String ioDetailType) {
		this.ioDetailType = ioDetailType;
	}

	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}

	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
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

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 出入库类型
	 * 
	 * @author yz
	 * 
	 */
	public enum IoType {
		IN("inwarehouse", "入库"), OUT("outwarehouse", "出库");

		private String value;
		private String display;

		private IoType(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
		
		public static IoType getIoType(String type) {
			for (IoType st : IoType.values()) {
				if(st.getValue().equals(type))
					return st;
			}
			return null;
		}
	}
	
	public enum DetailStatus {
		WAITING("waiting", "等待处理"), 
		PROCESSING("processing", "处理中"), 
		SUCCESS("success", "已完成"), 
		LACKNESS("lackness", "缺货"), 
		USELESS("useless", "无效"), 
		CANCEL("cancel", "已取消");
		
		private String value;
		private String display;

		private DetailStatus(String value, String display) {
			this.value = value;
			this.display = display;
		}

		public String getValue() {
			return value;
		}

		public String getDisplay() {
			return display;
		}
		
		/**
		 * 状态是否是最终状态
		 * @param status
		 * @return
		 */
		public static boolean isFinalStatus(DetailStatus status) {
			return SUCCESS.equals(status) || LACKNESS.equals(status) || USELESS.equals(status);
		}
		
		/**
		 * 状态是否是最终状态
		 * @param status
		 * @return
		 */
		public static boolean isFinalStatus(String status) {
			return isFinalStatus(getDetailStatus(status));
		}
		
		public static DetailStatus getDetailStatus(String status) {
			for (DetailStatus st : DetailStatus.values()) {
				if(st.getValue().equals(status))
					return st;
			}
			return null;
		}
	}
}