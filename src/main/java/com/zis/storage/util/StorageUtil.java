package com.zis.storage.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;

import com.zis.shiro.dto.ActiveUser;
import com.zis.shiro.realm.CustomRealm;

public class StorageUtil {

	/**
	 * 获取用户的公司ID
	 * 
	 * @return
	 */
	public static Integer getCompanyId() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		Integer companyId = au.getCompanyId();
		if(companyId == null){
			throw new RuntimeException("您没有公司，请联系管理员添加公司后重新登录");
		}
		return companyId;
	}

	/**
	 * 获取用户名称
	 * 
	 * @return
	 */
	public static String getUserName() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getUserName();
	}
	
	/**
	 * 获取用户真实姓名
	 * 
	 * @return
	 */
	public static String getRealName() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getRealName();
	}

	/**
	 * 获取用户Id
	 * 
	 * @return
	 */
	public static Integer getUserId() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		return au.getUserId();
	}

	/**
	 * 获取仓库Id
	 * 
	 * @return
	 */
	public static Integer getRepoId() {
		Subject user = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) user.getPrincipals().getPrimaryPrincipal();
		Integer stockId = au.getStockId();
		if(stockId == null){
			throw new RuntimeException("您没有公司没有仓库，请联系管理员添加仓库后重新登录");
		}
		return stockId;
	}

	/**
	 * 清除缓存
	 */
	public static void clearAllCached() {
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		CustomRealm userRealm = (CustomRealm) securityManager.getRealms().iterator().next();
		userRealm.clearAllCachedAuthorizationInfo();
		userRealm.clearAllCachedAuthenticationInfo();
	}
}
