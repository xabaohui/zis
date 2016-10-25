<%@page import="com.zis.requirement.bean.BookRequireImportDetailStatus"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>
	已导入书单
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>日期</th>
		<th>学校</th>
		<th>操作员</th>
		<th>操作备注</th>
		<th>记录数量</th>
		<th>状态</th>
	</tr>
	<c:forEach items="${resultList}" var="task">
		<tr>
			<td>${task.gmtCreate}</td>
			<td>${task.college}</td>
			<td>${task.operator}</td>
			<td>${task.memo}</td>
			<td>
				<a href="requirement/viewBookRequireImportDetailForMatched?status=<%=BookRequireImportDetailStatus.BOOK_NOT_MATCHED%>&taskId=${task.id}">${task.totalCount}</a>
			</td>
			<td>${task.statusDisplay}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>