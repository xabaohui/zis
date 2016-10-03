<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>系统设置</h1>
	<table id="common-table">
		<tr>
			<th>常量名</th>
			<th>常量值</th>
			<th>操作</th>
		</tr>
			<c:forEach items="${SYSVARLIST}" var="sysVar">
				<tr>
					<td>${sysVar.depKey}</td>
					<td>${sysVar.depValue}</td>
					<td><a href="purchase/updateSysVarPreAction?depKey=${sysVar.depKey}">修改</a></td>
				</tr>
			</c:forEach>
		</table>
</center>
<%@ include file="/footer.jsp"%>