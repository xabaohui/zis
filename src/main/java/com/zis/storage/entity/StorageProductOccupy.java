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
 * 商品库存占用明细
 * 
 * @author yz
 * 
 */
@Entity
@Table(name = "storage_product_occupy")
public class StorageProductOccupy {

	@Id
	@GeneratedValue
	@Column(name = "occupy_id")
	private Integer occupyId;

	@Column(name = "product_id", nullable = false)
	private Integer productId;

	@Column(name = "order_id", nullable = false)
	private Integer orderId;

	@Column(name = "cur_amt", nullable = false)
	private Integer curAmt;

	@Column(name = "orig_amt", nullable = false)
	private Integer origAmt;

	@Column(name = "status", nullable = false)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", updatable = false)
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	public Integer getOccupyId() {
		return occupyId;
	}

	public void setOccupyId(Integer occupyId) {
		this.occupyId = occupyId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCurAmt() {
		return curAmt;
	}

	public void setCurAmt(Integer curAmt) {
		this.curAmt = curAmt;
	}

	public Integer getOrigAmt() {
		return origAmt;
	}

	public void setOrigAmt(Integer origAmt) {
		this.origAmt = origAmt;
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

	/**
	 * 商品库存占用记录-状态
	 * 
	 * @author yz
	 * 
	 */
	public enum Status {
		OCCUPY("occupy", "占用中"), SENT("sent", "已出库"), CANCEL("cancel", "已取消");

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
