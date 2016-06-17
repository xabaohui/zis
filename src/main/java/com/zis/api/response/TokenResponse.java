package com.zis.api.response;

/**
 * 用户提交验证的token
 * @author yz
 *
 */
public class TokenResponse extends BaseApiResponse {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
