package com.zis.requirement.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zis.requirement.bean.Departmentinfo;
import com.zis.requirement.biz.SchoolBiz;

/**
 * 录入教材使用量前置Action
 * 准备院校信息，用于页面展示
 * 录入教材使用量时回显院校信息
 * @author lenovo
 *
 */
public class BookAmountAddPreAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	private SchoolBiz schoolBiz;
	private Integer id;

	public String amountPre(){
		if (id == null){
			return INPUT;
		}else {
			Departmentinfo di = schoolBiz.findDepartmentInfoById(id);
			if (di == null){
				return INPUT;
			}
			ActionContext ctx = ActionContext.getContext();
			ctx.put("did", id);
			ctx.put("college", di.getCollege());
			ctx.put("institute", di.getInstitute());
			ctx.put("partName", di.getPartName());
			ctx.put("grade", di.getYears());
			return SUCCESS;
		}
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SchoolBiz getSchoolBiz() {
		return schoolBiz;
	}

	public void setSchoolBiz(SchoolBiz schoolBiz) {
		this.schoolBiz = schoolBiz;
	}
	
}
