package com.zis.toolkit.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/toolkit")
public class ToolkitHelpController {
	
	@RequiresPermissions(value = { "toolkit:toolkit" })
	@RequestMapping(value="/gotoToolkit")
	public String gotoToolkit(){
		return "toolkit/toolkit";
	}
	
	@RequiresPermissions(value = { "toolkit:toolkit" })
	@RequestMapping(value="/gotoStockPosCheck")
	public String gotoStockPosCheck(){
		return "toolkit/stockPosCheck";
	}

}
