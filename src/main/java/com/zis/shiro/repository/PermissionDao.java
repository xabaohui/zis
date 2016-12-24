package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.Permission;

public interface PermissionDao extends PagingAndSortingRepository<Permission, Integer> {

	@Query(value = "SELECT pt " + "FROM User ut, Role rt , RolePermission rpt , Permission pt "
			+ "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId AND ut.id = :userId")
	public List<Permission> findPermissionByUserId(@Param("userId") Integer userId);

	public List<Permission> findByPermissionCodeIn(List<String> permissionCodes);

	public List<Permission> findByGroupNameAndIdIn(String gsroupName, List<Integer> ids);

	public List<Permission> findByGroupName(String groupName);

	public List<Permission> findByPermissionCode(String permissionCode);

	public List<Permission> findByUrl(String url);
}
