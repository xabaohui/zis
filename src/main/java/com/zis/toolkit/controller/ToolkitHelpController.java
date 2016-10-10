package com.zis.toolkit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/toolkit")
public class ToolkitHelpController {
	
	@RequestMapping(value="/gotoToolkit")
	public String gotoToolkit(){
		return "toolkit/toolkit";
	}

}
