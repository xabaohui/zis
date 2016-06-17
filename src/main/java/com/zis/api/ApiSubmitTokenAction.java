package com.zis.api;

import java.util.Random;

import org.apache.log4j.Logger;

import com.zis.api.response.TokenResponse;
import com.zis.common.util.ZisUtils;

/**
 * 负责生成提交token的Action
 * 
 * @author Administrator
 * 
 */
public class ApiSubmitTokenAction extends BaseApiAction {

	private static final long serialVersionUID = 3812103987186192091L;
	private static Logger logger = Logger.getLogger(ApiSubmitTokenAction.class);
	
	public String getToken() {
		logger.info("api.ApiSubmitTokenAction::requestSubmitToken.");
		// 生成token
		String token = generateToken();
		// 把token放入当前session中
		setSessionToken(token);
		// 设置返回结果
		TokenResponse response = new TokenResponse();
		response.setCode(TokenResponse.CODE_SUCCESS);
		response.setToken(token);
		renderResult(response);
		return SUCCESS;
	}

	private String generateToken() {
		return ZisUtils.getDateString("yyyyMMddHHmmss") + new Random().nextInt(100);
	}
}
