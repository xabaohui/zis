package com.zis.shiro.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.dto.ActiveUser;
import com.zis.shiro.service.RegistAndUpdateService;

@Controller
public class ActionHelpUtil {

	// 权限分组
	protected final String BOOK_INFO = "bookInfo";
	protected final String PURCHASE = "purchase";
	protected final String REQUIREMENT = "requirement";
	protected final String TOOLKIT = "toolkit";
	protected final String SHIRO = "shiro";
	protected final String STOCK = "stock";
	protected final String DATA = "data";

	@Autowired
	private RegistAndUpdateService registAndUpdateService;
	
	/**
	 * 将分组权限封装成list
	 * 
	 * @return
	 */
	protected List<String> groupNames() {
		List<String> list = new ArrayList<String>();
		list.add(BOOK_INFO);
		list.add(PURCHASE);
		list.add(REQUIREMENT);
		list.add(TOOLKIT);
		list.add(SHIRO);
		list.add(STOCK);
		list.add(DATA);
		return list;
	}
	
	protected void putPermissionToView(ModelMap map) {
		map.put("registList", this.registAndUpdateService.getGroupPermissions(SHIRO));
		map.put("purchaseList", this.registAndUpdateService.getGroupPermissions(PURCHASE));
		map.put("requirementList", this.registAndUpdateService.getGroupPermissions(REQUIREMENT));
		map.put("bookInfoList", this.registAndUpdateService.getGroupPermissions(BOOK_INFO));
		map.put("toolkitList", this.registAndUpdateService.getGroupPermissions(TOOLKIT));
		map.put("stockList", this.registAndUpdateService.getGroupPermissions(STOCK));
		map.put("dataList", this.registAndUpdateService.getGroupPermissions(DATA));
	}
	
	/**
	 * 获取权限分组的list
	 * 
	 * @param list
	 * @param GroupName
	 * @return
	 */
	protected List<Permission> getGroupList(List<Permission> list, String GroupName) {
		List<Permission> listPermissions = new ArrayList<Permission>();
		for (Permission p : list) {
			if (p.getGroupName().equals(GroupName)) {
				listPermissions.add(p);
			}
		}
		return listPermissions;
	}

	/**
	 * 获取用户的permission ids
	 * 
	 * @param list
	 * @param GroupName
	 * @return
	 */
	protected List<Integer> getPermissionIds(List<Permission> list) {
		List<Integer> PermissionIds = new ArrayList<Integer>();
		for (Permission p : list) {
			PermissionIds.add(p.getId());
		}
		return PermissionIds;
	}

	/**
	 * 获取当前用户所有权限
	 * 
	 * @return
	 */
	protected List<Permission> getUserPermissions() {
		List<Permission> pList = getActiveUser().getPermissions();
		return pList;
	}

