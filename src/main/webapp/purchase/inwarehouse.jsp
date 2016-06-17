<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="resources/purchase.js"></script>

<h1>新建入库单</h1>
<h3><a href="purchase/viewInwarehouseList">入库单列表</a></h3>
<p/>
请使用IE浏览器，其他浏览器可能无法正确播放声音提示<p/>
<s:actionerror cssStyle="color:red" />
<s:fielderror cssStyle="color:red" />
<s:form action="purchase/inWarehouse" method="post">
	<table>
		<tr>
			<td>
			<s:select list="#{'purchase':'采购入库', 'direct':'直接入库', 'return':'退货入库'}" name="bizType" label="入库类型" required="true" />
			<s:textfield name="inwarehouseOperator" label="入库操作员" required="true" />
			<s:textfield name="purchaseOperator" label="采购员" />
			<s:textfield name="memo" label="操作备注" />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				库位标签&nbsp;&nbsp;库位容量 <p/>
				<input type="text" name="stockPosLabel" size="15" />&nbsp;<input type="text" name="stockPosCapacity" size="3" />
				<p />
				<div id="purchaseInStockPos"></div>
				<input type="button" value="添加"onclick="addStockPosition()" />
			</td>
		</tr>
		<tr>
			<td><s:submit value="下一步" /></td>
		</tr>
	</table>
</s:form>

<%@ include file="/footer.jsp"%>