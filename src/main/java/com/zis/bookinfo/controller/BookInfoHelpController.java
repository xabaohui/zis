package com.zis.bookinfo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/bookInfo")
public class BookInfoHelpController {
	
	@RequiresPermissions(value = "bookInfo:gotoAddBook")
	@RequestMapping(value = "gotoAddBook")
	public String gotoAddBook() {
		return "bookinfo/addBook";
	}
	
	@RequiresPermissions(value = "bookInfo:gotoAddYouLuData")
	@RequestMapping(value = "gotoAddYouLuData")
	public String gotoAddYouLuData() {
		return "bookinfo/addYouLuData";
	}

}
