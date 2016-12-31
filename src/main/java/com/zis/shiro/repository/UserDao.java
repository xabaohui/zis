package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.User;
import com.zis.shiro.dto.ShiroRealmDto;
import com.zis.shiro.dto.UpdateUserInfo;

public interface UserDao extends PagingAndSortingRepository<User, Integer> {

	final String ADMIN = "admin";

	@Query(value = "SELECT new com.zis.shiro.dto.ShiroRealmDto(ut.id, ut.userName, ut.password, ut.salt, rt.roleName, pt.permissionName, pt.url) "
			+ "FROM User ut, Role rt , RolePermission rpt , Permission pt "
			+ "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId AND ut.userName = :userName")
	public List<ShiroRealmDto> findUserAllInfoHQL(@Param("userName") String userName);

	/**
	 * 根据用户名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt) " + "FROM User ut, Role rt "
			+ "WHERE rt.id= ut.roleId AND ut.userName = :userName AND ut.userName <> '" + ADMIN + "'")
	public Page<UpdateUserInfo> findUpdateUserInfoByUserName(@Param("userName") String userName, Pageable page);

	/**
	 * 根据真实名称查找用户
	 * 
	 * @param realName
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt) " + "FROM User ut, Role rt "
			+ "WHERE rt.id= ut.roleId AND ut.realName = :realName AND ut.userName <> '" + ADMIN
			+ "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUpdateUserInfoByRealName(@Param("realName") String realName, Pageable page);

	/**
	 * 根据User的最新修改时间排列，查询所有用户及相关权限
	 * 
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt) " + "FROM User ut, Role rt "
			+ "WHERE rt.id= ut.roleId AND ut.userName <> '" + ADMIN + "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUserAllOrderByUserUpdateTimeDesc(Pageable page);

	public List<User> findByUserName(String userName);

	// @Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, pt) "
	// + "FROM User ut, Role rt , RolePermission rpt , Permission pt "
	// +
	// "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId AND ut.userName = :userName")
	// public List<UpdateUserInfo>
	// findUpdateUserInfoByUserName(@Param("userName") String userName);
	//
	// @Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, pt) "
	// + "FROM User ut, Role rt , RolePermission rpt , Permission pt "
	// +
	// "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId AND ut.realName = :realName")
	// public List<UpdateUserInfo>
	// findUpdateUserInfoByRealName(@Param("realName") String realName);

	// @Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, pt) "
	// + "FROM User ut, Role rt , RolePermission rpt , Permission pt "
	// +
	// "WHERE rt.id= ut.roleId AND rt.id = rpt.roleId AND pt.id = rpt.permissionId ")
	// public List<UpdateUserInfo> findUserAll();
}
