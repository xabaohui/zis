<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<style type="text/css">
#registDiv table tr td, #registDiv table tr th{
	z-index: 1;
	font-size:1em;
	border:1px solid #98bf21;
}
</style>
<div align="center">
	<h1 >授权创建</h1>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="shiro/createPermission" method="post" modelAttribute="createPermissionDto">
	<div align="center">
		<table>
			<tr>
				<td>权限Code:</td>
				<td><input type="text" name="permissionCode" value="${param.permissionCode}" style="width: 335px" onkeyup="value=value.replace(/[\W]/g,'') "/></td>
				<td class = "showError"><spring:errors delimiter="," path="permissionCode" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>权限名称:</td>
				<td><input type="text" name="permissionName" value="${param.permissionName}" style="width: 335px"/></td>
				<td class = "showError"><spring:errors delimiter="," path="permissionName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>权限url:</td>
				<td><input type="text" name="url" value="${param.url}" style="width: 335px"/></td>
				<td class = "showError"><spring:errors delimiter="," path="url" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>权限分组:</td>
				<td>
					<select name = "groupName">
						<c:forEach items="${groupNames}" var="gN">
							<option value="${gN}">${gN}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>权限描述</td>
				<td>
				<textarea name="permissionDescription" cols="45" rows="10">
					${param.permissionDescription}
				</textarea>
				</td>
			</tr>
		</table>
	</div>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</spring:form>

<%@ include file="/footer.jsp"%>