package com.zis.shiro.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.dto.RegistDto;

public class ActionHelpUtil {

	// 权限分组
	protected final String BOOK_INFO = "bookInfo";
	protected final String PURCHASE = "purchase";
	protected final String REQUIREMENT = "requirement";
	protected final String TOOLKIT = "toolkit";
	protected final String REGIST = "regist";

	// 获取展示的权限集合
	protected void showPermissionAllList(RegistDto registDto, ModelMap map, Integer... ids) {

		// 展示注册的权限
		List<Permission> registList = getPermissionList(registDto.getRegistList());
		// 如果有选择的id传入则将list改变为新的list传入map
		if (ids.length > 0 && ids != null) {
			registList = getCheckedPermission(registList, ids);
		}
		map.put("registList", registList);

		// 展示采购的权限
		List<Permission> purchaseList = getPermissionList(registDto.getPurchaseList());
		if (ids.length > 0 && ids != null) {
			purchaseList = getCheckedPermission(purchaseList, ids);
		}
		map.put("purchaseList", purchaseList);

		// 展示院校的权限
		List<Permission> requirementList = getPermissionList(registDto.getRequirementList());
		if (ids.length > 0 && ids != null) {
			requirementList = getCheckedPermission(requirementList, ids);
		}
		map.put("requirementList", requirementList);

		// 展示图书的权限
		List<Permission> bookInfoList = getPermissionList(registDto.getBookInfoList());
		if (ids.length > 0 && ids != null) {
			bookInfoList = getCheckedPermission(bookInfoList, ids);
		}
		map.put("bookInfoList", bookInfoList);

		// 展示系统的权限
		List<Permission> toolkitList = getPermissionList(registDto.getToolkitList());
		if (ids.length > 0 && ids != null) {
			toolkitList = getCheckedPermission(toolkitList, ids);
		}
		map.put("toolkitList", toolkitList);
	}

	// 筛选选中项
	protected List<Permission> getCheckedPermission(List<Permission> inputList, Integer[] ids) {
		List<Permission> list = new ArrayList<Permission>();
		for (int i = 0; i < ids.length; i++) {
			Integer id = ids[i];
			for (int j = 0; j < inputList.size(); j++) {
				Integer listId = inputList.get(j).getId();
				if (id.equals(listId)) {
					list.add(inputList.get(j));
				}
			}
		}
		return list;
	}

	// 获取show的list
	protected List<Permission> getPermissionList(String jspList) {
		List<Permission> list = new ArrayList<Permission>();
		String[] name1 = jspList.split("\\[");
		String[] name2 = new String[name1.length - 2];
		for (int i = 0; i < name1.length; i++) {
			if (i != 0 && i != 1)
				name2[i - 2] = name1[i];
		}
		for (String s : name2) {
			// 获取Id方式
			String id = getValue(s, "id");
			// 获取permissionName方法
			String permissionName = getValue(s, "permissionName");
			// 获取permissionDescription方法
			String permissionDescription = getValue(s, "permissionDescription");
			Permission p = new Permission();
			p.setId(Integer.parseInt(id));
			p.setPermissionName(permissionName);
			p.setPermissionDescription(permissionDescription);
			list.add(p);
		}
		return list;
	}

	// 获取String类型的值
	protected String getValue(String stringName, String splitName) {
		String[] s1 = stringName.split(splitName + "=");
		String[] s2 = s1[1].split(",");
		String value = s2[0];
		return value.trim();
	}
}
