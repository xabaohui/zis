package com.zis.shop.api.impl;

import java.util.Map;

import com.zis.shop.api.ApiTransfer;

/**
 * 平台Api调用工厂
 * @author think
 *
 */
public class ApiTransferFactory {
	private Map<String, ApiTransfer> apiMap;

	/**
	 * 获取根据平台名称获取的Api调用类
	 * @param type
	 * @return
	 */
	public ApiTransfer getInstance(String type) {
		return apiMap.get(type);
	}

	public void setApiMap(Map<String, ApiTransfer> apiMap) {
		this.apiMap = apiMap;
	}
}
