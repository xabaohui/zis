package com.zis.bookinfo.dto;

import com.zis.bookinfo.bean.Bookinfo;

/**
 * 图书基本信息和详情聚合DTO
 * @author yz
 *
 */
public class BookInfoAndDetailDTO extends Bookinfo {

	private static final long serialVersionUID = 3475121159026510230L;
	private String imageUrl;
	private String taobaoTitle;
	private Integer taobaoCatagoryId;
	private String summary;
	private String catalog;

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
}
