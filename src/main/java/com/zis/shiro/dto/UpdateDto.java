package com.zis.shiro.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 新建用户信息DTO用于验证
 * @author think
 *
 */
public class UpdateDto extends RegistDto{
	
	@NotBlank(message="删除选项不能为空")
	private String isDelete;
	
	@NotBlank(message="删除选项不能为空")
	private String roleId;
	
	
	
	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "UpdateDto [isDelete=" + isDelete + ", roleId=" + roleId + "]";
	}
}
