<%@page import="com.zis.purchase.bean.InwarehouseStatus"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/purchase.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/inwarehouseBO.js'></script>

<center>
	<h1>入库单详情</h1>
</center>
	<p>
	${inwarehouse.bizTypeDisplay}(${inwarehouse.statusDisplay})
	&nbsp;&nbsp;操作员&nbsp;${inwarehouse.inwarehouseOperator},${inwarehouse.source}
	&nbsp;&nbsp;操作备注：${inwarehouse.memo}
	&nbsp;&nbsp;共计${inwarehouse.amount}条
	</p>
	<table id="common-table">
		<tr>
			<th>库位</th>
			<th>ISBN</th>
			<th>书名/版次/作者/出版社</th>
			<th>数量</th>
			<th>入库时间</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${resultList}" var="record">
			<tr>
				<td>${record.positionLabel}
				</td>
				<td>${record.isbn}
				</td>
				<td>
				<div id="inwarehouseDetail_${record.id}">
					${record.bookName} / ${record.bookEdition} / 
					${record.bookAuthor} / ${record.bookPublisher}
				</div>
				</td>
				<td>${record.amount}
				</td>
				<td>${record.gmtCreate}
				</td>
				<td>
					<shiro:hasPermission name="stock:delete">
						<input type="button" value="删除" onclick="return deleteInwarehouseDetail('${record.id}')"/>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
	</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
<%@ include file="/footer.jsp"%>