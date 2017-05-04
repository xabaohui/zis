<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>

	<table>
		<tr>
			<td><h1>图书使用量</h1>&nbsp;&nbsp;<a href="requirement/importRequirement.jsp">批量导入</a>
			</td>
			<td><s:form
					action="exportRequirementCollectSchedule?groupByOperator=true"
					namespace="/requirement" method="post" target="_blank">
					<s:submit value="导出进度(分操作员)" />
				</s:form> <s:form
					action="exportRequirementCollectSchedule?groupByOperator=false"
					namespace="/requirement" method="post" target="_blank">
					<s:submit value="导出进度(不分操作员)" />
				</s:form>
			</td>
		</tr>
	</table>

	<s:form action="getAmountAction" method="post">
		<s:textfield name="isbn" label="ISBN" />
		<s:textfield name="bookName" label="书名" />
		<s:textfield name="school" label="学校" />
		<s:textfield name="institute" label="学院" />
		<s:textfield name="partName" label="专业" />
		<s:textfield name="grade" label="学年" />
		<s:textfield name="term" label="学期" />
		<s:textfield name="operator" label="收录人" />
		<s:submit value="查询" />
	</s:form>
	<table id="common-table">
		<tr>
			<th></th>
			<th>ISBN</th>
			<th>书名</th>
			<th>作者</th>
			<th>版次</th>
			<th>学校</th>
			<th>学院</th>
			<th>专业</th>
			<th>学年</th>
			<th>使用量</th>
			<th>收录人</th>
		</tr>
		<c:forEach items="${BookAmount}" var="ba">
			<tr>
				<td>${ba.id}</td>
				<td>${ba.isbn}</td>
				<td>${ba.bookName}</td>
				<td>${ba.bookAuthor}</td>
				<td>版次</td>
				<td>${ba.college}</td>
				<td>${ba.institute}</td>
				<td>${ba.partName}</td>
				<td>第${ba.grade}学年&nbsp;第${ba.term}学期</td>
				<td>${ba.amount}</td>
				<td>${ba.operator}</td>
			</tr>
		</c:forEach>
	</table>

	<!-- 分页查询start -->
	<s:if test="#prePage != null">
		<a
			href="requirement/getAmountAction?<s:property value="#queryCondition"/>pageSource=pagination&pageNow=<s:property value="#prePage"/>">上一页</a>&nbsp;
		</s:if>
	<s:property value="pageNow" />
	&nbsp;
	<s:if test="#nextPage != null">
		<a
			href="requirement/getAmountAction?<s:property value="#queryCondition"/>pageSource=pagination&pageNow=<s:property value="#nextPage"/>">下一页</a>&nbsp;
		</s:if>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>