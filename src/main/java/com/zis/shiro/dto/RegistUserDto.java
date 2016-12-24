package com.zis.shiro.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 新建用户信息DTO用于验证
 * @author think
 *
 */
public class RegistUserDto {
	
	private Integer id;
	
	@NotBlank(message = "用户名不能为空")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "用户名只能为数字与字母的组合")
	private String userName;

	@NotBlank(message = "使用者名称不能为空")
	private String realName;

	@NotBlank(message = "密码不能为空")
	private String password;

	@NotBlank(message = "密码不能为空")
	private String passwordAgain;
	
	private Integer roleId;
	
	private String roleName;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "RegistUserDto [id=" + id + ", userName=" + userName + ", realName=" + realName + ", password="
				+ password + ", passwordAgain=" + passwordAgain + ", roleId=" + roleId + ", roleName=" + roleName + "]";
	}
}
