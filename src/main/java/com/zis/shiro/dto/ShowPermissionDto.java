package com.zis.shiro.dto;

/**
 * 用户获取前端List帮助DTO
 * 
 * @author think
 * 
 */
public class ShowPermissionDto {

	private String registList;
	private String purchaseList;
	private String requirementList;
	private String bookInfoList;
	private String toolkitList;

	public String getRegistList() {
		return registList;
	}

	public void setRegistList(String registList) {
		this.registList = registList;
	}

	public String getPurchaseList() {
		return purchaseList;
	}

	public void setPurchaseList(String purchaseList) {
		this.purchaseList = purchaseList;
	}

	public String getRequirementList() {
		return requirementList;
	}

	public void setRequirementList(String requirementList) {
		this.requirementList = requirementList;
	}

	public String getBookInfoList() {
		return bookInfoList;
	}

	public void setBookInfoList(String bookInfoList) {
		this.bookInfoList = bookInfoList;
	}

	public String getToolkitList() {
		return toolkitList;
	}

	public void setToolkitList(String toolkitList) {
		this.toolkitList = toolkitList;
	}

}
