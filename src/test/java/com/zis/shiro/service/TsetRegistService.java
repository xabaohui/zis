package com.zis.shiro.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class TsetRegistService {

	@Autowired
	private RegistService registService;
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionDao permissionDao;

	@Test
	public void Test() {
//		List<Permission> list = this.registService.findAllPermission();
//		for (Permission p : list) {
//			System.out.println(p);
//		}
	}

	@Test
	public void registRole() {
		Role role = new Role();
		role.setCreateTime(new Date());
		role.setUpdateTime(new Date());
		role.setRoleCode("s3323");
		role.setRoleDescription("sdsd");
		role.setCreateUserName("sdsd");
		role.setRoleName("哈哈哈");
		this.roleDao.save(role);
		System.out.println(role);
	}
	
	@Test
	public void test() {
		List<String> list = new ArrayList<String>();
		list.add("淤说褐");
		list.add("单单即");
		list.add("畅沈代");
		list.add("屑塘起");
//		List<Permission> list1=this.permissionDao.findByPermissionNameIn(list);
//		System.out.println(list1);
	}
}
