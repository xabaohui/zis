package com.zis.purchase.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseHelpController {

	@RequiresPermissions(value = { "purchase:gotoInWarehouse" })
	@RequestMapping(value = "/gotoInWarehouse")
	public String gotoInWarehouse() {
		return "purchase/inwarehouse";
	}

	@RequiresPermissions(value = { "purchase:gotoTempImportUpload" })
	@RequestMapping(value = "/gotoTempImportUpload")
	public String gotoTempImportUpload() {
		return "purchase/tempImportUpload";
	}

	@RequiresPermissions(value = { "toolkit:gotoSysFunc" })
	@RequestMapping(value = "/gotoSysFunc")
	public String gotoSysFunc() {
		return "purchase/sysFunc";
	}
}
