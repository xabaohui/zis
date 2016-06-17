<%@page import="com.zis.requirement.bean.BookRequireImportDetailStatus"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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
	<s:iterator value="resultList" var="task">
		<tr>
			<td><s:date name="gmtCreate" format="yyyy年MM月dd日 HH:mm"/></td>
			<td><s:property value="college"/></td>
			<td><s:property value="operator"/></td>
			<td><s:property value="memo"/></td>
			<td>
				<a href="requirement/viewBookRequireImportDetailForBookNotMatched?status=<%=BookRequireImportDetailStatus.BOOK_NOT_MATCHED%>&taskId=<s:property value="id"/>">
					<s:property value="totalCount"/>
				</a>
			</td>
			<td><s:property value="statusDisplay"/></td>
		</tr>
	</s:iterator>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>