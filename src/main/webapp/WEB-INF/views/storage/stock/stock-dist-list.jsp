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
	<h1>库存分布（可用）</h1>
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
<form action="storage/stock/listStockDist" method="post">
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
				<th>库位编号</th>
				<th>总数量</th>
				<th>占用量</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${list}" var="stock">
				<tr>
				<td>${stock.posLabel}</td>
				<td>${stock.totalAmt}</td>
				<td>${stock.occupyAmt}</td>
				<td><a href="storage/stock/listStockAlter?productId=${stock.productId}&posId=${stock.posId}" target="_blank">变动明细</a></td>
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