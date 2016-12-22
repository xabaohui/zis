package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

	@Query(value = "SELECT rt FROM User ut, Role rt " + "WHERE rt.id= ut.roleId AND ut.id = :userId")
	public List<Role> findRoleByUserId(@Param("userId") Integer userId);

	@Query(value = "SELECT rt FROM Role rt WHERE rt.roleName LIKE %:roleName% ORDER BY rt.updateTime DESC")
	public Page<Role> findByRoleNameLikeOrderByUpdateTimeDesc(@Param("roleName") String roleName, Pageable page);

	public Page<Role> findByRoleCode(String roleCode, Pageable page);

	@Query(value = "SELECT rt FROM Role rt ORDER BY rt.updateTime DESC")
	public Page<Role> findAllOrderByUpdateTimeDesc(Pageable page);
}
