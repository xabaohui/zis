package com.zis.shiro.dto;

import java.util.List;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;

/**
 * 获取用户信息转换DTO
 * @author think
 *
 */
public class UpdateUserRoleAndPermissions {
	
	private User user;
	private Role role;
	private List<Permission> permissions;
	
	public UpdateUserRoleAndPermissions() {
	}

	public UpdateUserRoleAndPermissions(User user, Role role, List<Permission> permissions) {
		super();
		this.user = user;
		this.role = role;
		this.permissions = permissions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "UpdateUserRoleAndPermissions [user=" + user + ", role=" + role + ", permissions=" + permissions + "]";
	}
}