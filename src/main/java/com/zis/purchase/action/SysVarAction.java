package com.zis.purchase.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.cache.SysVarCache;
import com.zis.requirement.bean.SysVar;

public class SysVarAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private SysVarCache sysVarCache;
	private String depKey;

	/**
	 * 查询所有常量
	 * 
	 * @return
	 */
	public String queryAllSysVar() {
		List<SysVar> list = sysVarCache.getAllSysVars();
		ActionContext.getContext().put("SYSVARLIST", list);
		return SUCCESS;
	}

	/**
	 * 修改前回显
	 * 
	 * @return
	 */
	public String updateSysVarPre() {
		Integer depValue = sysVarCache.getSystemVar(depKey);
		ActionContext ctx = ActionContext.getContext();
		ctx.put("depKey", depKey);
		ctx.put("depValue", depValue);
		return SUCCESS;
	}

	public String getDepKey() {
		return depKey;
	}

	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

}
