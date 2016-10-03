package com.zis.purchase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.cache.SysVarCache;
import com.zis.requirement.bean.SysVar;

@Controller
@RequestMapping(value="/purchase")
public class SysVarController {
	
	@Autowired
	private SysVarCache sysVarCache;
//	private String depKey;

	/**
	 * 查询所有常量
	 * 
	 * @return
	 */
	@RequestMapping(value="/querySysVarAction")
	public String queryAllSysVar(ModelMap context) {
		List<SysVar> list = sysVarCache.getAllSysVars();
		context.put("SYSVARLIST", list);
		return "purchase/querySysVar";
	}

	/**
	 * 修改前回显
	 * 
	 * @return
	 */
	@RequestMapping(value="/updateSysVarPreAction")
	public String updateSysVarPre(ModelMap context,String depKey) {
		Integer depValue = sysVarCache.getSystemVar(depKey);
		context.put("depKey", depKey);
		context.put("depValue", depValue);
		return "purchase/updateSysVar";
	}
}
