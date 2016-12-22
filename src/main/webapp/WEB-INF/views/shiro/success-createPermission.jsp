<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
#registDiv table tr td,#registDiv table tr th {
	z-index: 1;
	font-size: 1em;
	border: 1px solid #98bf21;
}
</style>
<div align="center">
	<h1 style="color: green;">创建成功</h1>
	<br />
</div>
<p />
<div align="center">
	<table>
			<tr>
				<td>权限Code:</td>
				<td><input type="text" style="width: 335px" value="${createPermissionDto.permissionCode}" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>权限名称:</td>
				<td><input type="text" style="width: 335px" value="${createPermissionDto.permissionName}" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>权限url:</td>
				<td><input type="text" style="width: 335px" value="${createPermissionDto.url}" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>权限分组:</td>
				<td><input type="text" style="width: 335px" value="${createPermissionDto.groupName}" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>权限描述</td>
				<td><textarea cols="45" rows="10" readonly="readonly" style="text-align: left;">
					${createPermissionDto.permissionDescription}
				</textarea>
				</td>
			</tr>
	</table>
</div>
<div align="center">
	<a href="shiro/gotoCreatePermission">返回</a>
</div>
<%@ include file="/footer.jsp"%>