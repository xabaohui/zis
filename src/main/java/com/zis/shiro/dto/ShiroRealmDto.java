package com.zis.shiro.dto;

/**
 * ShiroRealm 的帮助DTO
 * @author think
 *
 */
public class ShiroRealmDto {

	private Integer userId;

	private String userName;

	private String password;

	private String salt;

	private String roleName;

	private String permissionName;

	private String url;

	public ShiroRealmDto() {
	}

	public ShiroRealmDto(Integer userId, String userName, String password, String salt, String roleName,
			String permissionName, String url) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.roleName = roleName;
		this.permissionName = permissionName;
		this.url = url;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "shiroTestDto [userId=" + userId + ", userName=" + userName + ", password=" + password + ", salt="
				+ salt + ", roleName=" + roleName + ", permissionName=" + permissionName + ", url=" + url + "]";
	}

}
