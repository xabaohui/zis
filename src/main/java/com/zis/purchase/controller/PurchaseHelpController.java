package com.zis.purchase.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseHelpController {

	@RequiresPermissions(value = { "stock:input" })
	@RequestMapping(value = "/gotoInWarehouse")
	public String gotoInWarehouse() {
		return "purchase/inwarehouse";
	}

	@RequiresPermissions(value = { "data:dataInfo" })
	@RequestMapping(value = "/gotoTempImportUpload")
	public String gotoTempImportUpload() {
		return "purchase/tempImportUpload";
	}

	@RequiresPermissions(value = { "toolkit:toolkit" })
	@RequestMapping(value = "/gotoSysFunc")
	public String gotoSysFunc() {
		return "purchase/sysFunc";
	}
}
