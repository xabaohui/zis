<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<script type="text/javascript">
	window.onload = function() {
		checkRoleIdAndCompanyId();
	};
</script>
<style type="text/css">
tr{
	display: table-row-group;
}
</style>
<div align="center">
	<h1 >用户修改</h1>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="shiro/updateForUser" method="post" modelAttribute="registRoleDto" id = "registFrom">
	<div align="center">
	<input type="hidden" name = "id" value = "${user.id}"/>
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="userName" value="${user.userName}" readonly="readonly"/></td>
				<td class = "showError"><spring:errors delimiter="," path="userName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>使用者姓名:</td>
				<td><input type="text" name="realName" value="${user.realName}" /></td>
				<td class = "showError"><spring:errors delimiter="," path="realName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td colspan="3">
				<input type="checkbox" id= "updatePassword" onclick="updateCheck()"/>修改密码
				</td>
			</tr>
			<tr class = "passwordClass" style="display: none;">
				<td>密码:</td>
				<td><input type="password" name="password" id="password" value="${user.password}"/></td>
				<td class = "showError"><spring:errors delimiter="," path="password" cssStyle="color:red"/></td>
			</tr>
			<tr class = "passwordClass" style="display: none;">
				<td>再次输入密码:</td>
				<td><input type="password" name="passwordAgain" id="passwordAgain" onblur="comparePassword()" value="${user.passwordAgain}"/></td>
				<td>
				<spring:errors delimiter="," path="passwordAgain" cssStyle="color:red" class = "showError"/>
				<font id = "errorPasswordAgain" color="red" style="display: none">两次密码输入不一致</font>
				<font color="red" class = "showError">${passwordError}</font>
				</td>
			</tr>
			<tr>
				<td>角色:</td>
				<td colspan="2">
					<c:forEach items="${roleList}" var="role" varStatus="i">
						<input type="radio" name = "roleId" value="${role.id}" onclick="getRoleName('${role.roleName}');"/>
						<font style="font-size: 10px;">${role.roleName}</font>
						<c:if test="${((i.index + 1) mod 5) eq 0}">
							<p/>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
			<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
			<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
			<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td>公司:</td>
				<td>
					<c:forEach items="${companyList}" var="company" varStatus="i">
						<input type="radio" name = "companyId" value="${company.companyId}" onclick="getCompanyName('${company.companyName}');"/>
						<font style="font-size: 10px;">${company.companyName}</font>
						<c:if test="${((i.index + 1) mod 5) eq 0}">
							<p/>
						</c:if>
					</c:forEach>
				</td>
			</tr>
		</table>
		<input type="hidden" name = "roleName" id = "roleName" value ="${roleName}"/>
		<input type="hidden" name = "companyName" id = "companyName" value ="${companyName}"/>
	</div>
	<br>
	<br>
	<div align="center">
		<input type="button" value="提交" style="font-size: 20px;font-weight:bolder;" onclick="passwordSubmit()">
	</div>
</spring:form>
	<input type="hidden" name = "checkedId" id = "checkedId" value="${checkedId}"/>
	<input type="hidden" name = "checkedCompanyId" id = "checkedCompanyId" value="${checkedCompanyId}"/>
<%@ include file="/footer.jsp"%>