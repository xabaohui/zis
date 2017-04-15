package com.zis.bookinfo.dto;

import org.directwebremoting.annotations.DataTransferObject;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 图书基本信息和详情聚合V2DTO
 * 添加数量及运费模板
 * @author think
 *
 */
@DataTransferObject
public class BookInfoAndDetailV2DTO extends Bookinfo {

	private static final long serialVersionUID = 3475121159026510230L;
	private String imageUrl;
	private String taobaoTitle;
	private Integer taobaoCatagoryId;
	private String summary;
	private String catalog;
	private String source;
	private Integer stockBalance;//库存量
	private Long deliveryTemplateId;//运费模板
	private Boolean taobaoForbidden; // 禁止在淘宝网发布

	public Long getDeliveryTemplateId() {
		return deliveryTemplateId;
	}

	public void setDeliveryTemplateId(Long deliveryTemplateId) {
		this.deliveryTemplateId = deliveryTemplateId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTaobaoTitle() {
		return taobaoTitle;
	}

	public void setTaobaoTitle(String taobaoTitle) {
		this.taobaoTitle = taobaoTitle;
	}

	public Integer getTaobaoCatagoryId() {
		return taobaoCatagoryId;
	}

	public void setTaobaoCatagoryId(Integer taobaoCatagoryId) {
		this.taobaoCatagoryId = taobaoCatagoryId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getStockBalance() {
		return stockBalance;
	}

	public void setStockBalance(Integer stockBalance) {
		this.stockBalance = stockBalance;
	}

	public Boolean getTaobaoForbidden() {
		return taobaoForbidden;
	}

	public void setTaobaoForbidden(Boolean taobaoForbidden) {
		this.taobaoForbidden = taobaoForbidden;
	}
}
