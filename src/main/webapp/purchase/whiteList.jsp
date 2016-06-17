<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
<h1>采购白名单</h1>
	<s:form name="queryWhiteList" method="post">
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
			<td><a href="purchase/deleteWhiteListAction?id=<s:property value="id"/>&flag=white">删除</a></td>
		</tr>
	</s:iterator>
</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
	
</center>
<%@ include file="/footer.jsp"%>