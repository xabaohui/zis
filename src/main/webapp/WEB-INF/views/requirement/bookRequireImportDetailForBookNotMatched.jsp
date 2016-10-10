<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>
	书单明细 - 图书未匹配
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>图书</th>
		<th>疑似匹配记录</th>
	</tr>
	<c:forEach items="${resultList}" var="task">
		<tr>
			<td>
				ISBN：${task.isbn}<br/>
				书名：${task.bookName}<br/>
				作者：${task.bookAuthor}&nbsp;版次：${task.bookEdition}<br/>
				出版社：${task.bookPublisher}
			</td>
			<td>
				ISBN：<input type="text" name="isbn" size="10"/>
				书名：<input type="text" name="bookName" size="30"/>
				作者：<input type="text" name="bookAuthor" size="7"/>
				<input type="button" value="查找"/>
			</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>