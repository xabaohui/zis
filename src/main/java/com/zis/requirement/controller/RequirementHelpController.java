package com.zis.requirement.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/requirement")
public class RequirementHelpController {
	
	@RequiresPermissions(value = { "requirement:gotoImportRequirement" })
	@RequestMapping(value="/gotoImportRequirement")
	public String gotoImportRequirement(){
		return "requirement/importRequirement";
	}
}
