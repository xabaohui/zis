package com.zis.youzan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youzan.open.sdk.gen.v1_0_0.model.KdtTradesSoldGetResult;

public class KdtTradesSoldGetResultNew extends KdtTradesSoldGetResult {

	/**
	 * 搜索到的交易总数
	 */
	@JsonProperty(value = "total_results")
	private Long totalResults;
	
	/**
	 * 是否存在下一页
	 */
	@JsonProperty(value = "has_next")
	private Boolean hasNext;

	public Long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}
}
