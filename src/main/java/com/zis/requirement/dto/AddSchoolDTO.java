package com.zis.requirement.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class AddSchoolDTO {

	@NotBlank(message = "学校不能为空")
	private String college;
	@NotBlank(message = "学院不能为空")
	private String institute;
	@NotBlank(message = "专业不能为空")
	private String partName;
	@NotNull(message = "学年制不能为空")
	private Integer years;

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

}
