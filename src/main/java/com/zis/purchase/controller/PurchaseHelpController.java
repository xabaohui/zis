package com.zis.purchase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseHelpController {

	@RequestMapping(value="/gotoInWarehouse")
	public String gotoInWarehouse(){
		return "purchase/inwarehouse";
	}
	@RequestMapping(value="/gotoTempImportUpload")
	public String gotoTempImportUpload(){
		return "purchase/tempImportUpload";
	}
}
