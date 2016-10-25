package com.zis.bookinfo.dto;

import org.directwebremoting.annotations.DataTransferObject;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 图书基本信息和详情聚合DTO
 * @author yz
 *
 */
@DataTransferObject
public class BookInfoAndDetailDTO extends Bookinfo {

	private static final long serialVersionUID = 3475121159026510230L;
	private String imageUrl;
	private String taobaoTitle;
	private Integer taobaoCatagoryId;
	private String summary;
	private String catalog;
	private String source;
	private Integer stockBalance;
	private Boolean taobaoForbidden; // 禁止在淘宝网发布

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
