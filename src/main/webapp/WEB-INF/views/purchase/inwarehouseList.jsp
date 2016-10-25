<%@page import="com.zis.purchase.action.InwarehouseDataExportType"%>
<%@page import="com.zis.purchase.bean.InwarehouseStatus"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header.jsp"%>

<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/purchase.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/inwarehouseBO.js'></script>

<center>
	<h1>入库单</h1>
</center>
	<p>
	<input type="button" value="导出网渠宝入库单" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.WANGQUBAO_INWAREHOUSE%>')"/>
	<input type="button" value="导出网渠宝商品资料" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.WANGQUBAO_ITEM_DATA%>')"/>
	<input type="button" value="导出淘宝数据包(新品)" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.TAOBAO_ITEM_DATA%>')"/>
	<input type="button" value="导出售罄清单" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.TAOBAO_ITEM_SOLD_OUT%>')"/>
	</p>
	<form action="purchase/exportInwarehouseData" method="post" id="form_checkBox">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<table id="common-table">
		<tr>
			<th><input type="checkbox" name="allCheck" value="checkAll1234" onclick="checkAll()" /></th>
			<th>时间</th>
			<th>入库类型</th>
			<th>操作员</th>
			<th>操作备注</th>
			<th>数量</th>
			<th>状态</th>
		</tr>
		<c:forEach items="${resultList}" var="record">
			<tr>
				<td>
					<input type="checkbox" value="${record.id}" name="batchSelectedItem" 
					<c:if test="${record.status eq 'processing'}">disabled</c:if>/>
				</td>
				<td>${record.gmtCreate}</td>
				<td>${record.bizTypeDisplay}${record.source}</td>
				<td>${record.inwarehouseOperator}</td>
				<td>${record.memo}</td>
				<td><a href="purchase/viewInwarehouseDetail?inwarehouseId=${record.id}">${record.amount}</a></td>
				<td>${record.statusDisplay}
					<c:if test="${record.status eq 'processing'}">
						<a href="purchase/recoverScan?inwarehouseId=${record.id}">继续扫描</a> 
						| <a href="#" onclick="return deleteInwarehouse('${record.id}');">删除</a>
					</c:if>
				</td>
			</tr>
			</c:forEach>
	</table>
	</form>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
<%@ include file="/footer.jsp"%>