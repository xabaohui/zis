<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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
	<s:iterator value="resultList" var="task">
		<tr>
			<td><s:property value="isbn"/></td>
			<td><s:property value="bookName"/></td>
			<td><s:property value="bookAuthor"/></td>
			<td><s:property value="bookEdition"/></td>
			<td><s:property value="bookPublisher"/></td>
			<td><s:property value="college"/></td>
			<td><s:property value="institute"/></td>
			<td><s:property value="partName"/></td>
			<td><s:property value="grade"/></td>
			<td><s:property value="term"/></td>
			<td><s:property value="totalCount"/></td>
		</tr>
	</s:iterator>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>