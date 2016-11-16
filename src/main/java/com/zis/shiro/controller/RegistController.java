package com.zis.shiro.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.shiro.dto.RegistDto;
import com.zis.shiro.service.RegistService;
import com.zis.shiro.util.ActionHelpUtil;

@Controller
@RequestMapping(value = "/shiro")
public class RegistController extends ActionHelpUtil {
	
	private Logger logger = Logger.getLogger(RegistController.class);

	@Autowired
	private RegistService registService;

	/**
	 * 新建用户 controller
	 * @param registDto
	 * @param br
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/registUser")
	public String registUser(@Valid @ModelAttribute("registDto") RegistDto registDto, BindingResult br, ModelMap map) {
		map.put("checkedIds", registDto.getPermissionIds());
		showPermissionAllList(registDto, map);
		if (br.hasErrors()) {
			return "shiro/regist";
		}
		String password = registDto.getPassword();
		String passwordAgain = registDto.getPasswordAgain();
		if (!password.equals(passwordAgain)) {
			map.put("passwordError", "两次密码不一致");
			return "shiro/regist";
		}
		String actionMessage = "" ;
		try{
			actionMessage = this.registService.registSave(registDto);
		}catch (Exception e) {
			map.put("errorAction", e.getMessage());
			logger.error("操作失败:"+e.getMessage(),e);
			return "shiro/regist";
		}
		map.put("actionMessage", actionMessage);
		Integer[] ids = (Integer[]) registDto.getPermissionIds().toArray(
				new Integer[registDto.getPermissionIds().size()]);
		showPermissionAllList(registDto, map, ids);
		return "shiro/regist-success";
	}
}
