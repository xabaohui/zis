package com.zis.youzan.response;

import com.alibaba.fastjson.annotation.JSONField;

public class ResultJsonToItem {

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

		@JSONField(name = "item")
		private Item item;// 单个商品

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		@Override
		public String toString() {
			return "Response [item=" + item + "]";
		}
	}
}
