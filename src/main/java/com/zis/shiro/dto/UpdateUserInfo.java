package com.zis.shiro.dto;

import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;

/**
 * 获取dao用户信息DTO
 * @author think
 *
 */
public class UpdateUserInfo {
	
	private User user;
	private Role role;
	
	public UpdateUserInfo() {
	}
	
	public UpdateUserInfo(User user, Role role) {
		super();
		this.user = user;
		this.role = role;
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

	@Override
	public String toString() {
		return "UpdateUserInfo [user=" + user + ", role=" + role + "]";
	}
}