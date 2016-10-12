<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<center>
	<h1>院校信息</h1>
	<form action="requirement/findSchoolInfo" method="post">
		<table>
			<tr>
				<td>学校</td>
				<td><input name="school" type="text">
				</td>
			</tr>
			<tr>
				<td>院系</td>
				<td><input name="institute" type="text">
				</td>
			</tr>
			<tr>
				<td>专业</td>
				<td><input name="partName" type="text">
				</td>
			</tr>
			<tr>
				<td><input value="查询" type="submit">
				</td>
				<td><input value="重置" type="reset">
				</td>
			</tr>
		</table>
	</form>
	<table id="common-table">
		<tr>
			<th>学校</th>
			<th>学院</th>
			<th>专业</th>
			<th>学年制</th>
			<th>操作</th>
			<th>录入操作</th>
		</tr>
		<c:forEach items="${allSchoolInfo}" var="st">
			<tr>
				<td>${st.college}</td>
				<td>${st.institute}</td>
				<td>${st.partName}</td>
				<td>${st.years}</td>
				<td><a href="requirement/updateSchoolPre?id=${st.did}">修改</a></td>
				<td><a href="requirement/addAmountPreAction?id=${st.did}">录入教材</a>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- 分页查询start -->
	<c:if test="${not empty prePage}">
		<a
			href="requirement/findSchoolInfo?page=${prePage}&school=${school}&institute=${institute}&partName=${partName}">上一页</a>&nbsp;
		</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a
			href="requirement/findSchoolInfo?page=${nextPage}&school=${school}&institute=${institute}&partName=${partName}">下一页</a>&nbsp;
		</c:if>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
