package com.zis.shiro.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.RolePermission;
import com.zis.shiro.bean.User;
import com.zis.shiro.dto.ActiveUser;
import com.zis.shiro.dto.CreatePermissionDto;
import com.zis.shiro.dto.GeneralUserPasswordUpdateDTO;
import com.zis.shiro.dto.RegistRoleDto;
import com.zis.shiro.dto.RegistUserDto;
import com.zis.shiro.dto.UpdateUserInfo;
import com.zis.shiro.realm.CustomRealm;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;
import com.zis.shiro.repository.RolePermissionDao;
import com.zis.shiro.repository.UserDao;
import com.zis.shop.bean.Company;
import com.zis.shop.repository.CompanyDao;

/**
 * @author think
 * 
 */
@Service
public class RegistAndUpdateService {

	private Logger logger = Logger.getLogger(RegistAndUpdateService.class);

	// 用户是否删除
	protected final String NO = "no";
	protected final String YES = "yes";
	protected final String NORMAL = "normal";
	protected final String DELETE = "delete";
	// 权限分组
	protected final String BOOK_INFO = "bookInfo";
	protected final String PURCHASE = "purchase";
	protected final String REQUIREMENT = "requirement";
	protected final String TOOLKIT = "toolkit";
	protected final String SHIRO = "shiro";
	protected final String STOCK = "stock";
	protected final String DATA = "data";

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;
	@Autowired
	private CompanyDao companyDao;

	// 获取分组所有的权限MAP
	private HashMap<String, List<Permission>> permissions;

	/**
	 * 获取分组权限
	 * 
	 * @param groupName
	 * @return
	 */
	public List<Permission> getGroupPermissions(String groupName) {
		if (permissions == null) {
			findAllPermission();
		}
		List<Permission> list = permissions.get(groupName);
		return list;
	}

	/**
	 * save权限，注入后删除
	 * 
	 * @param createPermissionDto
	 * @return
	 */
	public boolean savePermission(CreatePermissionDto createPermissionDto) {
		List<Permission> permissionCodeList = this.permissionDao.findByPermissionCode(createPermissionDto
				.getPermissionCode());
		List<Permission> urlList = this.permissionDao.findByUrl(createPermissionDto.getUrl());
		if (permissionCodeList.size() > 0) {
			throw new RuntimeException("权限Code重复");
		}
		if (urlList.size() > 0) {
			throw new RuntimeException("Url重复");
		}
		Permission p = new Permission();
		p.setPermissionCode(createPermissionDto.getPermissionCode());
		p.setGroupName(createPermissionDto.getGroupName());
		p.setPermissionName(createPermissionDto.getPermissionName());
		p.setPermissionDescription(createPermissionDto.getPermissionDescription());
		p.setUrl(createPermissionDto.getUrl());
		p.setCreateTime(new Date());
		p.setUpdateTime(new Date());
		this.permissionDao.save(p);
		return true;
	}

	/**
	 * 新建和修改角色，及授权
	 * 
	 * @param registRoleDto
	 * @return
	 */
	@Transactional
	public String registOrUpdateRole(RegistRoleDto registRoleDto) {

		// 新建角色
		Role role = null;
		if (registRoleDto.getId() != null) {
			role = this.roleDao.findRoleByRoleId(registRoleDto.getId());
		}
		if (role == null) {
			role = new Role();
			role.setRoleCode(registRoleDto.getRoleCode());
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			role.setCreateUserName(getCreateUserName());
			role.setRoleName(registRoleDto.getRoleName());
			role.setRoleDescription(registRoleDto.getRoleDescription());
		} else {
			role.setUpdateTime(new Date());
			role.setCreateUserName(getCreateUserName());
			role.setRoleName(registRoleDto.getRoleName());
			role.setRoleDescription(registRoleDto.getRoleDescription());
			// 删除原有角色权限关系
			List<RolePermission> list = this.rolePermissionDao.findByRoleId(role.getId());
			if (!list.isEmpty()) {
				for (RolePermission r : list) {
					r.setUpdateTime(new Date());
					r.setStatus(DELETE);
				}
				this.rolePermissionDao.save(list);

			}
		}
		try {
			this.roleDao.save(role);
		} catch (Exception e) {
			throw new RuntimeException("角色Code重复");
		}

		// 获取选择ID获取的权限
		List<Permission> permissionList = (List<Permission>) this.permissionDao.findAll(registRoleDto
				.getPermissionIds());
		// 判断用户是否拥有传入的权限
		// if (!hasPermission(permissionList)) {
		// throw new UnauthorizedException("权限不足，不允许添加您没有的权限");
		// }
		// 创建角色与权限的关系
		for (Permission p : permissionList) {
			RolePermission rp = new RolePermission();
			rp.setPermissionId(p.getId());
			rp.setRoleId(role.getId());
			rp.setCreateTime(new Date());
			rp.setUpdateTime(new Date());
			rp.setStatus(NORMAL);
			this.rolePermissionDao.save(rp);
		}
		return registRoleDto.getRoleName();
	}

