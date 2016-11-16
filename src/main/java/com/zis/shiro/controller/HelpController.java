package com.zis.shiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.service.RegistService;
import com.zis.shiro.util.ActionHelpUtil;

@Controller
@RequestMapping(value = "/shiro")
public class HelpController extends ActionHelpUtil {

	@Autowired
	private RegistService registService;

	@RequestMapping(value = "/showRegist")
	public String showRegist(ModelMap map) {
		//TODO 此处要加查询权限后放入list 在前台显示 需要新的模块后需要加入ConstanUtil 添加新的常量 此处要根据查询出来当前用户所有的权限放入权限
		List<Permission> registList = this.registService.findByGroupName(REGIST);
		List<Permission> bookInfoList = this.registService.findByGroupName(BOOK_INFO);
		List<Permission> purchaseList = this.registService.findByGroupName(PURCHASE);
		List<Permission> requirementList = this.registService.findByGroupName(REQUIREMENT);
		List<Permission> toolkitList = this.registService.findByGroupName(TOOLKIT);
		map.put("registList", registList);
		map.put("bookInfoList", bookInfoList);
		map.put("purchaseList", purchaseList);
		map.put("requirementList", requirementList);
		map.put("toolkitList", toolkitList);
		return "shiro/regist";
	}
}
