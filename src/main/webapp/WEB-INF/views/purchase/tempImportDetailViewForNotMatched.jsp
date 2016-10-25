<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/purchaseService.js'></script>
<script type="text/javascript" src="resources/purchase.js"></script>

<center>
	<h1>导入记录明细 - 未关联</h1>
	<p/>
	<h3><a href="purchase/viewTempImportTask">上一步</a>&nbsp;
		|&nbsp;<a href="purchase/viewTempImportDetailForMatched?status=matched&taskId=${task.id}">下一步</a>
	</h3>
	<p/>
	<div align="left">
		&nbsp;&nbsp;
		业务类型：${task.bizTypeDisplay}&nbsp;&nbsp;
		备注：${task.memo}&nbsp;&nbsp;
		当前状态：${task.statusDisplay}&nbsp;&nbsp;
		记录总数：${task.totalCount}
		<p/>
	</div>
	<table id="common-table">
		<tr>
		<th>&nbsp;</th>
		<th>导入记录</th>
		<th>疑似匹配记录</th>
		</tr>
	<c:forEach items="${resultList}" var="detail" varStatus="index">
		<tr>
			<td>${index.count}</td>
			<td>
				<a href="bookinfo/getAllBooks?bookISBN=${detail.isbn}" target="_blank">${detail.isbn}</a>
				&nbsp;/&nbsp;${detail.data}&nbsp;/&nbsp;${detail.additionalInfo}
				[<a href="https://s.taobao.com/search?q=${detail.isbn}" target="_blank">淘</a>]
				[<a href="http://search.dangdang.com/?key=${detail.isbn}" target="_blank">当</a>]
			</td>
			<td>
				<ol>
				<c:if test="${empty detail.relatedBooks}">无匹配图书</c:if>
				<c:forEach items="${detail.relatedBooks}" var="book">
					<li>
					<div id="book_${detail.id}_${book.id}">
						${book.bookName}&nbsp;/&nbsp;${book.bookEdition}&nbsp;/&nbsp;${book.bookAuthor}&nbsp;/&nbsp;${book.bookPublisher}
						<input type="button" value="选择" onclick="selectBookForMultipleIsbn(${detail.id}, ${book.id})"/>
					</div>
						<p/>
					</li>
				</c:forEach>
				</ol>
			</td>
		</tr>
	</c:forEach>
</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->

</center>
<%@ include file="/footer.jsp"%>