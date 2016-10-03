package com.zis.bookinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/bookInfo")
public class BookInfoHelpController {

	@RequestMapping(value = "gotoAddBook")
	public String gotoAddBook() {
		return "bookinfo/addBook";
	}
	@RequestMapping(value="gotoAddYouLuData")
	public String gotoAddYouLuData(){
		return "bookinfo/addYouLuData";
	}

}
