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

	@Column(name = "p_item_id")
	private Long pItemId;

	@Column(name = "p_item_sku_id")
	private Long pItemSkuId;

	@Column(name = "title")
	private String title;

	@Column(name = "item_out_num")
	private String itemOutNum;//商家编码

	@Column(name = "shop_status")
	private String shopStatus;

	@Column(name = "system_status")
	private String systemStatus;

	@Column(name = "flag")
	private Integer flag;

	@Column(name = "upload_time")
	private Date uploadTime;

	@Column(name = "p_price")
	private Double pPrice;

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

	public ShopItemMapping(Integer id, Integer bookId, Long pItemId, Long pItemSkuId, String title, String itemOutNum,
			String shopStatus, String systemStatus, Integer flag, Date uploadTime, Double pPrice, String failReason,
			Date createTime, Date updateTime, Integer version) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.pItemId = pItemId;
		this.pItemSkuId = pItemSkuId;
		this.title = title;
		this.itemOutNum = itemOutNum;
		this.shopStatus = shopStatus;
		this.systemStatus = systemStatus;
		this.flag = flag;
		this.uploadTime = uploadTime;
		this.pPrice = pPrice;
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

	public String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getSystemStatus() {
		return systemStatus;
	}

	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Double getpPrice() {
		return pPrice;
	}

	public void setpPrice(Double pPrice) {
		this.pPrice = pPrice;
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
		return "ShopItemMapping [id=" + id + ", bookId=" + bookId + ", pItemId=" + pItemId + ", pItemSkuId="
				+ pItemSkuId + ", title=" + title + ", itemOutNum=" + itemOutNum + ", shopStatus=" + shopStatus
				+ ", systemStatus=" + systemStatus + ", flag=" + flag + ", uploadTime=" + uploadTime + ", pPrice="
				+ pPrice + ", failReason=" + failReason + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", version=" + version + "]";
	}

	// 系统状态
	public enum ShopItemMappingSystemStatus {

		ON_SALES("on_sales", "已上架"), FOR_SHELVED("for_shelved", "已下架"), SOLD_OUT("sold_out", "已售罄");

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

	// 店铺状态
	public enum ShopItemMappingShopStatus {

		SUCCESS("success", "成功"), WAIT("wait", "等待"), FAIL("fail", "失败");

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

		private ShopItemMappingShopStatus(String value, String name) {
			this.value = value;
			this.name = name;
		}
	}
}
