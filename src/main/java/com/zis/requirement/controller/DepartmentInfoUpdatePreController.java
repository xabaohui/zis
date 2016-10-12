package com.zis.requirement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.SchoolBiz;

@Controller
@RequestMapping(value = "/requirement")
public class DepartmentInfoUpdatePreController {

	@Autowired
	private SchoolBiz schoolBiz;

	// 修改院校信息的回显操作
	@RequestMapping(value="/updateSchoolPre")
	public String getInfo(Integer id, ModelMap ctx) {
		if (id == null){
			return "requirement/addSchoolInfo";
		}else {
			Departmentinfo dmi = schoolBiz.findDepartmentInfoById(id);
			if (dmi != null){
				ctx.put("id", id);
				ctx.put("college", dmi.getCollege());
				ctx.put("institute", dmi.getInstitute());
				ctx.put("partName", dmi.getPartName());
				ctx.put("years", dmi.getYears());
				return "requirement/addSchoolInfo";
			}else {
				//TODO 验证框架
//				this.addActionError("院校信息不存在");
				return "error";
			}
		}
	}
}