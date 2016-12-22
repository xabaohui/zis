<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
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
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
	<div align="center">
		<table>
			<tr>
				<td>用户名:</td>
				<td><b>${param.userName}</b></td>
			</tr>
			<tr>
				<td>使用者姓名:</td>
				<td><b>${param.realName}</b></td>
			</tr>
			<tr>
				<td>角色:</td>
				<td><b>${param.roleName}</b></td>
			</tr>
		</table>
	</div>
	<div align="center">
		<a href="goIndex">返回首页</a>
	</div>
	<br>
	<br>
<%@ include file="/footer.jsp"%>