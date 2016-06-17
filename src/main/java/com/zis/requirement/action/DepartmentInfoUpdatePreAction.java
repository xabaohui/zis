package com.zis.requirement.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.SchoolBiz;

public class DepartmentInfoUpdatePreAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private SchoolBiz schoolBiz;
	private Integer id;

	// 修改院校信息的回显操作
	public String getInfo() {
		if (id == null){
			return SUCCESS;
		}else {
			Departmentinfo dmi = schoolBiz.findDepartmentInfoById(id);
			if (dmi != null){
				ActionContext ctx = ActionContext.getContext();
				ctx.put("id", id);
				ctx.put("college", dmi.getCollege());
				ctx.put("institute", dmi.getInstitute());
				ctx.put("partName", dmi.getPartName());
				ctx.put("years", dmi.getYears());
				return SUCCESS;
			}else {
				this.addActionError("院校信息不存在");
				return INPUT;
			}
		}
	}

	public SchoolBiz getSchoolBiz() {
		return schoolBiz;
	}

	public void setSchoolBiz(SchoolBiz schoolBiz) {
		this.schoolBiz = schoolBiz;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
