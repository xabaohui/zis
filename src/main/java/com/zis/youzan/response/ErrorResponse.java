package com.zis.youzan.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 错误的response 返回的code 和msg
 * 
 * @author think
 * 
 */
public class ErrorResponse {

	@JSONField(name = "code")
	private String code;// 错误编码

	@JSONField(name = "msg")
	private String msg;// 错误信息

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "rep [code=" + code + ", msg=" + msg + "]";
	}

}