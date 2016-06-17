<%@page import="com.zis.purchase.bean.InwarehouseStatus"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/purchase.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/inwarehouseBO.js'></script>

<center>
	<h1>入库单详情</h1>
</center>
	<p>
	<s:property value="#inwarehouse.bizTypeDisplay" />(<s:property value="#inwarehouse.statusDisplay" />)
	&nbsp;&nbsp;操作员&nbsp;<s:property value="#inwarehouse.inwarehouseOperator" />,<s:property value="#inwarehouse.source" />
	&nbsp;&nbsp;操作备注：<s:property value="#inwarehouse.memo" />
	&nbsp;&nbsp;共计<s:property value="#inwarehouse.amount" />条
	</p>
	<table id="common-table">
		<tr>
			<th>库位</th>
			<th>ISBN</th>
			<th>书名/版次/作者/出版社</th>
			<th>数量</th>
			<th>入库时间</th>
			<th>&nbsp;</th>
		</tr>
		<s:iterator value="resultList" var="record">
			<tr>
				<td><s:property value="positionLabel" />
				</td>
				<td><s:property value="isbn" />
				</td>
				<td>
				<div id="inwarehouseDetail_<s:property value="id" />">
					<s:property value="bookName" /> / <s:property value="bookEdition" /> / 
					<s:property value="bookAuthor" /> / <s:property value="bookPublisher" />
				</div>
				</td>
				<td><s:property value="amount" />
				</td>
				<td><s:date name="gmtCreate" format="yyyy年MM月dd日"/>
				</td>
				<td><input type="button" value="删除" onclick="return deleteInwarehouseDetail('<s:property value="id" />')"/>
				</td>
			</tr>
		</s:iterator>
	</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
<%@ include file="/footer.jsp"%>