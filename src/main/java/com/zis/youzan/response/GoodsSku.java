package com.zis.youzan.response;

import com.alibaba.fastjson.annotation.JSONField;

public class GoodsSku {

	@JSONField(name = "num_iid")
	private Long numIid;
	
	@JSONField(name = "quantity")
	private Number quantity;
	
	@JSONField(name = "price")
	private Double price;
	
	@JSONField(name = "sku_id")
	private Long skuId;

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public Number getQuantity() {
		return quantity;
	}

	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@Override
	public String toString() {
		return "GoodsSku [numIid=" + numIid + ", quantity=" + quantity + ", price=" + price + ", skuId=" + skuId + "]";
	}
}