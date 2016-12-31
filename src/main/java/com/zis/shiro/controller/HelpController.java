package com.zis.shiro.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.RolePermission;
import com.zis.shiro.bean.User;
import com.zis.shiro.dto.RegistRoleDto;
import com.zis.shiro.dto.RegistUserDto;
import com.zis.shiro.service.RegistAndUpdateService;
import com.zis.shiro.util.ActionHelpUtil;

@Controller
@RequestMapping(value = "/shiro")
public class HelpController extends ActionHelpUtil {

	private Logger logger = Logger.getLogger(HelpController.class);

	@Autowired
	private RegistAndUpdateService registAndUpdateService;
	
	@RequestMapping(value = "/gotoGeneralUserUpdatePassword")
	public String gotoGeneralUserUpdatePassword() {
		return "shiro/update/alter-general-user-password";
	}
	
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/gotoRegistRole")
	public String showRegistRole(ModelMap map) {
		map.put("registList", this.registAndUpdateService.getGroupPermissions(SHIRO));
		map.put("purchaseList", this.registAndUpdateService.getGroupPermissions(PURCHASE));
		map.put("requirementList", this.registAndUpdateService.getGroupPermissions(REQUIREMENT));
		map.put("bookInfoList", this.registAndUpdateService.getGroupPermissions(BOOK_INFO));
		map.put("toolkitList", this.registAndUpdateService.getGroupPermissions(TOOLKIT));
		map.put("stockList", this.registAndUpdateService.getGroupPermissions(STOCK));
		map.put("dataList", this.registAndUpdateService.getGroupPermissions(DATA));
		return "shiro/regist/regist-role";
	}
	
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/gotoRegistUser")
	public String gotoRegistUser(ModelMap map) {
		List<Role> roleList = this.registAndUpdateService.findAllRole();
		map.put("roleList", roleList);
		return "shiro/regist/regist-user";
	}
	
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/showUpdate")
	public String showUpdate(ModelMap map) {
		return "shiro/update/show_update_list";
	}
	
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/showUpdateRole")
	public String showUpdateRole(ModelMap map) {
		return "shiro/update/show-update-role-list";
	}

	/**
	 * 修改用户 帮助action
	 * 
	 * @param registUserDto
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/updateUser")
	public String updateUser(RegistUserDto registUserDto, ModelMap map) {
		if (registUserDto.getId() == null) {
			map.put("actionError", "修改用户ID异常,请联系管理员");
			logger.error("修改用户ID异常,请联系管理员 " + registUserDto.getId());
			return "error";
		}

		// 如果用户名为空则查询
		if (StringUtils.isBlank(registUserDto.getUserName())) {
			User user = this.registAndUpdateService.findOneUser(registUserDto.getId());
			if (user == null) {
				map.put("actionError", "用户ID非法使用");
				return "error";
			}
			registUserDto.setUserName(user.getUserName());
			registUserDto.setPasswordAgain(user.getPassword());
			registUserDto.setPassword(user.getPassword());
			registUserDto.setRoleId(user.getRoleId());
			registUserDto.setRealName(user.getRealName());
		}
		map.put("checkedId", registUserDto.getRoleId());
		map.put("user", registUserDto);
		map.put("roleList", this.registAndUpdateService.findAllRole());
		return "shiro/update/alter-user";
	}

	/**
	 * 修改角色 帮助action
	 * 
	 * @param registRoleDto
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "updateRole")
	public String updateRole(RegistRoleDto registRoleDto, ModelMap map) {
		if (registRoleDto.getId() == null) {
			map.put("actionError", "修改角色ID异常,请联系管理员");
			logger.error("修改角色ID异常,请联系管理员 " + registRoleDto.getId());
			return "error";
		}
		if (registRoleDto.getPermissionIds() == null) {
			registRoleDto.setPermissionIds(new ArrayList<Integer>());
			List<RolePermission> list = this.registAndUpdateService.findRolePermissionByRoleId(registRoleDto.getId());
			for (RolePermission rp : list) {
				registRoleDto.getPermissionIds().add(rp.getPermissionId());
			}
		}
		map.put("role", this.registAndUpdateService.findRoleByRoleId(registRoleDto.getId()));
		map.put("checkedIds", registRoleDto.getPermissionIds());
		putPermissionToView(map);
		return "shiro/update/alter-role";
	}
	
	@RequiresPermissions(value = { "xxxxx:gotoCreatePermission" })
	@RequestMapping(value = "/gotoCreatePermission")
	public String gotoCreatePermission(ModelMap map) {
		map.put("groupNames", groupNames());
		return "shiro/createPermission";
	}

	// @RequestMapping(value = "/showRegist")
	// public String showRegist(ModelMap map) {
	// List<Permission> registList =
	// this.registAndUpdateService.getGroupPermissions(REGIST);
	// List<Permission> bookInfoList =
	// this.registAndUpdateService.getGroupPermissions(BOOK_INFO);
	// List<Permission> purchaseList =
	// this.registAndUpdateService.getGroupPermissions(PURCHASE);
	// List<Permission> requirementList =
	// this.registAndUpdateService.getGroupPermissions(REQUIREMENT);
	// List<Permission> toolkitList =
	// this.registAndUpdateService.getGroupPermissions(TOOLKIT);
	// map.put("registList", registList);
	// map.put("bookInfoList", bookInfoList);
	// map.put("purchaseList", purchaseList);
	// map.put("requirementList", requirementList);
	// map.put("toolkitList", toolkitList);
	// return "shiro/regist/regist";
	// }
}
