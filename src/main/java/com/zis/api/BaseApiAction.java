package com.zis.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.api.response.BaseApiResponse;

/**
 * API基类 封装了渲染结果的方法
 * 
 * @author Administrator
 * 
 */
public abstract class BaseApiAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BaseApiAction.class);
	public static final String ZIS_TOKEN = "zis.token";

	protected String token;
	
	/**
	 * 检查token是否有效
	 * @return
	 */
	protected String checkToken() {
		return checkToken(false);
	}

	/**
	 * 检查token是否有效
	 * 
	 * @param forceTokenCheck
	 *            是否强制检查token，如果request中没有token，并且forceTokenCheck=false，则认为检查通过（
	 *            用于兼用没有token的版本）
	 * @return
	 */
	protected String checkToken(boolean forceTokenCheck) {
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		logger.info("sessionId = " + sessionId);
		// request中不存在token
		if (StringUtils.isBlank(token)) {
			// 如果强制token检查，则返回false；否则直接返回true（兼容没有token的版本）
			if (forceTokenCheck) {
				return "无效请求，token不能为空";
			} else {
				return StringUtils.EMPTY;
			}
		}
		// session中不存在token
		ActionContext ctx = ActionContext.getContext();
		String sessionToken = (String) ctx.getSession().get(ZIS_TOKEN);
		if (StringUtils.isBlank(sessionToken)) {
			return "操作成功，请勿重复提交";
		}
		// session中token和request中的不一致
		if (!token.equals(sessionToken)) {
			return "无效token";
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 保存token到session中
	 * 
	 * @param sessionToken
	 */
	protected void setSessionToken(String sessionToken) {
		ActionContext ctx = ActionContext.getContext();
		ctx.getSession().put(ZIS_TOKEN, sessionToken);
		String sessionId = ServletActionContext.getRequest().getSession().getId();
		logger.info("sessionId = " + sessionId);
	}

	/**
	 * 移除session中记录的token
	 */
	protected void clearSessionToken() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey(ZIS_TOKEN)) {
			session.remove(ZIS_TOKEN);
		}
	}
	
	protected void renderErrResult(String errMsg) {
		BaseApiResponse response = new BaseApiResponse();
		response.setCode(BaseApiResponse.CODE_INNER_ERROR);
		response.setMsg("系统内部错误：" + errMsg);
		renderResult(response);
	}

	protected void renderResult(Object obj) {
		// json序列化
//		JSONObject jsonObj = JSONObject.fromObject(obj);
//		String content = jsonObj.toString();
		// FIXME FASTJSON
		String content = JSON.toJSONString(obj);
		// 输出结果
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=utf-8");
		try {
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			// 异常处理，异常情况及堆栈信息打印到日志中
			logger.error("渲染API结果出现异常", e);
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
