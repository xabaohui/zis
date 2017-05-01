package com.zis.storage.dto;

import com.zis.bookinfo.bean.Bookinfo;

public class StorageProductViewDTO extends Bookinfo {

	private static final long serialVersionUID = 1L;

	private Integer skuId;
	private Integer productId; // 库存商品Id
	private Integer stockAmt; // 总数量
	private Integer stockOccupy; // 占用量
	private Integer stockAvailable; // 可用量

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(Integer stockAmt) {
		this.stockAmt = stockAmt;
	}

	public Integer getStockOccupy() {
		return stockOccupy;
	}

	public void setStockOccupy(Integer stockOccupy) {
		this.stockOccupy = stockOccupy;
	}

	public Integer getStockAvailable() {
		return stockAvailable;
	}

	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}

}
