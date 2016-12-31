package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

	final String ADMIN = "adminTest";

	@Query(value = "SELECT rt FROM User ut, Role rt WHERE rt.id = ut.roleId AND ut.id = :userId")
	public List<Role> findRoleByUserId(@Param("userId") Integer userId);

	@Query(value = "SELECT rt FROM Role rt WHERE rt.roleCode <> '" + ADMIN
			+ "' AND rt.id <> 0 AND rt.roleName LIKE %:roleName% ORDER BY rt.updateTime DESC")
	public Page<Role> findByRoleNameLikeOrderByUpdateTimeDesc(@Param("roleName") String roleName, Pageable page);

	@Query(value = "SELECT rt FROM Role rt WHERE rt.roleCode <> '" + ADMIN
			+ "' AND rt.id <> 0 AND rt.roleCode = :roleCode")
	public Page<Role> findByRoleCode(@Param("roleCode") String roleCode, Pageable page);

	@Query(value = "SELECT rt FROM Role rt WHERE rt.roleCode <> '" + ADMIN
			+ "' AND rt.id <> 0 ORDER BY rt.updateTime DESC")
	public Page<Role> findAllOrderByUpdateTimeDesc(Pageable page);

	@Query(value = "SELECT rt FROM Role rt WHERE rt.roleCode <> '" + ADMIN
			+ "' AND rt.id <> 0 ORDER BY rt.updateTime DESC")
	public List<Role> findByIdNotEqZeroAll();
}
