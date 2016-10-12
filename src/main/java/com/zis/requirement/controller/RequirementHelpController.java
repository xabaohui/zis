package com.zis.requirement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/requirement")
public class RequirementHelpController {
	
	@RequestMapping(value="/gotoImportRequirement")
	public String gotoImportRequirement(){
		return "requirement/importRequirement";
	}
	
	@RequestMapping(value="/gotoSuccess")
	public String gotoSuccess(){
		return "success";
	}

}
