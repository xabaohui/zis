package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.User;
import com.zis.shiro.dto.ShiroRealmDto;


public interface UserDao extends PagingAndSortingRepository<User, Integer> {

	@Query(value = "SELECT new com.zis.shiro.dto.ShiroRealmDto(ut.id, ut.userName, ut.password, ut.salt, rt.roleName, pt.permissionName, pt.url) "
			+ "FROM User ut, Role rt , RolePermission rpt , Permission pt "
			+ "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId AND ut.userName = :userName")
	public List<ShiroRealmDto> findUserAllInfoHQL(@Param("userName") String userName);
	
	public List<User> findByUserName(String userName);
}
