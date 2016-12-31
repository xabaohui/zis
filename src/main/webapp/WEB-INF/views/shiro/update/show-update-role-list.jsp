<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
function updateRole(roleId){
	window.location.href="/zis/shiro/updateRole?id="+roleId;
}
function rigistRoleUser(){
	window.location.href="shiro/gotoRegistRole";
}
function clearAll(){
	document.getElementById('roleName').value = "";
	document.getElementById('roleCode').value = "";
}
</script>
<style type="text/css">

#infoDiv table tr td,#infoDiv table tr th {
	z-index: 2;
	font-size: 1em;
	border: 1px solid #98bf21;
	text-align: center;
}

</style>
<div align="center">
	<h1>角色查询</h1>
	<br />
	<h2>
		<font color="red">${notResult}</font>
	</h2>
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
<form action="shiro/updateWaitRole" method="post">
	<div align="center">
		<table>
			<tr>
				<td>角色名:</td>
				<td colspan="3"><input type="text" id = "roleName" name="roleName" value="${param.roleName}" style="display: block;"/>
				</td>
			</tr>
			<tr>
				<td>角色CODE:</td>
				<td colspan="3"><input type="text" id = "roleCode" name="roleCode" value="${param.roleCode}" />
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="查询" />
				</td>
				<td><input type="reset" value="重置" />
				</td>
				<td><input type="button" value="清除条件" onclick="clearAll()"/>
				</td>
				<td align="right"><input type="button" value="新增角色" onclick="rigistRoleUser();"/>
				</td>
			</tr>
		</table>
	</div>
</form>

<div style="width: 100%;" id="infoDiv" align="center">
	<table>
		<tr>
			<th>角色Code</th>
			<th>角色名称</th>
			<th>角色说明</th>
			<th>创建者用户名</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${roleList}" var="roles">
			<tr>
				<td><a href="shiro/updateRole?id=${roles.id}">${roles.roleCode}</a></td>
				<td>${roles.roleName}</td>
				<td width="30%">${roles.roleDescription}</td>
				<td>${roles.createUserName}</td>
				<td>
				<input type="button" value = "修改" onclick="updateRole('${roles.id}')"/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="shiro/updateWaitRole?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page}
	&nbsp;
	<c:if test="${not empty nextPage}">
		<a href="shiro/updateWaitRole?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>