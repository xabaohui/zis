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
	<h1>商品库存列表</h1>
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
<form action="storage/stock/listProducts" method="post">
	<div align="center">
		<table>
			<tr>
				<td>ISBN</td>
				<td colspan="3"><input type="text" name="isbn" value="${isbn}" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询" /></td>
				<td><input type="reset" value="重置" /></td>
				<td><input type="button" value="清除条件" onclick="clearAll()"/></td>
			</tr>
		</table>
	</div>
</form>
<div style="width: 100%;" id="infoDiv" align="center">
	<form id="sendForm" action="">
		<table>
			<tr>
				<th>书名</th>
				<th>ISBN</th>
				<th>作者</th>
				<th>版次</th>
				<th>出版社</th>
				<th>总数量</th>
				<th>占用量</th>
				<th>可用量</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${storageProducts}" var="product">
				<tr>
				<td>${product.bookName}</td>
				<td>${product.isbn}</td>
				<td>${product.bookAuthor}</td>
				<td>${product.bookEdition}</td>
				<td>${product.bookPublisher}</td>
				<td>${product.stockAmt}</td>
				<td>${product.stockOccupy}</td>
				<td>${product.stockAvailable}</td>
				<td>
					<a href="storage/stock/listStockDist?productId=${product.productId}" target="_blank">库存分布</a>&nbsp;
					<a href="storage/stock/listStockAlter?productId=${product.productId}" target="_blank">变动日志</a>
				</td>
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