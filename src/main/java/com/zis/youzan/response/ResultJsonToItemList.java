package com.zis.youzan.response;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ResultJsonToItemList {

	@JSONField(name = "response")
	private Response response;// 正确的response

	@JSONField(name = "error_response")
	private ErrorResponse errorResponse;// 错误的response

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	@Override
	public String toString() {
		return "ResultJson [response=" + response + ", errorResponse=" + errorResponse + "]";
	}

	/**
	 * 正确的response
	 * 
	 * @author think
	 * 
	 */
	public class Response {

		@JSONField(name = "total_results")
		private Long totalResults;// 总数量

		@JSONField(name = "items")
		private List<Item> items;// 商品集合

		public Long getTotalResults() {
			return totalResults;
		}

		public void setTotalResults(Long totalResults) {
			this.totalResults = totalResults;
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		@Override
		public String toString() {
			return "Response [totalResults=" + totalResults + ", items=" + items + "]";
		}
	}
}
