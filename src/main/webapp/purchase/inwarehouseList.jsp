<%@page import="com.zis.purchase.action.InwarehouseDataExportType"%>
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
	<h1>入库单</h1>
</center>
	<p>
	<input type="button" value="导出网渠宝入库单" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.WANGQUBAO_INWAREHOUSE%>')"/>
	<input type="button" value="导出网渠宝商品资料" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.WANGQUBAO_ITEM_DATA%>')"/>
	<input type="button" value="导出淘宝数据包(新品)" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.TAOBAO_ITEM_DATA%>')"/>
	<input type="button" value="导出售罄清单" onclick="exportInwarehouseData('<%=InwarehouseDataExportType.TAOBAO_ITEM_SOLD_OUT%>')"/>
	</p>
	<s:form action="exportInwarehouseData" method="post" id="form_checkBox">
	<s:hidden name="operateType" id="operateType" />
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
		<s:iterator value="resultList" var="record">
			<tr>
				<td><input type="checkbox" value="<s:property value="id"/>"
					name="batchSelectedItem" <s:if test="#record.status == 'processing'">disabled</s:if>/>
				</td>
				<td><s:date name="gmtCreate" format="yyyy年MM月dd日 HH:mm"/>
				</td>
				<td><s:property value="bizTypeDisplay" /> <s:property value="source"/>
				</td>
				<td><s:property value="inwarehouseOperator" />
				</td>
				<td><s:property value="memo" />
				</td>
				<td><s:a href="purchase/viewInwarehouseDetail?inwarehouseId=%{id}"><s:property value="amount" /></s:a>
				</td>
				<td><s:property value="statusDisplay" />
				<s:if test="#record.status == 'processing'">
					<a href="purchase/recoverScan?inwarehouseId=<s:property value="#record.id"/>">继续扫描</a> 
					| <a href="#" onclick="return deleteInwarehouse('<s:property value="#record.id"/>');">删除</a>
				</s:if>
				</td>
			</tr>
		</s:iterator>
	</table>
	</s:form>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
<%@ include file="/footer.jsp"%>