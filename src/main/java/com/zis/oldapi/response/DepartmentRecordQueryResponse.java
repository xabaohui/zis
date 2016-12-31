package com.zis.oldapi.response;

import java.util.Map;

/**
 * 院校信息查询接口返回DTO（新的）
 * 
 * @author Administrator
 * 
 */
public class DepartmentRecordQueryResponse extends BaseApiResponse {

	private Map<String, Integer> resultList;

	public Map<String, Integer> getResultList() {
		return resultList;
	}

	public void setResultList(Map<String, Integer> resultList) {
		this.resultList = resultList;
	}

}