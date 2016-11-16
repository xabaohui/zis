package com.zis.shiro.service;

import java.util.List;

import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;
import com.zis.shiro.repository.UserDao;


@Service
public class SysService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PermissionDao permissionDao;

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
}
