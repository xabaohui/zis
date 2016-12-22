<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
function comparePassword() {
	var password = document.getElementById('newPassword').value;
	var passwordAgain = document.getElementById('newPasswordAgain').value;
	var hideErrors = document.getElementsByClassName('showError');
	if(!(password == passwordAgain)) {
		for(var i = 0; i < hideErrors.length; i++){
			hideErrors[i].style = "display:none";
		}
		document.getElementById('errorPasswordAgain').style="display:block;";
	}else{
		document.getElementById('errorPasswordAgain').style="display:none";
	}
}

function passwordSubmit() {
	var password = document.getElementById("newPassword").value;
	var passwordAgain = document.getElementById("newPasswordAgain").value;
	var hideErrors = document.getElementsByClassName("showError");
	if(!(password == passwordAgain)) {
		document.getElementById("errorPasswordAgain").style="display:block;";
		return;
	}
	document.getElementById("errorPasswordAgain").style="display:none";
	document.getElementById("updateFrom").submit();
}
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
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
<spring:form action="shiro/updateGeneralUserPassword" method="post" 
modelAttribute="generalUserPasswordUpdateDTO" id = "updateFrom">
	<div align="center">
		<table>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="oldPassword" /></td>
				<td class = "showError">
					<spring:errors delimiter="," path="oldPassword" cssStyle="color:red"/>
					<font color="red">${oldPasswordError}</font>
				</td>
			</tr>
			<tr>
				<td>新密码:</td>
				<td><input type="password" name="newPassword" id="newPassword"/></td>
				<td class = "showError"><spring:errors delimiter="," path="newPassword" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>再次输入新密码:</td>
				<td><input type="password" name="newPasswordAgain" id="newPasswordAgain" onblur="comparePassword()" /></td>
				<td>
				<spring:errors delimiter="," path="newPasswordAgain" cssStyle="color:red" class = "showError"/>
				<font id = "errorPasswordAgain" color="red" style="display: none">两次密码输入不一致</font>
				<font color="red" class = "showError">${passwordError}</font>
				</td>
			</tr>
		</table>
	</div>
	<br>
	<br>
	<div align="center">
		<input type="button" value="提交" style="font-size: 20px;font-weight:bolder;" onclick="passwordSubmit()">
	</div>
</spring:form>
	<input type="hidden" name = "checkedId" id = "checkedId" value="${checkedId}"/>
<%@ include file="/footer.jsp"%>