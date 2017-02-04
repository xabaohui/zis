package com.zis.youzan.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品类
 * 
 * @author think
 * 
 */
public class Item {

	@JSONField(name = "num_iid")
	private Long numIid;// 有赞商城商品ID

	private String title;// 商品标题

	private List<GoodsSku> skus;// 商品skuInfo

	@JSONField(name = "outerId")
	private String outerId;// 商品编码

	@JSONField(name = "post_fee")
	private Double postFee;// 运费

	@JSONField(name = "price")
	private Double price;// 商品价格

	@JSONField(name = "delivery_template_id")
	private Long deliveryTemplateId;// 运费模板Id

	private Long num;// 商品数量

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GoodsSku> getSkus() {
		return skus;
	}

	public void setSkus(List<GoodsSku> skus) {
		this.skus = skus;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getDeliveryTemplateId() {
		return deliveryTemplateId;
	}

	public void setDeliveryTemplateId(Long deliveryTemplateId) {
		this.deliveryTemplateId = deliveryTemplateId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "Item [numIid=" + numIid + ", title=" + title + ", skus=" + skus + ", outerId=" + outerId + ", postFee="
				+ postFee + ", price=" + price + ", deliveryTemplateId=" + deliveryTemplateId + ", num=" + num + "]\n";
	}
}