package com.zis.shiro.service;

import java.util.List;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;
import com.zis.shiro.repository.UserDao;
import com.zis.shop.bean.Company;
import com.zis.shop.repository.CompanyDao;


@Service
public class SysService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private RoleDao roleDao;

	public User findSysUserByUserName(String userName) {
		List<User> list = this.userDao.findByUserName(userName);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new LockedAccountException("账号已锁定，原因为账号重复");
		}
		if (list.get(0).getIsDelete().equals("yes")) {
			throw new DisabledAccountException("账号已锁定，原因账号已删除");
		}
		return list.get(0);
	}

	public List<Permission> findPermissionByUserId(Integer userId) {
		List<Permission> list = this.permissionDao.findPermissionByUserId(userId);
		return list;
	}

	public List<Role> findRoleByUserId(Integer userId) {
		List<Role> list = this.roleDao.findRoleByUserId(userId);
		return list;
	}
	
	public Company findCompanyByCompanyId(Integer companyId) {
		Company company = this.companyDao.findBySysCompanyId(companyId);
		return company;
	}
}
