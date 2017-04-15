package com.zis.storage.dto;

public class StockDTO {

	private Integer stockId; // 库位库存Id
	private Integer posId; // 库位Id
	private String posLabel; // 库位标签
	private Integer repoId; // 仓库Id
	private Integer productId; // 商品表Id
	private Integer skuId; // 商品SkuId
	private Integer totalAmt;
	private Integer occupyAmt;

	public StockDTO() {
	}
	
	public StockDTO(Integer stockId, Integer posId, String posLabel, Integer repoId, Integer productId,
			Integer skuId, Integer totalAmt, Integer occupyAmt) {
		this.stockId = stockId;
		this.posId = posId;
		this.posLabel = posLabel;
		this.repoId = repoId;
		this.productId = productId;
		this.skuId = skuId;
		this.totalAmt = totalAmt;
		this.occupyAmt = occupyAmt;
	}



	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Integer getPosId() {
		return posId;
	}

	public void setPosId(Integer posId) {
		this.posId = posId;
	}

	public Integer getRepoId() {
		return repoId;
	}

	public void setRepoId(Integer repoId) {
		this.repoId = repoId;
	}

	public String getPosLabel() {
		return posLabel;
	}

	public void setPosLabel(String posLabel) {
		this.posLabel = posLabel;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Integer totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Integer getOccupyAmt() {
		return occupyAmt;
	}

	public void setOccupyAmt(Integer occupyAmt) {
		this.occupyAmt = occupyAmt;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
