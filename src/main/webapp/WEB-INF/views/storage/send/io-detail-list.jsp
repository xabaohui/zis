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
</style>
<center>
	<h1>批次变动明细</h1>
</center>
	<br />
<div style="width: 100%;" id="infoDiv" align="center">
<table id="common-table">
		<tr>
			<th>库位</th>
			<th>变动时间</th>
			<th>bookId</th>
			<th>ISBN</th>
			<th>书名</th>
			<th>作者</th>
			<th>操作类型</th>
			<th>变动量</th>
			<th>库存剩余量</th>
			<th>状态</th>
			<th>操作员</th>
		</tr>
		<c:forEach items="${detailList}" var="detail">
			<tr>
				<td>${detail.posLabel}</td>
				<td>
					<fmt:formatDate value="${detail.gmtModify}" pattern="yyyy-MM-dd"/>
					<br/>
					<fmt:formatDate value="${detail.gmtModify}" pattern="HH:mm:ss"/>
				</td>
				<td>
					${detail.book.id}
				</td>
				<td>
					${detail.book.isbn}
				</td>
				<td>
					${detail.book.bookName}
				</td>
				<td>
					${detail.book.bookAuthor}
				</td>
				<td>
					${detail.zhCnType}
				</td>
				<td>
					<c:if test="${detail.ioDetailType eq 'inwarehouse'}"><font color="green">+${detail.amount}</font></c:if>
					<c:if test="${detail.ioDetailType eq 'outwarehouse'}"><font color="red">-${detail.amount}</font></c:if>
				</td>
				<td>
					${detail.balance}
				</td>
				<td>
					${detail.zhCnStatus}
				</td>
				<td>
					${detail.realName}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="storage/findIoDetailByBatchId?batchId=${batchId}&page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/findIoDetailByBatchId?batchId=${batchId}&page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>