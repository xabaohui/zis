package com.zis.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zis.shiro.bean.Permission;
import com.zis.shiro.bean.Role;
import com.zis.shiro.bean.RolePermission;
import com.zis.shiro.bean.User;
import com.zis.shiro.dto.ShiroRealmDto;
import com.zis.shiro.repository.PermissionDao;
import com.zis.shiro.repository.RoleDao;
import com.zis.shiro.repository.RolePermissionDao;
import com.zis.shiro.repository.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:spring/spring.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "TransactionConfiguration")
public class shiroTest {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Test
	public void saveUser() {
		for (int i = 0; i < 3; i++) {
			User user = new User();
			String salt = getSalt();
			String password = new SimpleHash("md5", "123456", salt).toString();
			user.setPassword(password);
			user.setSalt(salt);
			user.setUserName("liumang" + (i - 1));
			user.setIsDelete("no");
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setRoleId(i);
			userDao.save(user);
		}
	}

	@Test
	public void saveRoleDao() {
		for (int i = 0; i < 10; i++) {
			Role role = new Role();
			role.setRoleName(getRandomJianHan(3));
			role.setRoleDescription(getRandomJianHan(10));
			role.setCreateUserName("2222" + i);
			role.setCreateTime(new Date());
			role.setUpdateTime(new Date());
			roleDao.save(role);
		}
	}

	@Test
	public void savePermissionDao() {
		for (int i = 0; i < 10; i++) {
			Permission p = new Permission();
			p.setPermissionName(getRandomJianHan(3));
			p.setPermissionDescription(getRandomJianHan(10));
			p.setCreateTime(new Date());
			p.setUpdateTime(new Date());
			p.setUrl("bookInfo/saveOrUpdate"+i);
			permissionDao.save(p);
		}
	}

	@Test
	public void saverolePermissionDao() {
		for (int j = 1; j < 10; j++) {
			Integer length = new Random().nextInt(1) + 10;
			for (int i = 1; i < length; i++) {
				RolePermission rp = new RolePermission();
				rp.setPermissionId(i);
				rp.setRoleId(j);
				rolePermissionDao.save(rp);
			}
		}
	}

	@Test
	public void gethehe1() {
		List<ShiroRealmDto> list1 = userDao.findUserAllInfoHQL("liumang6");
		for (ShiroRealmDto s : list1) {
			System.out.println(s);
		}
	}

	@Test
	public void gethehe2() {
		List<Permission> list1 = permissionDao.findPermissionByUserId(7);
		for (Permission permission : list1) {
			System.out.println(permission);
		}
	}

	// salt 生成器 长度是20-30之间随机String
	private String getSalt() {
		Integer length = new Random().nextInt(10) + 20;
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 获取中文名称
	private String getRandomJianHan(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBk"); // 转成中文
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}
}
