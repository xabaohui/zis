<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>

<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	z-index: 2;
	font-size: 1em;
	border: 1px solid #98bf21;
	text-align: center;
}
</style>
<div align="center">
	<h1>库存变动日志</h1>
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
</div>
<p />
<form action="storage/stock/listStockAlter" method="post">
	<div align="center">
		<table>
			<tr>
				<td>书名</td>
				<td>${book.bookName}</td>
			</tr>
			<tr>
				<td>ISBN</td>
				<td>${book.isbn}</td>
			</tr>
			<tr>
				<td>作者</td>
				<td>${book.bookAuthor}</td>
			</tr>
			<tr>
				<td>版次</td>
				<td>${book.bookEdition}</td>
			</tr>
		</table>
	</div>
</form>
<div style="width: 100%;" id="infoDiv" align="center">
	<form id="sendForm" action="">
		<table>
			<tr>
				<th>时间</th>
				<th>类型</th>
				<th>库位</th>
				<th>变动量</th>
				<th>剩余量</th>
				<th>操作员</th>
				<th>备注</th>
			</tr>
			<c:forEach items="${list}" var="detail">
				<tr>
				<td><fmt:formatDate value="${detail.gmtCreate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${detail.displayType}</td>
				<td>${detail.posLabel}</td>
				<td>${detail.displayAmount}</td>
				<td>${detail.balance}</td>
				<td>${detail.operator}</td>
				<td><c:if test="${detail.detailStatus eq 'lackness'}" ><font color="red">缺货</font></c:if></td>
				</tr>
			</c:forEach>
		</table>
	</form>
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