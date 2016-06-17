<%@page import="com.zis.purchase.action.TempImportDetailTransferAction"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>导入记录明细 - 已关联</h1>
	<p/>
	<h3><a href="purchase/viewTempImportDetailForNotMatched?status=not_matched&taskId=<s:property value="#task.id"/>">上一步</a>&nbsp;
	|&nbsp;<a href="purchase/exportBookInfoByTempImport?taskId=<s:property value="#task.id"/>" target="_blank">导出基本信息</a>&nbsp;
	|&nbsp;<a href="purchase/exportTaobaoItemDataByTempImport?taskId=<s:property value="#task.id"/>" target="_blank">导出淘宝数据包</a>&nbsp;
	|&nbsp;<a href="purchase/exportWangqubaoItemDataByTempImport?taskId=<s:property value="#task.id"/>" target="_blank">导出网渠宝商品资料</a>&nbsp;
	|&nbsp;<a href="purchase/transferTempImportDetailForMatched?operateType=<%=TempImportDetailTransferAction.OPERATE_TYPE_BOOK_STOCK %>&taskId=<s:property value="#task.id"/>">转为更新库存</a>&nbsp;
	|&nbsp;<a href="purchase/transferTempImportDetailForMatched?operateType=<%=TempImportDetailTransferAction.OPERATE_TYPE_SHOP_ITEM %>&taskId=<s:property value="#task.id"/>">更新网店快照</a>&nbsp;</h3>
	<div align="left">
	&nbsp;&nbsp;
	业务类型：<s:property value="#task.bizTypeDisplay"/>&nbsp;&nbsp;
	备注：<s:property value="#task.memo"/>&nbsp;&nbsp;
	当前状态：<s:property value="#task.statusDisplay"/>&nbsp;&nbsp;
	记录总数：<s:property value="#task.totalCount"/>
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
			<th>数量</th>
			<th>附加信息</th>
		</tr>
		<s:iterator value="resultList" var="detail" status="index">
			<tr>
				<td><s:property value="#index.count"/></td>
				<td><s:property value="isbn"/></td>
				<td><s:property value="associateBook.bookName" /></td>
				<td><s:property value="associateBook.bookEdition" /></td>
				<td><s:property value="associateBook.bookAuthor" /></td>
				<td><s:property value="associateBook.bookPublisher" /></td>
				<td><s:property value="amount" /></td>
				<td><s:property value="additionalInfo" /></td>
			</tr>
		</s:iterator>
	</table>
	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>