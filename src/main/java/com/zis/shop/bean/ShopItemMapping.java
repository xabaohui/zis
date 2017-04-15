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
@Table(name = "shop_item_mapping")
public class ShopItemMapping {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "book_id")
	private Integer bookId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "p_item_id")
	private Long pItemId;

	@Column(name = "p_item_sku_id")
	private Long pItemSkuId;

	@Column(name = "title")
	private String title;

	@Column(name = "item_out_num")
	private String itemOutNum;// 商家编码

	@Column(name = "system_status")
	private String systemStatus;
	
	@Column(name = "update_status")
	private String updateStatus;

	@Column(name = "upload_time")
	private Date uploadTime;

	@Column(name = "fail_reason")
	private String failReason;

	@Column(name = "create_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	@Version
	@Column(name = "version")
	private Integer version;

	public ShopItemMapping() {
	}
	
	public ShopItemMapping(Integer id, Integer bookId, Integer shopId, Long pItemId, Long pItemSkuId, String title,
			String itemOutNum, String systemStatus, String updateStatus, Date uploadTime, String failReason,
			Date createTime, Date updateTime, Integer version) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.shopId = shopId;
		this.pItemId = pItemId;
		this.pItemSkuId = pItemSkuId;
		this.title = title;
		this.itemOutNum = itemOutNum;
		this.systemStatus = systemStatus;
		this.updateStatus = updateStatus;
		this.uploadTime = uploadTime;
		this.failReason = failReason;
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

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Long getpItemId() {
		return pItemId;
	}

	public void setpItemId(Long pItemId) {
		this.pItemId = pItemId;
	}

	public Long getpItemSkuId() {
		return pItemSkuId;
	}

	public void setpItemSkuId(Long pItemSkuId) {
		this.pItemSkuId = pItemSkuId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItemOutNum() {
		return itemOutNum;
	}

	public void setItemOutNum(String itemOutNum) {
		this.itemOutNum = itemOutNum;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
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

	@Override
	public String toString() {
		return "ShopItemMapping [id=" + id + ", bookId=" + bookId + ", shopId=" + shopId + ", pItemId=" + pItemId
				+ ", pItemSkuId=" + pItemSkuId + ", title=" + title + ", itemOutNum=" + itemOutNum + ", systemStatus="
				+ systemStatus + ", updateStatus=" + updateStatus + ", uploadTime=" + uploadTime + ", failReason="
				+ failReason + ", createTime=" + createTime + ", updateTime=" + updateTime + ", version=" + version
				+ "]";
	}

	// 系统状态
	public enum ShopItemMappingSystemStatus {

		SUCCESS("success", "成功"), 
		WAIT("wait", "等待"), 
		FAIL("fail", "失败"),
		PROCESSING("processing", "处理中"),
		WAIT_DOWNLOAD("wait_download", "等待数据更新映射表"),
		DELETE("delete", "网店删除");

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

		private ShopItemMappingSystemStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
	// 系统状态
		public enum ShopItemMappingUpdateStatus {

			SUCCESS("success", "成功"), 
			FAIL("fail", "失败");

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

			private ShopItemMappingUpdateStatus(String value, String name) {
				this.value = value;
				this.name = name;
			}
		}
}
