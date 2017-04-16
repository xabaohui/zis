<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>在见书城</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body bgcolor="#FFFAF0">
	<div align="center">
		<h1 align="center" style="color:blue">在见书城登陆系统</h1>
		<font color="red">${message}</font>
		<form action="loginAuthc" method="post">
			<table bgcolor="#FFFAF0" >
				<tr height="50px">
					<td><b>用户名:</b></td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr height="40px">
					<td colspan="2"><font color="red">${userNameError}</font></td>
				</tr>
				<tr height="40px">
					<td><b>密码:</b></td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr height="40px">
					<td colspan="2"><font color="red">${passwordError}</font></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" value="登陆" style="font-size: 20px;font-weight: bold;"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
