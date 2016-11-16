package com.zis.shiro.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping(value = "/tologin")
	public String login() {
		return "shiro/login";
	}
	
	@RequestMapping(value = "/goIndex")
	public String Index() {
		return "index";
	}

	/**
	 * 登陆认证
	 * 
	 * @param username
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginAuthc")
	public String login(String username, String password, ModelMap model) {
		String msg = "";
		if (StringUtils.isBlank(username)) {
			model.addAttribute("userNameError", "用户名不能为空");
			return "shiro/login";
		}
		if (StringUtils.isBlank(password)) {
			model.addAttribute("passwordError", "密码不能为空");
			return "shiro/login";
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				return "redirect:/goIndex";
			} else {
				return "shiro/login";
			}
		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误.";
			model.addAttribute("passwordError", msg);
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
			model.addAttribute("message", msg);
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定.";
			model.addAttribute("message", msg);
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用.";
			model.addAttribute("message", msg);
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期. ";
			model.addAttribute("message", msg);
		} catch (UnknownAccountException e) {
			msg = token.getPrincipal() + "帐号不存在. ";
			model.addAttribute("userNameError", msg);
		} 
		return "shiro/login";
	}
}