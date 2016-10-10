package com.zis.requirement.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.xwork2.ActionContext;
import com.zis.common.util.ZisUtils;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.SchoolBiz;

@Controller
@RequestMapping(value = "/requirement")
public class DepartmentInfoAddController {

	private static Logger logger = Logger.getLogger(DepartmentInfoAddController.class);
	@Autowired
	private SchoolBiz schoolBiz;

	// TODO 验证框架
	// @Validations(
	// // 不能为空
	// requiredStrings = {
	// @RequiredStringValidator(fieldName = "college", trim = true, key =
	// "学校不能为空"),
	// @RequiredStringValidator(fieldName = "institute", trim = true, key =
	// "学院不能为空"),
	// @RequiredStringValidator(fieldName = "partName", trim = true, key =
	// "专业不能为空") },
	// requiredFields = { @RequiredFieldValidator(fieldName = "years", key =
	// "学年制不能为空") })
	@RequestMapping(value = "/addSchoolAction")
	public String addSchool(String college, String institute, String partName, Integer years, Integer id) {
		ActionContext ctx = ActionContext.getContext();
		Departmentinfo dmi;
		try {
			if (id != null) {
				dmi = schoolBiz.findDepartmentInfoById(id);
				if (dmi != null) {
					dmi.setGmtModify(ZisUtils.getTS());
					dmi.setCollege(college);
					dmi.setInstitute(institute);
					dmi.setPartName(partName);
					dmi.setYears(years);
					schoolBiz.updateDepartmentInfo(dmi);
					logger.info("requirement.action.AddSchoolAction.addSchool--学院信息已存在,修改学年制成功");
					ctx.put("MSG", "操作成功");
					return "redirect:/requirement/updateSchoolPre";
				} else {
					return "error";
				}
			} else {
				dmi = new Departmentinfo();
				dmi.setCollege(college);
				dmi.setPartName(partName);
				dmi.setInstitute(institute);
				dmi.setYears(years);
				dmi.setGmtModify(ZisUtils.getTS());
				dmi.setGmtCreate(ZisUtils.getTS());
				try {
					schoolBiz.addDepartmentInfo(dmi);
				} catch (Exception e) {
					logger.error("院校信息已重复", e);
					// TODO 验证框架
					// this.addFieldError("ERROR", "院校信息已重复");
					return "error";
				}
				ctx.put("MSG", "操作成功");
				return "redirect:/requirement/updateSchoolPre";
			}
		} catch (Exception e) {
			logger.error("系统错误", e);
			throw new RuntimeException("系统错误");
		}
	}
}
