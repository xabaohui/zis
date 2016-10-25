package com.zis.toolkit.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateSysVarDTO {

	@NotBlank(message = "常量值不能为空")
	private Integer depValue;
	private String depKey;

	public Integer getDepValue() {
		return depValue;
	}

	public void setDepValue(Integer depValue) {
		this.depValue = depValue;
	}

	public String getDepKey() {
		return depKey;
	}

	public void setDepKey(String depKey) {
		this.depKey = depKey;
	}
}
