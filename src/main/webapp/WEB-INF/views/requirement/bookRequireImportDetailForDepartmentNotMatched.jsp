<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>
	书单明细 - 专业未匹配
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>专业</th>
		<th>疑似匹配记录</th>
	</tr>
	<c:forEach items="${resultList}" var="task">
		<tr>
			<td>
				学校：${task.college}<br/>
				学院：${task.institute}<br/>
				班级编码：${task.classNum}<br/>
				学年：${task.grade}&nbsp;学期：${task.term}
			</td>
			<td>无匹配记录</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>