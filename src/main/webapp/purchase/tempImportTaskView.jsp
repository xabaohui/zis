<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>导入数据列表</h1>
	<h3><a href="purchase/tempImportUpload.jsp">新表导入</a></h3>
	<table id="common-table">
		<tr>
		<th>日期</th>
		<th>业务类型</th>
		<th>备注</th>
		<th>记录总数</th>
		<th>状态</th>
		<th>&nbsp;</th>
		</tr>
	<s:iterator value="taskList" var="task">
		<tr>
			<td><s:date name="gmtCreate" format="yyyy-MM-dd HH:mm"/></td>
			<td><s:property value="bizTypeDisplay"/></td>
			<td><s:property value="memo"/></td>
			<td><s:property value="totalCount"/></td>
			<td><s:property value="statusDisplay"/></td>
			<td><a href="purchase/viewTempImportDetailForNotMatched?status=not_matched&taskId=<s:property value="id"/>">查看</a>&nbsp;|&nbsp;完成&nbsp;|&nbsp;删除</td>
		</tr>
	</s:iterator>
</table>
</center>
<%@ include file="/footer.jsp"%>