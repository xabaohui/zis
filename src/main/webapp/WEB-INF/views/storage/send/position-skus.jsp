<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	border: 1px solid #98bf21;
	text-align: center;
}
select{
width: 150px;
}
</style>

<div align="center">
	<h1>${label}库位图书</h1>
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
<div style="width: 100%;" id="infoDiv" align="center">
<table id="common-table">
		<tr>
			<th>图书Id</th>
			<th>ISBN</th>
			<th>图书名称</th>
			<th>图书作者</th>
			<th>版次</th>
			<th>出版社</th>
			<th>总量</th>
			<th>占用量</th>
			<th>可用量</th>
		</tr>
		<c:forEach items="${vList}" var="v">
			<tr>
				<td>
					${v.book.id}
				</td>
				<td>
					${v.book.isbn}
				</td>
				<td>
					${v.book.bookName}
				</td>
				<td>
					${v.book.bookAuthor}
				</td>
				<td>
					${v.book.bookEdition}
				</td>
				<td>
					${v.book.bookPublisher}
				</td>
				<td>
					${v.stockAmt}
				</td>
				<td>
					${v.stockOccupy}
				</td>
				<td>
					${v.stockAvailable}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="storage/findPositionSkus?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/findPositionSkus?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>