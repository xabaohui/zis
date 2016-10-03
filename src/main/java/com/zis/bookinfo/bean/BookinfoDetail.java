package com.zis.bookinfo.bean;

import java.sql.Timestamp;

/**
 * BookinfoDetail entity. @author MyEclipse Persistence Tools
 */

public class BookinfoDetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2036786162490743934L;
	private Integer bookid;
	private String imageUrl;
	private String taobaoTitle;
	private Integer taobaoCatagoryId;
	private Boolean taobaoForbidden; // 禁止在淘宝网发布
	private String summary;
	private String catalog;
	private Integer outId;
	private String source;
	private Timestamp gmtCreate;
	private Timestamp gmtModify;
	private Integer version;

	// Constructors

	/** default constructor */
	public BookinfoDetail() {
	}

	/** minimal constructor */
	public BookinfoDetail(Integer bookid, Timestamp gmtCreate,
			Timestamp gmtModify, Integer version) {
		this.bookid = bookid;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	/** full constructor */
	public BookinfoDetail(Integer bookid, String imageUrl, String taobaoTitle,
			Integer taobaoCatagoryId, String summary, String catalog,
			Timestamp gmtCreate, Timestamp gmtModify, Integer version) {
		this.bookid = bookid;
		this.imageUrl = imageUrl;
		this.taobaoTitle = taobaoTitle;
		this.taobaoCatagoryId = taobaoCatagoryId;
		this.summary = summary;
		this.catalog = catalog;
		this.gmtCreate = gmtCreate;
		this.gmtModify = gmtModify;
		this.version = version;
	}

	// Property accessors

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTaobaoTitle() {
		return this.taobaoTitle;
	}

	public void setTaobaoTitle(String taobaoTitle) {
		this.taobaoTitle = taobaoTitle;
	}

	public Integer getTaobaoCatagoryId() {
		return this.taobaoCatagoryId;
	}

	public void setTaobaoCatagoryId(Integer taobaoCatagoryId) {
		this.taobaoCatagoryId = taobaoCatagoryId;
	}

	public Boolean getTaobaoForbidden() {
		return taobaoForbidden;
	}

	public void setTaobaoForbidden(Boolean taobaoForbidden) {
		this.taobaoForbidden = taobaoForbidden;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Integer getOutId() {
		return outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Timestamp getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtModify() {
		return this.gmtModify;
	}

	public void setGmtModify(Timestamp gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}