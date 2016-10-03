package com.zis.purchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.cache.SysVarCache;

@Controller
@RequestMapping(value = "/purchase")
public class SysVarUpdateController {

	@Autowired
	private SysVarCache sysVarCache;

	// TODO 验证框架
	// @Validations(requiredFields = { @RequiredFieldValidator(fieldName =
	// "depValue", key = "常量值不能为空") }, conversionErrorFields = {
	// @ConversionErrorFieldValidator(fieldName = "depValue", key = "常量值只能填数字",
	// shortCircuit = true) })
	/**
	 * 修改系统常量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateSysVarAction")
	public String updateSysVar(Integer depValue, String depKey) {
		sysVarCache.updateSysVar(depKey, depValue);
		return "redirect:/purchase/querySysVarAction";
	}
}
