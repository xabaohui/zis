package com.zis.common.mvc.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 页面下拉框控制拦截器
 * 
 * @author think
 * 
 */
//TODO 页面跳转控制待定
@Deprecated
public class HeaderCheckHandlerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		setChecked(request);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		setChecked(request);
		super.postHandle(request, response, handler, modelAndView);
	}

	private void setChecked(HttpServletRequest request) {
		if (request != null) {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute("headerChecked");
			if (obj != null) {
				String str = obj.toString();
				if (str != null) {
					System.out.println(str);
					System.out.println("成功");
					request.setAttribute("headerChecked", str);
				}
			}
		}
	}
}
