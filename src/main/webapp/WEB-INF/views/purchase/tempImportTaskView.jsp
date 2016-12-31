<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>导入数据列表</h1>
	<h3></h3>
	<table id="common-table">
		<tr>
		<th>日期</th>
		<th>业务类型</th>
		<th>备注</th>
		<th>记录总数</th>
		<th>状态</th>
		<th>&nbsp;</th>
		</tr>
	<c:forEach items="${taskList}" var="task">
		<tr>
			<td>${task.gmtCreate}</td>
			<td>${task.bizTypeDisplay}</td>
			<td>${task.memo}</td>
			<td>${task.totalCount}</td>
			<td>${task.statusDisplay}</td>
			<td><a href="purchase/viewTempImportDetailForMatched?status=not_matched&taskId=${task.id}">查看</a>&nbsp;|&nbsp;完成&nbsp;|&nbsp;删除</td>
		</tr>
	</c:forEach>
</table>
</center>
<%@ include file="/footer.jsp"%>