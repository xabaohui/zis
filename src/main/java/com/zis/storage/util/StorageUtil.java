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
		return au.getCompanyId();
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
		return au.getStockId();
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
