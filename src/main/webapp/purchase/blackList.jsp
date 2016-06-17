<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>采购黑名单</h1>
	<s:form name="queryBlackList" method="post">
		<s:textfield name="isbn" label="ISBN"/>
		<s:textfield name="bookName" label="书名"/>
		<s:submit value="查询"/>
	</s:form>
	<table id="common-table">
		<tr>
		<th>ISBN</th>
		<th>书名</th>
		<th>版次</th>
		<th>作者</th>
		<th>出版社</th>
		<th>删除</th>
		</tr>
	<s:iterator value="resultList" var="record">
		<tr>
			<td><s:property value="isbn"/></td>
			<td><s:property value="bookName"/></td>
			<td><s:property value="bookEdition"/></td>
			<td><s:property value="bookAuthor"/></td>
			<td><s:property value="bookPublisher"/></td>
			<td><a href="purchase/deleteBlackListAction?id=<s:property value="id"/>&flag=black">删除</a></td>
		</tr>
	</s:iterator>
</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>