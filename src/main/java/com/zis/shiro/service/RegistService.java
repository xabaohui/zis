package com.zis.shiro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.RolePermission;
import com.zis.shiro.bean.User;
import com.zis.shiro.dto.ActiveUser;
import com.zis.shiro.dto.RegistDto;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;
import com.zis.shiro.repository.RolePermissionDao;
import com.zis.shiro.repository.UserDao;

/**
 * @author think
 *
 */
@Service
public class RegistService {

	private Logger logger = Logger.getLogger(RegistService.class);

	// 用户是否删除
	protected final String NO = "no";
	protected final String YES = "YES";

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionDao RolePermissionDao;

	/**
	 * 
	 * 获取分组权限
	 * 
	 * @return
	 */
	public List<Permission> findByGroupName(String GroupName) {
		List<Integer> ids = getUserPermissionIds();
		List<Permission> list = this.permissionDao.findByGroupNameAndIdIn(GroupName, ids);
		return list;
	}

	/**
	 * 创建用户，及授权
	 * 
	 * @param registDto
	 * @return
	 */
	@Transactional
	public String registSave(RegistDto registDto) {

		// 新建角色
		Role role = new Role();
		role.setRoleCode(registDto.getRoleCode());
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setCreateUserName(getCreateUserName());
		role.setRoleName(registDto.getRoleName());
		role.setRoleDescription(registDto.getRoleDescription());

		try {
			this.roleDao.save(role);
		} catch (Exception e) {
			throw new RuntimeException("角色Code重复");
		}

		// 新建用户
		User user = new User();
		String salt = getSalt();
		user.setUserName(registDto.getUserName());
		user.setRealName(registDto.getRoleName());
		user.setIsDelete(NO);
		user.setPassword(getPasswordMD5(registDto.getPassword(), salt));
		user.setSalt(salt);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setRoleId(role.getId());
		try {
			this.userDao.save(user);
		} catch (Exception e) {
			throw new RuntimeException("用户名重复");
		}
		
		// 获取选择ID获取的权限
		List<Permission> permissionList = (List<Permission>) this.permissionDao.findAll(registDto.getPermissionIds());
		// 判断用户是否拥有传入的权限
		hasPermission(permissionList);
		// 创建角色与权限的关系
		for (Permission p : permissionList) {
			RolePermission rp = new RolePermission();
			rp.setPermissionId(p.getId());
			rp.setRoleId(role.getId());
			this.RolePermissionDao.save(rp);
		}
		return registDto.getUserName() + " 创建成功";
	}

	/**
	 * salt 生成器 长度是20-30之间随机String
	 * @return
	 */
	private String getSalt() {
		Integer length = new Random().nextInt(10) + 20;
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 密码加密
	 * @param password
	 * @param salt
	 * @return
	 */
	private String getPasswordMD5(String password, String salt) {
		return new SimpleHash("md5", password, salt).toString();
	}

	/**
	 * 获取当前用户的权限，名称等信息
	 * @return
	 */
	private ActiveUser getActiveUser() {
		Subject my = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) my.getPrincipals().getPrimaryPrincipal();
		return au;
	}

	/**
	 *  获取创建者账户名称
	 * @return
	 */
	protected String getCreateUserName() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getUserName();
	}

	/**
	 * 获取当前用户所有权限Id
	 * @return
	 */
	private List<Integer> getUserPermissionIds() {
		List<Integer> ids = new ArrayList<Integer>();
		List<Permission> pList = getActiveUser().getPermissions();
		for (Permission p : pList) {
			ids.add(p.getId());
		}
		return ids;
	}

	/**
	 * 判断用户是否拥有权限
	 * @param permissionList
	 */
	private void hasPermission(List<Permission> permissionList) {
		// 用户当前权限
		List<Permission> userPermissionList = getActiveUser().getPermissions();
		List<Integer> userIdList = new ArrayList<Integer>();
		for (Permission p : userPermissionList) {
			userIdList.add(p.getId());
		}
		// 传入要添加，新用户的权限id
		List<Integer> permissionQueryList = new ArrayList<Integer>();
		for (Permission p : permissionList) {
			permissionQueryList.add(p.getId());
		}
		if (!userIdList.containsAll(permissionQueryList)) {
			throw new UnauthorizedException("权限不足，不允许添加您没有的权限");
		}
	}
}
