<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/header.jsp"%>
<%@	taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<center>

	<table>
		<tr>
			<td><h1>图书使用量</h1>&nbsp;&nbsp;
			<shiro:hasPermission name="requirement:books:input">
				<a href="requirement/gotoImportRequirement">批量导入</a>
			</shiro:hasPermission>
			</td>
			<td>
			<shiro:hasPermission name="requirement:books:output">
				<form action="requirement/exportRequirementCollectSchedule?groupByOperator=true" method="post" target="_blank">
					<input type="submit" value="导出进度(分操作员)">
				</form>
				<form action="requirement/exportRequirementCollectSchedule?groupByOperator=false" method="post" target="_blank">
					<input type="submit" value="导出进度(不分操作员)">
				</form>
			</shiro:hasPermission>
			</td>
		</tr>
	</table>

	<spring:form action="requirement/getAmountAction" method="post" modelAttribute="getAmountDTO">
		<table>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="isbn" value="${param.isbn}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>书名</td>
				<td><input type="text" name="bookName" value="${param.bookName}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>学校</td>
				<td><input type="text" name="school" value="${param.school}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>学院</td>
				<td><input type="text" name="institute" value="${param.institute}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>专业</td>
				<td><input type="text" name="partName" value="${param.partName}"/></td>
				<td></td>
			</tr>
			<tr>
				<td>学年</td>
				<td><input type="text" name="grade" value="${param.grade}" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')"/></td>
				<td><spring:errors delimiter="," path="grade" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>学期</td>
				<td><input type="text" name="term" value="${param.term}" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/></td>
				<td><spring:errors delimiter="," path="term" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>收录人</td>
				<td><input type="text" name="operator" value="${param.operator}" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input type="submit" value="查询" /></td>
			</tr>
		</table>
	</spring:form>
	<table id="common-table">
		<tr>
			<th>ID</th>
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
		<c:forEach items="${list}" var="ba">
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
	<c:if test="${not empty prePage}">
		<a href="requirement/getAmountAction?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="requirement/getAmountAction?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>