<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src="resources/regist.js"></script>
<script type="text/javascript">
	function updateUser(userId) {
		window.location.href = "/zis/shiro/updateUser?id=" + userId;
	}
	function deleteUser(userId) {
		if (confirm("你确定删除吗？")) {
			window.location.href = "/zis/shiro/deleteUser?id=" + userId;
		} else {
		}
	}
	function registUser() {
		window.location.href = "shiro/gotoRegistUser";
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
	<h1>用户查询及修改</h1>
	<br />
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
	<h2>
		<font color="red">${notResult}</font>
	</h2>
</div>
<p />
<form action="shiro/updateWaitUser" method="post">
	<div align="center">
		<table>
			<tr>
				<td>用户名:</td>
				<td colspan="3"><input type="text" name="userName" value="${param.userName}" style="display: block;"
					onkeyup="value=value.replace(/[\W]/g,'') " /></td>
			</tr>
			<tr>
				<td>使用者姓名:</td>
				<td colspan="3"><input type="text" name="realName" value="${param.realName}" /></td>
			</tr>
			<tr>
				<td>公司名称:</td>
				<td colspan="3"><input type="text" name="companyName" value="${companyName}" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询" /></td>
				<td><input type="reset" value="重置" /></td>
				<td><input type="button" value="清除条件" onclick="clearAll()"/></td>
				<td align="right"><input type="button" value="新增用户" onclick="registUser();" /></td>
			</tr>
		</table>
	</div>
</form>

<div style="width: 100%;" id="infoDiv" align="center">
	<table>
		<tr>
			<th>用户名称</th>
			<th>使用者姓名</th>
			<th>角色名称</th>
			<th>角色Code</th>
			<th>角色说明</th>
			<th>公司名称</th>
			<th>角色创建者姓名</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${userList}" var="users">
			<tr>
				<td><a href="shiro/updateUser?id=${users.user.id}">${users.user.userName}</a>
				</td>
				<td>${users.user.realName}</td>
				<td>${users.role.roleName}</td>
				<td>${users.role.roleCode}</td>
				<td width="30%">${users.role.roleDescription}</td>
				<td>${users.company.companyName}</td>
				<td>${users.role.createUserName}</td>
				<td><input type="button" value="修改" onclick="updateUser('${users.user.id}')" /> 
					&nbsp; &nbsp; 
					<input type="button" value="删除" onclick="deleteUser('${users.user.id}')" />
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="shiro/updateWaitUser?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="shiro/updateWaitUser?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>