	public List<RolePermission> findRolePermissionByRoleId(Integer roleId) {
		return this.rolePermissionDao.findByRoleId(roleId);
	}

	public Role findRoleByRoleId(Integer roleId) {
		return this.roleDao.findRoleByRoleId(roleId);
	}
	
	public Company findCompanyById(Integer companyId) {
		return this.companyDao.findByCompanyId(companyId);
	}

	/**
	 * 注册用户及修改
	 * 
	 * @param registUserDto
	 * @return
	 */
	@Transactional
	public String registAndUpdateUser(RegistUserDto registUserDto) {

		// 新建用户
		User user = null;
		if (registUserDto.getId() != null) {
			user = this.findOneUser(registUserDto.getId());
		}
		String salt = getSalt();
		if (user == null) {
			user = new User();
			user.setUserName(registUserDto.getUserName());
			user.setRealName(registUserDto.getRealName());
			user.setIsDelete(NO);
			user.setPassword(getPasswordMD5(registUserDto.getPassword(), salt));
			user.setSalt(salt);
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			if (registUserDto.getRoleId() != null) {
				user.setRoleId(registUserDto.getRoleId());
			} else {
				user.setRoleId(0);
			}
			if (registUserDto.getCompanyId() != null) {
				user.setCompanyId(registUserDto.getCompanyId());
			} else {
				user.setCompanyId(0);
			}

		} else {
			// 修改用户
			if (!user.getPassword().equals(registUserDto.getPassword())) {
				user.setPassword(getPasswordMD5(registUserDto.getPassword(), user.getSalt()));
				// 清除缓存
				clearAllCached();
			}
			user.setUserName(registUserDto.getUserName());
			user.setRealName(registUserDto.getRealName());
			user.setIsDelete(NO);
			user.setUpdateTime(new Date());
			if (registUserDto.getRoleId() != null) {
				user.setRoleId(registUserDto.getRoleId());
			} else {
				user.setRoleId(0);
			}
			if (registUserDto.getCompanyId() != null) {
				user.setCompanyId(registUserDto.getCompanyId());
			} else {
				user.setCompanyId(0);
			}
		}
		try {
			this.userDao.save(user);
		} catch (Exception e) {
			throw new RuntimeException("用户名重复");
		}
		return registUserDto.getUserName();
	}

	/**
	 * 删除用户
	 * 
	 * @param userId
	 */
	@Transactional
	public void deleteUser(Integer userId) {
		User user = this.userDao.findOne(userId);
		user.setUpdateTime(new Date());
		user.setIsDelete(YES);
		this.userDao.save(user);
		// 清除缓存
		clearAllCached();
	}

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	@Transactional
	public void deleteRole(Integer roleId) {
		Role role = this.roleDao.findOne(roleId);
		//删除角色时查询所有有权限的用户，将其权限值设置成空
		List<User> list = this.userDao.findByRoleId(roleId);
		for (User u : list) {
			u.setUpdateTime(new Date());
			u.setRoleId(0);
		}
		role.setUpdateTime(new Date());
		role.setStatus(DELETE);
		this.userDao.save(list);
		this.roleDao.save(role);
		// 清除缓存
		clearAllCached();
	}
	
