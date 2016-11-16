package com.zis.shiro.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zis.shiro.bean.RolePermission;


public interface RolePermissionDao extends PagingAndSortingRepository<RolePermission, Integer> {
}