	/**
	 * 获取当前用户的权限，名称等信息
	 * 
	 * @return
	 */
	private ActiveUser getActiveUser() {
		Subject my = SecurityUtils.getSubject();
		ActiveUser au = (ActiveUser) my.getPrincipals().getPrimaryPrincipal();
		return au;
	}

//	// TODO 测试完成后删除
//	/**
//	 * 
//	 * 获取展示的权限集合 新建用户使用
//	 * 
//	 * @param registDto
//	 * @param map
//	 * @param ids
//	 */
//	protected void showPermissionAllList(RegistDto registDto, ModelMap map, Integer... ids) {
//
//		// 展示注册的权限
//		List<Permission> registList = getPermissionList(registDto.getRegistList());
//		// 如果有选择的id传入则将list改变为新的list传入map
//		if (ids.length > 0 && ids != null) {
//			registList = getCheckedPermission(registList, ids);
//		}
//		map.put("registList", registList);
//
//		// 展示采购的权限
//		List<Permission> purchaseList = getPermissionList(registDto.getPurchaseList());
//		if (ids.length > 0 && ids != null) {
//			purchaseList = getCheckedPermission(purchaseList, ids);
//		}
//		map.put("purchaseList", purchaseList);
//
//		// 展示院校的权限
//		List<Permission> requirementList = getPermissionList(registDto.getRequirementList());
//		if (ids.length > 0 && ids != null) {
//			requirementList = getCheckedPermission(requirementList, ids);
//		}
//		map.put("requirementList", requirementList);
//
//		// 展示图书的权限
//		List<Permission> bookInfoList = getPermissionList(registDto.getBookInfoList());
//		if (ids.length > 0 && ids != null) {
//			bookInfoList = getCheckedPermission(bookInfoList, ids);
//		}
//		map.put("bookInfoList", bookInfoList);
//
//		// 展示系统的权限
//		List<Permission> toolkitList = getPermissionList(registDto.getToolkitList());
//		if (ids.length > 0 && ids != null) {
//			toolkitList = getCheckedPermission(toolkitList, ids);
//		}
//		map.put("toolkitList", toolkitList);
//	}
//
//	/**
//	 * 获取展示的权限集合 新建角色使用
//	 * 
//	 * @param registDto
//	 * @param map
//	 * @param ids
//	 */
//	protected void showPermissions(RegistRoleDto registRoleDto, ModelMap map, Integer... ids) {
//
//		// 展示注册的权限
//		List<Permission> registList = getPermissionList(registRoleDto.getRegistList());
//		// 如果有选择的id传入则将list改变为新的list传入map
//		if (ids.length > 0 && ids != null) {
//			registList = getCheckedPermission(registList, ids);
//		}
//		map.put("registList", registList);
//
//		// 展示采购的权限
//		List<Permission> purchaseList = getPermissionList(registRoleDto.getPurchaseList());
//		if (ids.length > 0 && ids != null) {
//			purchaseList = getCheckedPermission(purchaseList, ids);
//		}
//		map.put("purchaseList", purchaseList);
//
//		// 展示院校的权限
//		List<Permission> requirementList = getPermissionList(registRoleDto.getRequirementList());
//		if (ids.length > 0 && ids != null) {
//			requirementList = getCheckedPermission(requirementList, ids);
//		}
//		map.put("requirementList", requirementList);
//
//		// 展示图书的权限
//		List<Permission> bookInfoList = getPermissionList(registRoleDto.getBookInfoList());
//		if (ids.length > 0 && ids != null) {
//			bookInfoList = getCheckedPermission(bookInfoList, ids);
//		}
//		map.put("bookInfoList", bookInfoList);
//
//		// 展示系统的权限
//		List<Permission> toolkitList = getPermissionList(registRoleDto.getToolkitList());
//		if (ids.length > 0 && ids != null) {
//			toolkitList = getCheckedPermission(toolkitList, ids);
//		}
//		map.put("toolkitList", toolkitList);
//	}

//	/**
//	 * 筛选选中项
//	 * 
//	 * @param inputList
//	 * @param ids
//	 * @return
//	 */
//	private List<Permission> getCheckedPermission(List<Permission> inputList, Integer[] ids) {
//		List<Permission> list = new ArrayList<Permission>();
//		for (int i = 0; i < ids.length; i++) {
//			Integer id = ids[i];
//			for (int j = 0; j < inputList.size(); j++) {
//				Integer listId = inputList.get(j).getId();
//				if (id.equals(listId)) {
//					list.add(inputList.get(j));
//				}
//			}
//		}
//		return list;
//	}

//	/**
//	 * 获取show的list
//	 * 
//	 * @param jspList
//	 * @return
//	 */
//	private List<Permission> getPermissionList(String jspList) {
//		List<Permission> list = new ArrayList<Permission>();
//		String[] name1 = jspList.split("\\[");
//		String[] name2 = new String[name1.length - 2];
//		for (int i = 0; i < name1.length; i++) {
//			if (i != 0 && i != 1)
//				name2[i - 2] = name1[i];
//		}
//		for (String s : name2) {
//			// 获取Id方式
//			String id = getValue(s, "id");
//			// 获取permissionName方法
//			String permissionName = getValue(s, "permissionName");
//			// 获取permissionDescription方法
//			String permissionDescription = getValue(s, "permissionDescription");
//			Permission p = new Permission();
//			p.setId(Integer.parseInt(id));
//			p.setPermissionName(permissionName);
//			p.setPermissionDescription(permissionDescription);
//			list.add(p);
//		}
//		return list;
//	}
//
//	/**
//	 * 获取String类型的值
//	 * 
//	 * @param stringName
//	 * @param splitName
//	 * @return
//	 */
//	private String getValue(String stringName, String splitName) {
//		String[] s1 = stringName.split(splitName + "=");
//		String[] s2 = s1[1].split(",");
//		String value = s2[0];
//		return value.trim();
//	}
}
