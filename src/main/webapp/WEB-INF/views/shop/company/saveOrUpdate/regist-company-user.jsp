<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<script type="text/javascript">
</script>
<style type="text/css">
#registDiv table tr td, #registDiv table tr th{
	z-index: 1;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#bookInfoDiv table tr td, #bookInfoDiv table tr th{
	z-index: 2;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#purchaseDiv table tr td, #purchaseDiv table tr th{
	z-index: 3;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#requirementDiv table tr td, #requirementDiv table tr th{
	z-index: 4;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#toolkitDiv table tr td, #toolkitDiv table tr th{
	z-index: 5;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#allPermissionDiv div{
	float:inherit;
	display: inline-table;
}
</style>
<div align="center">
	<h1 >${typeName}</h1>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="#" method="post" modelAttribute="registUserDto" id = "registFrom">
	<div align="center">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="userName" value="${user.userName}" style="display: block;"  onkeyup="value=value.replace(/[\W]/g,'')" 
				<c:if test="${not empty user.id}"> readonly="readonly" </c:if> 
				/></td>
				<td class = "showError"><spring:errors delimiter="," path="userName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>使用者姓名:</td>
				<td><input type="text" name="realName" value="${user.realName}" /></td>
				<td class = "showError"><spring:errors delimiter="," path="realName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password" id="password" value="${user.password}"/></td>
				<td class = "showError"><spring:errors delimiter="," path="password" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>再次输入密码:</td>
				<td><input type="password" name="passwordAgain" id="passwordAgain" onblur="comparePassword()" value="${user.passwordAgain}"/></td>
				<td>
				<spring:errors delimiter="," path="passwordAgain" cssStyle="color:red" class = "showError"/>
				<font id = "errorPasswordAgain" color="red" style="display: none">两次密码输入不一致</font>
				<font color="red" class = "showError">${passwordError}</font>
				</td>
			</tr>
		</table>
	</div>
	<br>
	<br>
	<input type="hidden" name = "typeName" value = "${typeName}"/>
	<input type="hidden" name = "id" value = "${user.id}"/>
	<div align="center">
		<input type="button" value="提交" style="font-size: 20px;font-weight:bolder;" onclick="passwordSubmit()">
	</div>
</spring:form>
<%@ include file="/footer.jsp"%>