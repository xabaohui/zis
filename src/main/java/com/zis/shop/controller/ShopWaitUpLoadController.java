package com.zis.shop.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zis.shiro.dto.ActiveUser;
import com.zis.shop.bean.ShopInfo;
import com.zis.shop.service.ShopService;

@Controller
@RequestMapping(value = "/shop")
public class ShopWaitUpLoadController {
	
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/getShopWaitLoad")
	public String getShopWaitLoad(ModelMap map, Integer shopId){
		if(shopId == null){
//			List<ShopInfo> shopList = this.
		}
		return "";
	}
	/**
	 * 获取用户的公司ID
	 * 
	 * @return
	 */
	private Integer getCompanyId() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getCompanyId();
	}
}
