package com.zis.purchase.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.common.cache.SysVarCache;
import com.zis.purchase.biz.DoPurchaseService;
import com.zis.requirement.bean.SysVar;

public class SysVarAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DoPurchaseService doPurchaseService;
	private SysVarCache sysVarCache;

	private String depKey;
	private Integer bookId;
	private Integer id;
	private String flag;

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

	/**
	 * 添加黑名单
	 * 
	 * @return
	 */
	public String addBlackList() {
		doPurchaseService.addBlackList(bookId);
		return SUCCESS;
	}

	/**
	 * 添加白名单
	 * 
	 * @return
	 */
	public String addWhiteList() {
		doPurchaseService.addWhiteList(bookId);
		return SUCCESS;
	}

	/**
	 * 删除黑名单或者白名单
	 * 
	 * @return
	 */
	public String deleteWhiteOrBlackList() {
		if (id != null && flag != null) {
			doPurchaseService.deleteBlackOrWhiteList(id, flag);
			return SUCCESS;
		} else {
			return INPUT;
		}
	}

	public DoPurchaseService getDoPurchaseService() {
		return doPurchaseService;
	}

	public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
		this.doPurchaseService = doPurchaseService;
	}

	public String getDepKey() {
		return depKey;
	}

	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

}
