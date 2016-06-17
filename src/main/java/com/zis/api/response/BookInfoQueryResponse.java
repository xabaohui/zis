/**
 * 
 */
package com.zis.api.response;

import java.util.List;

/**
 * @author lvbin 2015-10-10
 */
public class BookInfoQueryResponse extends BaseApiResponse {

	private List<BookInfoQueryData> resultList;

	public List<BookInfoQueryData> getResultList() {
		return resultList;
	}

	public void setResultList(List<BookInfoQueryData> resultList) {
		this.resultList = resultList;
	}

}
