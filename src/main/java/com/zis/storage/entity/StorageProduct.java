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
 * 仓储系统-商品
 */
@Entity
@Table(name = "storage_product")
public class StorageProduct {

	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "sku_id", nullable = false)
	private Integer skuId;

	@Column(name = "sku_name", nullable = false)
	private String skuName;

	@Column(name = "repo_id", nullable = false)
	private Integer repoId;

	@Column(name = "subject_id", nullable = false)
	private Integer subjectId;

	@Column(name = "subject_name", nullable = false)
	private String subjectName;

	@Column(name = "stock_amt", nullable = false)
	private Integer stockAmt;

	@Column(name = "stock_occupy")
	private Integer stockOccupy;

	@Column(name = "stock_available")
	private Integer stockAvailable;

	@Column(name = "lock_flag")
	private Boolean lockFlag;

	@Column(name = "lock_reason")
	private String lockReason;

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
	public StorageProduct() {
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(Integer stockAmt) {
		this.stockAmt = stockAmt;
	}

	public Integer getStockOccupy() {
		return stockOccupy;
	}

	public void setStockOccupy(Integer stockOccupy) {
		this.stockOccupy = stockOccupy;
	}

	public Integer getStockAvailable() {
		return stockAvailable;
	}

	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}

	public Boolean getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
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
}