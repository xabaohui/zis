package com.zis.purchase.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.zis.common.cache.SysVarCache;

public class SysVarUpdateAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// private DoPurchaseService doPurchaseService;
	private SysVarCache sysVarCache;

	private Integer depValue;
	private String depKey;

	// private Integer depId;

	@Validations(requiredFields = { @RequiredFieldValidator(fieldName = "depValue", key = "常量值不能为空") }, conversionErrorFields = { @ConversionErrorFieldValidator(fieldName = "depValue", key = "常量值只能填数字", shortCircuit = true) })
	/**
	 * 修改系统常量
	 * @return
	 */
	public String updateSysVar() {
		sysVarCache.updateSysVar(depKey, depValue);
		// SysVar sv = doPurchaseService.querySysVarByDepId(depId);
		// sv.setDepValue(depValue);
		// doPurchaseService.updateSysVar(sv);
		return SUCCESS;
	}

	public SysVarCache getSysVarCache() {
		return sysVarCache;
	}

	public void setSysVarCache(SysVarCache sysVarCache) {
		this.sysVarCache = sysVarCache;
	}

	// public DoPurchaseService getDoPurchaseService() {
	// return doPurchaseService;
	// }
	//
	// public void setDoPurchaseService(DoPurchaseService doPurchaseService) {
	// this.doPurchaseService = doPurchaseService;
	// }

	public String getDepKey() {
		return depKey;
	}

	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}

	public Integer getDepValue() {
		return depValue;
	}

	public void setDepValue(Integer depValue) {
		this.depValue = depValue;
	}

	// public Integer getDepId() {
	// return depId;
	// }
	//
	// public void setDepId(Integer depId) {
	// this.depId = depId;
	// }

}
