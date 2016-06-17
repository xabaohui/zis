/**
 * 
 */
package com.zis.api.response;

/**
 * @author lvbin 2015-10-10
 */
public class BaseApiResponse {

	private Integer code;
	private String msg;

	/** 成功 */
	public static final Integer CODE_SUCCESS = 200;
	/** 参数异常 */
	public static final Integer CODE_ILLEGAL_ARGUMENT = 501;
	/** 系统错误 */
	public static final Integer CODE_INNER_ERROR = 500;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
