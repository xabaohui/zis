package com.zis.youzan.dto;

/**
 * 有赞API添加商品DTO
 * 
 * @author think
 * 
 */
public class AddItemDto {

	private String title;// 标题
	private Double price;// 价格
	private Number quantity;// 数量
	private String outerId;// 商家编码
	private String desc;// 描述
	private Double postFee;// 运费
	private String[] imagesUrl;// 图片网址
	private Number deliveryTemplateId;// 运费模板
	private String skuProperties;// SKU详情
	private Number skuQuantities;// SKU数量
	private Double skuPrices;// SKU价格

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Number getQuantity() {
		return quantity;
	}

	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	public Number getDeliveryTemplateId() {
		return deliveryTemplateId;
	}

	public void setDeliveryTemplateId(Number deliveryTemplateId) {
		this.deliveryTemplateId = deliveryTemplateId;
	}

	public Number getSkuQuantities() {
		return skuQuantities;
	}

	public void setSkuQuantities(Number skuQuantities) {
		this.skuQuantities = skuQuantities;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public String[] getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(String... imagesUrl) {
		this.imagesUrl = imagesUrl;
	}

	public String getSkuProperties() {
		return skuProperties;
	}

	public void setSkuProperties(String skuProperties) {
		this.skuProperties = skuProperties;
	}

	public Double getSkuPrices() {
		return skuPrices;
	}

	public void setSkuPrices(Double skuPrices) {
		this.skuPrices = skuPrices;
	}

	@Override
	public String toString() {
		return "AddItemDto [title=" + title + ", price=" + price + ", quantity=" + quantity + ", outerId=" + outerId
				+ ", desc=" + desc + ", postFee=" + postFee + ", imagesUrl=" + imagesUrl + ", deliveryTemplateId="
				+ deliveryTemplateId + ", skuProperties=" + skuProperties + ", skuQuantities=" + skuQuantities
				+ ", skuPrices=" + skuPrices + "]";
	}
}
