package com.zis.shiro.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 新建用户信息DTO用于验证
 * @author think
 *
 */
public class RegistRoleDto extends ShowPermissionDto{
	
	private Integer id;

	@NotBlank(message = "角色名不能为空")
	private String roleName;

	@NotBlank(message = "角色Code不能为空")
	private String roleCode;
	
	@NotBlank(message = "角色描述不能为空")
	private String roleDescription;

	@NotNull(message = "权限不能为空")
	private List<Integer> permissionIds;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<Integer> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Integer> permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
	public String toString() {
		return "RegistRoleDto [id=" + id + ", roleName=" + roleName + ", roleCode=" + roleCode + ", roleDescription="
				+ roleDescription + ", permissionIds=" + permissionIds + "]";
	}
}
