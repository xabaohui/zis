<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/regist.js"></script>
<script type="text/javascript">
	function updateCompany(companyId) {
		window.location.href = "shop/gotoUpdateCompany?companyId=" + companyId;
	}
	function registCompany() {
		window.location.href = "shop/gotoSaveCompany";
	}
	function registStock(companyId) {
		window.location.href = "storage/gotoSaveStorageRepoInfo?companyId="+companyId;
	}
</script>
<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	z-index: 2;
	font-size: 1em;
	border: 1px solid #98bf21;
	text-align: center;
}
</style>
<div align="center">
	<h1>公司查询及修改</h1>
	<br />
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
	<h2>
		<font color="red">${notResult}</font>
	</h2>
	<h3>
		<font color="red">如果公司有未创建仓库，请点击此公司新增仓库</font>
	</h3>
</div>
<p />
<form action="shop/showCompanys" method="post">
	<div align="center">
		<table>
			<tr>
				<td>公司名称:</td>
				<td colspan="3"><input type="text" name="companyName" value="${param.companyName}" /></td>
			</tr>
			<tr>
				<td>联系人:</td>
				<td colspan="3"><input type="text" name="contacts" value="${param.contacts}" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询" /></td>
				<td><input type="reset" value="重置" /></td>
				<td><input type="button" value="清除条件" onclick="clearAll()"/></td>
				<td align="right"><input type="button" value="新增公司" onclick="registCompany();" /></td>
			</tr>
		</table>
	</div>
</form>
<div style="width: 100%;" id="infoDiv" align="center">
	<table>
		<tr>
			<th>公司名称</th>
			<th>联系人</th>
			<th>手机号</th>
			<th>地址</th>
			<th>创建日期</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${companyList}" var="companyStock">
			<tr>
			<td><a href="shop/gotoUpdateCompany?companyId=${companyStock.company.companyId}">${companyStock.company.companyName}</a></td>
			<td>${companyStock.company.contacts}</td>
			<td>${companyStock.company.mobile}</td>
			<td  width="30%">${companyStock.company.address}</td>
			<td><fmt:formatDate value="${companyStock.company.createTime}" pattern="yyyy年MM月dd日"/></td>
			<td>
				<input type="button" value = "修改" onclick="updateCompany('${companyStock.company.companyId}')"/>
				&nbsp; &nbsp;
				<c:if test="${empty companyStock.storageRepoInfo}">
					<input type="button" value = "新增仓库" onclick="registStock('${companyStock.company.companyId}')"/>
				</c:if>
			</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="shop/showCompanys?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="shop/showCompanys?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>