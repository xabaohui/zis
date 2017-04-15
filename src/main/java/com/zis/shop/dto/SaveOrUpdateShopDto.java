package com.zis.shop.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class SaveOrUpdateShopDto {
	
	private Integer shopId;

	private Integer companyId;
	
	@NotBlank(message = "店铺名称不能为空")
	private String shopName;

	private String shopUrl;
	
	private String appId;

	private String appSecret;
	
	@NotBlank(message = "平台名称不能为空")
	private String pName;
	
	@Email(message = "邮箱格式不符")
	@NotBlank(message = "邮箱不能为空")
	private String emails;
	
	@NotBlank(message = "折扣率不能为空")
	@Pattern(regexp = "^(([1]{1})|([0]{1}\\.[0-9]{1,2}))$",message = "折扣率请填1至0.01之间的数字")
	private String discount;
	
	@NotNull(message = "运费模板不能为空")
	private Long deliveryTemplateId;//运费模板 
	
	private Long templateId;//商品模板 
	
	private String typeStatus;
	
	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(String typeStatus) {
		this.typeStatus = typeStatus;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Long getDeliveryTemplateId() {
		return deliveryTemplateId;
	}

	public void setDeliveryTemplateId(Long deliveryTemplateId) {
		this.deliveryTemplateId = deliveryTemplateId;
	}

	@Override
	public String toString() {
		return "SaveOrUpdateShopDto [shopId=" + shopId + ", companyId=" + companyId + ", shopName=" + shopName
				+ ", shopUrl=" + shopUrl + ", appId=" + appId + ", appSecret=" + appSecret + ", pName=" + pName
				+ ", emails=" + emails + ", discount=" + discount + ", deliveryTemplateId=" + deliveryTemplateId
				+ ", templateId=" + templateId + ", typeStatus=" + typeStatus + "]";
	}
}
