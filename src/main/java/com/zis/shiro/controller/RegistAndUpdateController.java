package com.zis.shiro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.common.mvc.ext.WebHelper;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.User;
import com.zis.shiro.dto.CreatePermissionDto;
import com.zis.shiro.dto.GeneralUserPasswordUpdateDTO;
import com.zis.shiro.dto.RegistRoleDto;
import com.zis.shiro.dto.RegistUserDto;
import com.zis.shiro.dto.UpdateUserInfo;
import com.zis.shiro.service.RegistAndUpdateService;
import com.zis.shiro.util.ActionHelpUtil;

@Controller
@RequestMapping(value = "/shiro")
public class RegistAndUpdateController extends ActionHelpUtil {

	private Logger logger = Logger.getLogger(RegistAndUpdateController.class);

	@Autowired
	private RegistAndUpdateService registAndUpdateService;

	/**
	 * 新建角色及授权controller
	 * 
	 * @param registDto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/registRole")
	public String registRole(@Valid @ModelAttribute("registRoleDto") RegistRoleDto registRoleDto, BindingResult br,
			ModelMap map) {
		map.put("checkedIds", registRoleDto.getPermissionIds());
		putPermissionToView(map);
		if (br.hasErrors()) {
			return "shiro/regist/regist-role";
		}
		String actionMessage = "";
		try {
			actionMessage = this.registAndUpdateService.registOrUpdateRole(registRoleDto);
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return "shiro/regist/regist-role";
		}
		map.put("actionMessage", actionMessage + " 新建成功");
		return "shiro/update/show-update-role-list";
	}

	/**
	 * 修改角色及授权controller
	 * 
	 * @param registDto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/updateForRole")
	public String updateRole(@Valid @ModelAttribute("registRoleDto") RegistRoleDto registRoleDto, BindingResult br,
			ModelMap map) {
		map.put("role", registRoleDto);
		map.put("checkedIds", registRoleDto.getPermissionIds());
		putPermissionToView(map);
		if (br.hasErrors()) {
			return "shiro/update/alter-role";
		}
		String actionMessage = "";
		try {
			actionMessage = this.registAndUpdateService.registOrUpdateRole(registRoleDto);
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return "shiro/update/alter-role";
		}
		map.put("actionMessage", "[" + actionMessage + "]" + " 修改成功");
		return "shiro/update/show-update-role-list";
	}

	/**
	 * 新建用户 controller
	 * 
	 * @param registUserDto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/regist")
	public String regist(@Valid @ModelAttribute("registUserDto") RegistUserDto registUserDto, BindingResult br,
			ModelMap map) {
		map.put("checkedId", registUserDto.getRoleId());
		map.put("roleList", this.registAndUpdateService.findAllRole());
		if (br.hasErrors()) {
			return "shiro/regist/regist-user";
		}
		String password = registUserDto.getPassword();
		String passwordAgain = registUserDto.getPasswordAgain();
		if (!password.equals(passwordAgain)) {
			map.put("passwordError", "两次密码不一致");
			return "shiro/regist/regist-user";
		}
		String actionMessage = "";
		try {
			actionMessage = this.registAndUpdateService.registAndUpdateUser(registUserDto);
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return "shiro/regist/regist-user";
		}
		map.put("actionMessage", actionMessage + " 创建成功");
		map.put("roleName", registUserDto.getRoleName());
		return "shiro/regist/regist-user-success";
	}

	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/deleteUser")
	public String deleteUser(Integer id, ModelMap map) {
		if (id == null) {
			map.put("errorAction", "userId为空");
			logger.error("userId删除时 为空");
			return "error";
		}
		User user = this.registAndUpdateService.findOneUser(id);
		map.put("actionMessage", user.getUserName() + " 删除成功");
		map.put("user", user);
		this.registAndUpdateService.deleteUser(id);
		return "shiro/delete/delete-user-success";
	}

	// TODO 测试用
	/**
	 * 创建权限的controller
	 * 
	 * @return
	 */
	@RequiresPermissions(value = { "xxxx:createPermission" })
	@RequestMapping(value = "/createPermission")
	public String createPermission(
			@Valid @ModelAttribute("createPermissionDto") CreatePermissionDto createPermissionDto, BindingResult br,
			ModelMap map) {
		map.put("groupNames", groupNames());
		if (br.hasErrors()) {
			return "shiro/createPermission";
		}
		try {
			this.registAndUpdateService.savePermission(createPermissionDto);
		} catch (RuntimeException e) {
			map.put("errorAction", e.getMessage());
			return "shiro/createPermission";
		}
		map.put("createPermissionDto", createPermissionDto);
		return "shiro/success-createPermission";
	}

