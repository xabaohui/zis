package com.zis.filter;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ZisRequest extends HttpServletRequestWrapper {
	
	private HttpServletRequest request;

	public ZisRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	@Override
	public String getParameter(String name) {
		try {
			return new String(name.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
	}
//	@Override
//	public String[] (String name) {
//		
//		return super.getParameterValues(name);
//	}
//	@Override
//	public Map getParameterMap() {
//		return super.getParameterMap();
//	}

}
