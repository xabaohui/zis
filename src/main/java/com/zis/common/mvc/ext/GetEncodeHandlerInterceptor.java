package com.zis.common.mvc.ext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Get请求参数转码拦截器
 * 
 * @author lvbin
 * 
 */
public class GetEncodeHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final String METHOD_GET = "GET";
	private static final String CHARSET_ISO = "ISO-8859-1";
	private static final String CHARSET_UTF8 = "UTF-8";
	// private static final String HEADER = "x-requested-with";
	// private static final String AJAX_HEADER = "XMLHttpRequest";

	private static final Logger logger = LoggerFactory.getLogger(GetEncodeHandlerInterceptor.class);

	/**
	 * Get请求转码，POST请求直接跳过
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (METHOD_GET.equals(request.getMethod())) {
			handleParametersEncoding(request);
//			handleParametersEncodingV2(request);
		}
		// if (request.getHeader(HEADER) != null &&
		// request.getHeader(HEADER).equalsIgnoreCase(AJAX_HEADER)) {
		// handleParametersEncoding(request);
		// }
		return true;
	}

	/**
	 * 请求参数转码，ISO-8859-1 => UTF-8
	 * 
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void handleParametersEncoding(HttpServletRequest request) {
		Map<String, Object> map = request.getParameterMap();
		for (String key : map.keySet()) {
			Object val = map.get(key);
			if (val instanceof String[]) {
				String[] vals = (String[]) val;
				for (int i = 0; i < vals.length; i++) {
					try {
						vals[i] = new String(((String) vals[i]).getBytes(CHARSET_ISO), CHARSET_UTF8);
					} catch (UnsupportedEncodingException e) {
						// 转码失败，不作任何处理
						logger.error("encoding failed, parse '" + key + "' to UTF8", e);
					}
				}
			}
		}
	}

//	private void handleParametersEncodingV2(HttpServletRequest request) {
//		while (request.getParameterMap().values().iterator().hasNext()) {
//				buildMapValue((String[]) request.getParameterMap().values().iterator().next());
//		}
//	}
//
//	private void buildMapValue(String[] value) {
//		for (String string : value) {
//
//			try {
//				string = new String(string.getBytes(CHARSET_ISO), CHARSET_UTF8);
//			} catch (UnsupportedEncodingException e) {
//				// 转码失败，不作任何处理
//				logger.error("encoding failed, parse '" + string + "' to UTF8", e);
//			}
//		}
//	}
}