	/**
	 * 公司员工注册 及修改
	 * @param dto
	 */
	public void saveOrUpdateCompanyUser(RegistUserDto dto){
		User user ;
		if(dto.getId()!=null){
			user = this.userDao.findAllUserByCompanyIdAndUserId(dto.getCompanyId(), dto.getId());
			if(user == null){
				throw new RuntimeException("请联系管理员，页面可能被篡改");
			}
			if(!user.getPassword().equals(dto.getPassword())){
				user.setPassword(getPasswordMD5(dto.getPassword(), user.getSalt()));
			}
			user.setRealName(dto.getRealName());
			user.setUpdateTime(new Date());
			this.userDao.save(user);
			clearAllCached();
		}else{
			user = new User();
			String salt = getSalt();
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setCompanyId(dto.getCompanyId());
			user.setIsDelete(NO);
			user.setPassword(getPasswordMD5(dto.getPassword(), salt));
			user.setRealName(dto.getRealName());
			user.setRoleId(0);
			user.setSalt(salt);
			user.setUserName(dto.getUserName());
		}
	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> findAllRole() {
		List<Role> roleList = (List<Role>) this.roleDao.findByIdNotEqZeroAll();
		return roleList;
	}

	/**
	 * 获取所有公司
	 * 
	 * @return
	 */
	public List<Company> findAllCompany() {
		List<Company> companyList = this.companyDao.findAllByStatusIsNormal();
		return companyList;
	}
	
	/**
	 * 根据公司id查找公司下所有员工
	 * @param companyId
	 * @return
	 */
	public List<UpdateUserInfo> findAllUserByCompanyId(Integer companyId){
		List<UpdateUserInfo> list = this.userDao.findAllUserByCompanyId(companyId);
		return list;
	}
	
	/**
	 * 根据id 查询user
	 * 
	 * @param userId
	 * @return
	 */
	public User findOneUser(Integer userId) {
		User user = this.userDao.findUserByUserId(userId);
		return user;
	}

	/**
	 * 根据id 查询Role
	 * 
	 * @param userId
	 * @return
	 */
	public Role findOneRole(Integer roleId) {
		Role role = this.roleDao.findOne(roleId);
		return role;
	}

	/**
	 * 获取用户的角色list
	 * 
	 * @return
	 */
	public Page<UpdateUserInfo> findUserInfo(String userName, String realName, String companyName, Pageable page) {
		if (StringUtil.isBlank(userName)) {
			if (!StringUtil.isBlank(companyName) && !StringUtils.isBlank(realName)) {
				// 查询出来可能为多条记录
				return this.userDao.findUpdateUserInfoByCompanyNameLikeAndRealName(companyName, realName, page);
			} else if (!StringUtils.isBlank(realName)) {
				// 查询出来可能为多条记录
				return this.userDao.findUpdateUserInfoByRealName(realName, page);
			} else if (!StringUtil.isBlank(companyName)) {
				// 查询出来为多条记录
				return this.userDao.findUpdateUserInfoByCompanyNameLike(companyName, page);
			} else {
				// 查询出来为多条记录
				return this.userDao.findUserAllOrderByUserUpdateTimeDesc(page);
			}
		} else {
			// 查询出来为单条记录
			return this.userDao.findUpdateUserInfoByUserName(userName, page);
		}
	}

	/**
	 * 普通用户修改密码
	 * 
	 * @param dto
	 * @return
	 */
	public String generalUserPasswordUpdate(GeneralUserPasswordUpdateDTO dto) {
		List<User> userList = this.userDao.findByUserName(getCreateUserName());
		if (!userList.isEmpty()) {
			User user = userList.get(0);
			String oldPassword = getPasswordMD5(dto.getOldPassword(), user.getSalt());
			if (!user.getPassword().equals(oldPassword)) {
				throw new RuntimeException("密码错误");
			}
			String newPassword = getPasswordMD5(dto.getNewPassword(), user.getSalt());
			user.setPassword(newPassword);
			this.userDao.save(user);
			// 清除缓存
			clearAllCached();
		}
		return "密码修改成功";
	}

	/**
	 * 获取角色list
	 * 
	 * @return
	 */
	public Page<Role> findRoleInfo(String roleName, String roleCode, Pageable page) {
		if (StringUtil.isBlank(roleCode)) {
			if (!StringUtil.isBlank(roleName)) {
				// 查询出来可能为多条记录
				return this.roleDao.findByRoleNameLikeOrderByUpdateTimeDesc(roleName, page);
			} else {
				// 查询出来为多条记录
				return this.roleDao.findAllOrderByUpdateTimeDesc(page);
			}
		} else {
			// 查询出来为单条记录
			return this.roleDao.findByRoleCode(roleCode, page);
		}
	}

	// XXX 后续添加模块需在这里加入 权限列表
	/**
	 * 获取全部权限 注入map
	 * 
	 */
	private void findAllPermission() {
		if (permissions == null) {
			permissions = new HashMap<String, List<Permission>>();
			permissions.put(BOOK_INFO, this.permissionDao.findByGroupName(BOOK_INFO));
			permissions.put(PURCHASE, this.permissionDao.findByGroupName(PURCHASE));
			permissions.put(REQUIREMENT, this.permissionDao.findByGroupName(REQUIREMENT));
			permissions.put(TOOLKIT, this.permissionDao.findByGroupName(TOOLKIT));
			permissions.put(SHIRO, this.permissionDao.findByGroupName(SHIRO));
			permissions.put(STOCK, this.permissionDao.findByGroupName(STOCK));
			permissions.put(DATA, this.permissionDao.findByGroupName(DATA));
		}
	}

	/**
	 * salt 生成器 长度是20-30之间随机String
	 * 
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
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	private String getPasswordMD5(String password, String salt) {
		return new SimpleHash("md5", password, salt).toString();
	}

	/**
	 * 获取创建者账户名称
	 * 
	 * @return
	 */
	private String getCreateUserName() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getUserName();
	}

