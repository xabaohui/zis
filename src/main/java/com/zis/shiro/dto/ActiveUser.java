package com.zis.shiro.dto;

import java.io.Serializable;
import java.util.List;

import com.zis.shiro.bean.Permission;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements Serializable {

	private static final long serialVersionUID = -6981354022001586691L;

	private Integer userId;// 用户id（主键）
	private String userName;// 用户名称
	private String realName;// 使用者姓名
	private List<Permission> permissions;// 权限
	private Integer companyId;// 公司ID
	private Integer stockId;//仓库Id;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	@Override
	public String toString() {
		return "ActiveUser [userId=" + userId + ", userName=" + userName + ", realName=" + realName + ", permissions="
				+ permissions + ", companyId=" + companyId + ", stockId=" + stockId + "]";
	}
}