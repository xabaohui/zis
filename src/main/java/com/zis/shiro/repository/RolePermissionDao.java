package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.shiro.bean.RolePermission;

public interface RolePermissionDao extends PagingAndSortingRepository<RolePermission, Integer> {

	public List<RolePermission> findByRoleId(Integer roleId);
}