	/**
	 * 清除缓存，防止修改密码后无法更新密码登陆
	 */
	private void clearAllCached() {
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		CustomRealm userRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		userRealm.clearAllCachedAuthorizationInfo();
		userRealm.clearAllCachedAuthenticationInfo();
	}

	// /**
	// *
	// * 获取分组权限 根据当前用户权限显示信息
	// *
	// * @return
	// */
	// public List<Permission> findByGroupName(String GroupName) {
	// List<Integer> ids = getUserPermissionIds();
	// List<Permission> list =
	// this.permissionDao.findByGroupNameAndIdIn(GroupName, ids);
	// return list;
	// }
	// /**
	// *
	// * 获取分组权限 全部
	// *
	// * @return
	// */
	// public List<Permission> findByGroupName(String groupName) {
	// List<Permission> list = this.permissionDao.findByGroupName(groupName);
	// return list;
	// }

	// /**
	// * 创建用户，及授权
	// *
	// * @param registDto
	// * @return
	// */
	// @Transactional
	// public String registSave(RegistDto registDto) {
	//
	// // 新建角色
	// Role role = new Role();
	// role.setRoleCode(registDto.getRoleCode());
	// role.setCreateTime(new Date());
	// role.setUpdateTime(new Date());
	// role.setCreateUserName(getCreateUserName());
	// role.setRoleName(registDto.getRoleName());
	// role.setRoleDescription(registDto.getRoleDescription());
	// try {
	// this.roleDao.save(role);
	// } catch (Exception e) {
	// throw new RuntimeException("角色Code重复");
	// }
	//
	// // 新建用户
	// User user = new User();
	// String salt = getSalt();
	// user.setUserName(registDto.getUserName());
	// user.setRealName(registDto.getRealName());
	// user.setIsDelete(NO);
	// user.setPassword(getPasswordMD5(registDto.getPassword(), salt));
	// user.setSalt(salt);
	// user.setCreateTime(new Date());
	// user.setUpdateTime(new Date());
	// user.setRoleId(role.getId());
	// try {
	// this.userDao.save(user);
	// } catch (Exception e) {
	// throw new RuntimeException("用户名重复");
	// }
	//
	// // 获取选择ID获取的权限
	// List<Permission> permissionList = (List<Permission>)
	// this.permissionDao.findAll(registDto.getPermissionIds());
	// // 判断用户是否拥有传入的权限
	// // if (!hasPermission(permissionList)) {
	// // throw new UnauthorizedException("权限不足，不允许添加您没有的权限");
	// // }
	// // 创建角色与权限的关系
	// for (Permission p : permissionList) {
	// RolePermission rp = new RolePermission();
	// rp.setPermissionId(p.getId());
	// rp.setRoleId(role.getId());
	// this.RolePermissionDao.save(rp);
	// }
	// return registDto.getUserName() + " 创建成功";
	// }

	// /**
	// * 获取用户向下包含权限的 所有用户
	// *
	// * @param userName
	// * @param realName
	// * @return
	// */
	// public List<UpdateUserRoleAndPermissions>
	// findThisUserContainsPermissionUser(String userName, String realName) {
	// List<UpdateUserRoleAndPermissions> hasPermissionList = new
	// ArrayList<UpdateUserRoleAndPermissions>();
	// List<UpdateUserRoleAndPermissions> list = findUserInfo(userName,
	// realName);
	// if (list.isEmpty()) {
	// return hasPermissionList;
	// }
	// for (UpdateUserRoleAndPermissions u : list) {
	// // 查询有权限 并且删除状态不为NO的用户
	// if (hasPermission(u.getPermissions()) &&
	// u.getUser().getIsDelete().equals(NO)) {
	// hasPermissionList.add(u);
	// }
	// }
	// return hasPermissionList;
	// }
	//
	// /**
	// * 获取所有用户
	// *
	// * @param userName
	// * @param realName
	// * @return
	// */
	// public List<UpdateUserRoleAndPermissions> findAllUser(String userName,
	// String realName) {
	// List<UpdateUserRoleAndPermissions> hasPermissionList = new
	// ArrayList<UpdateUserRoleAndPermissions>();
	// List<UpdateUserRoleAndPermissions> list = findUserInfo(userName,
	// realName);
	// if (list.isEmpty()) {
	// return hasPermissionList;
	// }
	// for (UpdateUserRoleAndPermissions u : list) {
	// // 查询有权限 并且删除状态不为NO的用户
	// if (u.getUser().getIsDelete().equals(NO)) {
	// hasPermissionList.add(u);
	// }
	// }
	// return hasPermissionList;
	// }

