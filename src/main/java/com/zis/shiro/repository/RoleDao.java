package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.Role;


public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {
	
	@Query(value = "SELECT rt "
			+ "FROM User ut, Role rt "
			+ "WHERE rt.id= ut.roleId AND ut.id = :userId")
	public List<Role> findRoleByUserId(@Param("userId") Integer userId);
	
	public List<Role> findByRoleName(String roleName);
}
