<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>
	书单明细 - 匹配成功，未导入记录
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>ISBN</th>
		<th>书名</th>
		<th>作者</th>
		<th>版次</th>
		<th>出版社</th>
		<th>学校</th>
		<th>学院</th>
		<th>专业</th>
		<th>学年</th>
		<th>学期</th>
		<th>人数</th>
	</tr>
	<c:forEach items="${resultList}" var="task">
		<tr>
			<td>${task.isbn}</td>
			<td>${task.bookName}</td>
			<td>${task.bookAuthor}</td>
			<td>${task.bookEdition}</td>
			<td>${task.bookPublisher}</td>
			<td>${task.college}</td>
			<td>${task.institute}</td>
			<td>${task.partName}</td>
			<td>${task.grade}</td>
			<td>${task.term}</td>
			<td>${task.totalCount}</td>
		</tr>
		</c:forEach>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>