	// /**
	// * 将用户信息转换 用户单条记录，角色单条记录，权限多条记录的list
	// *
	// * @return
	// */
	// private List<UpdateUserRoleAndPermissions> findUserInfo(String userName,
	// String realName) {
	// List<UpdateUserRoleAndPermissions> list = new
	// ArrayList<UpdateUserRoleAndPermissions>();
	// List<UpdateUserInfo> DaoList = null;
	// if (StringUtil.isBlank(userName)) {
	// if (!StringUtil.isBlank(realName)) {
	// // 查询出来可能为多条记录
	// DaoList = this.userDao.findUpdateUserInfoByRealName(realName);
	// } else {
	// // 查询出来为多条记录
	// DaoList = this.userDao.findUserAll();
	// }
	// } else {
	// // 查询出来为单条记录
	// DaoList = this.userDao.findUpdateUserInfoByUserName(userName);
	// }
	// // 如果记录为空直接返回
	// if (DaoList.isEmpty()) {
	// return list;
	// }
	// List<Permission> pList = new ArrayList<Permission>();
	// UpdateUserRoleAndPermissions up = new UpdateUserRoleAndPermissions();
	// for (int i = 0; i < DaoList.size(); i++) {
	// if (i == 0) {
	// up.setUser(DaoList.get(i).getUser());
	// up.setRole(DaoList.get(i).getRole());
	// pList.add(DaoList.get(i).getPermission());
	// } else if (i != 0) {
	// Integer upUserId = DaoList.get(i - 1).getUser().getId();
	// Integer thisUserId = DaoList.get(i).getUser().getId();
	// if (upUserId.equals(thisUserId)) {
	// pList.add(DaoList.get(i).getPermission());
	// }
	// if (!upUserId.equals(thisUserId)) {
	// up.setPermissions(pList);
	// list.add(up);
	// up = new UpdateUserRoleAndPermissions();
	// pList = new ArrayList<Permission>();
	// up.setUser(DaoList.get(i).getUser());
	// up.setRole(DaoList.get(i).getRole());
	// pList.add(DaoList.get(i).getPermission());
	// }
	// }
	// if (i == DaoList.size() - 1) {
	// up.setPermissions(pList);
	// list.add(up);
	// }
	// }
	// return list;
	// }

	// /**
	// * 获取当前用户的权限，名称等信息
	// *
	// * @return
	// */
	// private ActiveUser getActiveUser() {
	// Subject my = SecurityUtils.getSubject();
	// ActiveUser au = (ActiveUser) my.getPrincipals().getPrimaryPrincipal();
	// return au;
	// }

	// /**
	// * 获取当前用户所有权限Id
	// *
	// * @return
	// */
	// private List<Integer> getUserPermissionIds() {
	// List<Integer> ids = new ArrayList<Integer>();
	// List<Permission> pList = getActiveUser().getPermissions();
	// for (Permission p : pList) {
	// ids.add(p.getId());
	// }
	// return ids;
	// }

	// /**
	// * 判断用户是否向下兼容拥有权限
	// *
	// * @param permissionList
	// */
	// private boolean hasPermission(List<Permission> permissionList) {
	// // 用户当前权限
	// List<Permission> userPermissionList = getActiveUser().getPermissions();
	// List<Integer> userIdList = new ArrayList<Integer>();
	// for (Permission p : userPermissionList) {
	// userIdList.add(p.getId());
	// }
	// // 传入要添加，新用户的权限id
	// List<Integer> permissionQueryList = new ArrayList<Integer>();
	// for (Permission p : permissionList) {
	// permissionQueryList.add(p.getId());
	// }
	// if (userIdList.containsAll(permissionQueryList)) {
	// return true;
	// }
	// return false;
	// }

}
