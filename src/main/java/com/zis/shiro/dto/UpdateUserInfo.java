package com.zis.shiro.dto;

import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;
import com.zis.shop.bean.Company;

/**
 * 获取dao用户信息DTO
 * 
 * @author think
 * 
 */
public class UpdateUserInfo {

	private User user;
	private Role role;
	private Company company;

	public UpdateUserInfo() {
	}

	public UpdateUserInfo(User user, Role role, Company company) {
		this.user = user;
		this.role = role;
		this.company = company;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "UpdateUserInfo [user=" + user + ", role=" + role + ", company=" + company + "]";
	}

}