	/**
	 * 查询用户 controller
	 * 
	 * @param registDto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/updateWaitUser")
	public String updateWaitUser(String userName, String realName, ModelMap map, HttpServletRequest request) {
		Pageable page = WebHelper.buildPageRequest(request);
		Page<UpdateUserInfo> userList = this.registAndUpdateService.findUserInfo(userName, realName, page);
		if (!userList.getContent().isEmpty()) {
			List<UpdateUserInfo> list = userList.getContent();
			map.put("userList", list);
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage("", "", userName, realName, map);
			if (userList.hasPrevious()) {
				map.put("prePage", page.previousOrFirst().getPageNumber());
			}
			if (userList.hasNext()) {
				map.put("nextPage", page.next().getPageNumber());
			}
			return "shiro/update/show_update_list";
		}
		map.put("notResult", "未找到结果,您输入的用户名称或者使用者姓名不在服务区");
		return "shiro/update/show_update_list";
	}

	/**
	 * 查询角色 controller
	 * 
	 * @param roleName
	 * @param roleCode
	 * @param map
	 * @param request
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/updateWaitRole")
	public String updateWaitRole(String roleName, String roleCode, ModelMap map, HttpServletRequest request) {
		Pageable page = WebHelper.buildPageRequest(request);
		Page<Role> roleList = this.registAndUpdateService.findRoleInfo(roleName, roleCode, page);
		if (!roleList.getContent().isEmpty()) {
			map.put("roleList", roleList.getContent());
			map.put("page", page.getPageNumber() + 1);
			setQueryConditionToPage(roleName, roleCode, "", "", map);
			if (roleList.hasPrevious()) {
				map.put("prePage", page.previousOrFirst().getPageNumber());
			}
			if (roleList.hasNext()) {
				map.put("nextPage", page.next().getPageNumber());
			}
			return "shiro/update/show-update-role-list";
		}
		map.put("notResult", "未找到结果,您输入的角色或者角色CODE不在服务区");
		return "shiro/update/show-update-role-list";
	}

	/**
	 * 修改用户 controller
	 * 
	 * @param registDto
	 * @param br
	 * @param map
	 * @param id
	 * @return
	 */
	@RequiresPermissions(value = { "shiro:shiro" })
	@RequestMapping(value = "/updateForUser")
	public String updateForUser(@Valid @ModelAttribute("registUserDto") RegistUserDto registUserDto, BindingResult br,
			ModelMap map) {
		map.put("checkedId", registUserDto.getRoleId());
		map.put("user", registUserDto);
		map.put("roleList", this.registAndUpdateService.findAllRole());
		if (br.hasErrors()) {
			return "shiro/update/alter-user";
		}
		String password = registUserDto.getPassword();
		String passwordAgain = registUserDto.getPasswordAgain();
		if (!password.equals(passwordAgain)) {
			map.put("passwordError", "两次密码不一致");
			return "shiro/update/alter-user";
		}
		String actionMessage = "";
		try {
			actionMessage = this.registAndUpdateService.registAndUpdateUser(registUserDto);
		} catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error("操作失败:" + e.getMessage(), e);
			return "shiro/update/alter-user";
		}
		map.put("actionMessage", actionMessage + "修改成功");
		map.put("roleName", registUserDto.getRoleName());
		return "shiro/update/alter-user-success";
	}

	/**
	 * 普通用户修改密码
	 * 
	 * @param generalUserPasswordUpdateDTO
	 * @param br
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/updateGeneralUserPassword")
	public String updateGeneralUserPassword(
			@Valid @ModelAttribute("generalUserPasswordUpdateDTO") GeneralUserPasswordUpdateDTO generalUserPasswordUpdateDTO,
			BindingResult br, ModelMap map) {
		if (br.hasErrors()) {
			return "shiro/update/alter-general-user-password";
		}
		String newPassword = generalUserPasswordUpdateDTO.getNewPassword();
		String newPasswordAgain = generalUserPasswordUpdateDTO.getNewPasswordAgain();
		if (!newPassword.equals(newPasswordAgain)) {
			map.put("passwordError", "两次密码不一致");
			return "shiro/update/alter-general-user-password";
		}
		String actionMessage = "";
		try {
			actionMessage = this.registAndUpdateService.generalUserPasswordUpdate(generalUserPasswordUpdateDTO);
		} catch (RuntimeException e) {
			map.put("oldPasswordError", e.getMessage());
			return "shiro/update/alter-general-user-password";
		}
		map.put("actionMessage", actionMessage);
		return "shiro/update/alter-general-user-password";
	}

	private void setQueryConditionToPage(String roleName, String roleCode, String userName, String realName,
			ModelMap map) {
		StringBuilder condition = new StringBuilder();
		if (StringUtils.isNotBlank(roleName)) {
			condition.append("roleName=" + roleName + "&");
		}
		if (StringUtils.isNotBlank(roleCode)) {
			condition.append("roleCode=" + roleCode + "&");
		}
		if (StringUtils.isNotBlank(userName)) {
			condition.append("userName=" + userName + "&");
		}
		if (StringUtils.isNotBlank(realName)) {
			condition.append("realName=" + realName + "&");
		}
		map.put("queryCondition", condition.toString());
	}
}
