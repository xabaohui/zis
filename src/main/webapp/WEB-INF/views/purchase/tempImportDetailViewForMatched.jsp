<%@page import="com.zis.purchase.action.TempImportDetailTransferAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>导入记录明细 - 已关联</h1>
	<p/>
	<h3><a href="purchase/viewTempImportDetailForMatched?status=not_matched&taskId=${task.id}">上一步</a>&nbsp;
	|&nbsp;<a href="purchase/exportBookInfoByTempImport?taskId=${task.id}" target="_blank">导出基本信息</a>&nbsp;
	|&nbsp;<a href="purchase/exportTaobaoItemDataByTempImport?taskId=${task.id}" target="_blank">导出淘宝数据包</a>&nbsp;
	|&nbsp;<a href="purchase/exportWangqubaoItemDataByTempImport?taskId=${task.id}" target="_blank">导出网渠宝商品资料</a>&nbsp;
	|&nbsp;<a href="purchase/transferTempImportDetailForMatched?taskId=${task.id}">${task.bizTypeDisplay}</a>&nbsp;</h3>
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
			<th>ISBN</th>
			<th>书名</th>
			<th>版次</th>
			<th>作者</th>
			<th>出版社</th>
			<th>数据项</th>
			<th>附加信息</th>
		</tr>
		<c:forEach items="${resultList}" var="detail" varStatus="index">
			<tr>
				<td>${index.count}</td>
				<td>${detail.isbn}</td>
				<td>${detail.associateBook.bookName}</td>
				<td>${detail.associateBook.bookEdition}</td>
				<td>${detail.associateBook.bookAuthor}</td>
				<td>${detail.associateBook.bookPublisher}</td>
				<td>${detail.data}</td>
				<td>${detail.additionalInfo}</td>
			</tr>
		</c:forEach>
	</table>
	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>