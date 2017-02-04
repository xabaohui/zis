package com.zis.shiro.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zis.shiro.bean.User;
import com.zis.shiro.dto.UpdateUserInfo;

public interface UserDao extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {

	final String ADMIN = "admin";
	final String YES = "yes";
	final String NO = "no";
	final String NORMAL = "normal";
	final String DELETE = "delete";

	@Query(value = "FROM User WHERE id =:id AND userName <> '" + ADMIN + "' AND isDelete = '" + NO + "'")
	public User findUserByUserId(@Param("id") Integer id);

	/**
	 * 根据用户名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt,Company cp "
			+ "WHERE rt.id= ut.roleId AND ut.userName = :userName AND cp.companyId = ut.companyId "
			+ "AND cp.status = '" + NORMAL + "' AND ut.userName <> '" + ADMIN + "'" + " AND ut.isDelete = '" + NO
			+ "' AND rt.status = '" + NORMAL + "'")
	public Page<UpdateUserInfo> findUpdateUserInfoByUserName(@Param("userName") String userName, Pageable page);

	/**
	 * 根据真实名称查找用户
	 * 
	 * @param realName
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt, Company cp "
			+ "WHERE rt.id= ut.roleId AND ut.realName = :realName AND cp.companyId = ut.companyId "
			+ " AND cp.status = '" + NORMAL + "' AND ut.userName <> '" + ADMIN + "'" + " AND ut.isDelete = '" + NO
			+ "' AND rt.status = '" + NORMAL + "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUpdateUserInfoByRealName(@Param("realName") String realName, Pageable page);

	/**
	 * 根据公司姓名查询用户
	 * 
	 * @param companyName
	 * @param page
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt, Company cp "
			+ "WHERE rt.id= ut.roleId AND cp.companyName LIKE %:companyName% AND cp.companyId = ut.companyId "
			+ " AND cp.status = '" + NORMAL + "' AND ut.userName <> '" + ADMIN + "'" + " AND ut.isDelete = '" + NO
			+ "' AND rt.status = '" + NORMAL + "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUpdateUserInfoByCompanyNameLike(@Param("companyName") String companyName,
			Pageable page);

	/**
	 * 根据公司姓名，及真实姓名查询用户
	 * 
	 * @param companyName
	 * @param page
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt, Company cp "
			+ "WHERE rt.id= ut.roleId AND cp.companyName LIKE %:companyName% AND ut.realName = :realName "
			+ "AND cp.companyId = ut.companyId " + " AND cp.status = '" + NORMAL + "' AND ut.userName <> '" + ADMIN
			+ "'" + " AND ut.isDelete = '" + NO + "' AND rt.status = '" + NORMAL + "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUpdateUserInfoByCompanyNameLikeAndRealName(
			@Param("companyName") String companyName, @Param("realName") String realName, Pageable page);

	/**
	 * 根据User的最新修改时间排列，查询所有用户及相关权限
	 * 
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt, Company cp "
			+ "WHERE rt.id= ut.roleId AND cp.companyId = ut.companyId " + "AND cp.status = '" + NORMAL
			+ "' AND ut.userName <> '" + ADMIN + "' AND ut.isDelete = '" + NO + "' AND rt.status = '" + NORMAL
			+ "' ORDER BY ut.updateTime DESC")
	public Page<UpdateUserInfo> findUserAllOrderByUserUpdateTimeDesc(Pageable page);

	@Query(value = "FROM User WHERE userName = :userName AND isDelete = '" + NO + "'")
	public List<User> findByUserName(@Param("userName") String userName);

	/**
	 * 删除用户时，查询使用，不需要限制状态
	 * 
	 * @param roleId
	 * @return
	 */
	public List<User> findByRoleId(Integer roleId);

	/**
	 * 根据公司ID查找 公司下所有员工
	 * 
	 * @param companyName
	 * @param page
	 * @return
	 */
	@Query(value = "SELECT new com.zis.shiro.dto.UpdateUserInfo(ut, rt, cp) " + "FROM User ut, Role rt, Company cp "
			+ "WHERE rt.id= ut.roleId AND cp.companyId = :companyId AND cp.companyId = ut.companyId "
			+ " AND cp.status = '" + NORMAL + "' AND ut.userName <> '" + ADMIN + "'" + " AND ut.isDelete = '" + NO
			+ "' AND rt.status = '" + NORMAL + "' ORDER BY ut.updateTime DESC")
	public List<UpdateUserInfo> findAllUserByCompanyId(@Param("companyId") Integer companyId);

	/**
	 * 根据公司ID查找 公司某一个员工
	 * 
	 * @param companyId
	 * @return
	 */
	@Query(value = "FROM User WHERE companyId = :companyId AND id = :userId AND isDelete = '" + NO + "'")
	public User findAllUserByCompanyIdAndUserId(@Param("companyId") Integer companyId,
			@Param("userId") Integer userId);

}
