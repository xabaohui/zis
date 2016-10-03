<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script type="text/javascript" src="resources/purchase.js"></script>

<h1>新建入库单</h1>
<h3><a href="purchase/viewInwarehouseList">入库单列表</a></h3>
<p/>
请使用IE浏览器，其他浏览器可能无法正确播放声音提示<p/>
<form action="purchase/inWarehouse" method="post">
	<table>
		<tr>
			<td>
			 入库类型&nbsp;&nbsp;<select required="true" name="bizType" >
			 <option value="purchase">采购入库</option>
			 <option value="direct">直接入库</option>
			 <option value="return">退货入库</option>
			 </select>
			 <table>
			 	<tr>
			 		<td>入库操作员</td>
					<td><input type="text" name="inwarehouseOperator" value="${inwarehouseOperator}" required="true"></td>
			 	</tr>
			 	<tr>
			 		<td>采购员</td>
			 		<td><input type="text" name="purchaseOperator" value="${purchaseOperator}"></td>
			 	</tr>
			 	<tr>
			 		<td>操作备注</td>
			 		<td><input type="text" name="memo" value="${memo}"></td>
			 	</tr>
			 </table>
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
			<td>
			<input type="submit"value="下一步">
			</td>
		</tr>
	</table>
</form>

<%@ include file="/footer.jsp"%>