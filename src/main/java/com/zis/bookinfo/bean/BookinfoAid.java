package com.zis.bookinfo.bean;

/**
 * BookinfoAid entity. @author MyEclipse Persistence Tools
 */

public class BookinfoAid implements java.io.Serializable {

	// Fields

	private Integer id;
	private String groupKey;
	private String shortBookName;
	private String ids;
	private Integer totalCount;
	private Integer checkLevel;

	// Constructors

	/** default constructor */
	public BookinfoAid() {
	}

	/** full constructor */
	public BookinfoAid(String groupKey, String shortBookName, String ids,
			Integer totalCount) {
		this.groupKey = groupKey;
		this.shortBookName = shortBookName;
		this.ids = ids;
		this.totalCount = totalCount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupKey() {
		return this.groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public String getShortBookName() {
		return this.shortBookName;
	}

	public void setShortBookName(String shortBookName) {
		this.shortBookName = shortBookName;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCheckLevel() {
		return checkLevel;
	}

	public void setCheckLevel(Integer checkLevel) {
		this.checkLevel = checkLevel;
	}

}