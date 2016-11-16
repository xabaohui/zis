package com.zis.bookinfo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiresPermissions(value = "ssss/ssssss")
@RequestMapping(value = "/bookInfo")
public class BookInfoHelpController {

	@RequestMapping(value = "gotoAddBook")
	public String gotoAddBook() {
		return "bookinfo/addBook";
	}

	@RequestMapping(value = "gotoAddYouLuData")
	public String gotoAddYouLuData() {
		return "bookinfo/addYouLuData";
	}

}
