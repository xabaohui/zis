<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header.jsp"%>
<script type="text/javascript" src="resources/purchase.js"></script>

<h1>新建入库单</h1>
<p />
<h3>
<font color="green">${actionMessage}</font>
</h3>
<p />
请使用IE浏览器，其他浏览器可能无法正确播放声音提示
<p />
<font color="red">${actionError}</font>
<spring:form action="storage/inWarehouse" modelAttribute="inwarehouseCreateDto">
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td>操作备注</td>
						<td>
							<input type="text" name="memo" value="${memo}">
						</td>
						<td>
							<spring:errors delimiter="," path="memo" cssStyle="color:red" />
						</td>
					</tr>
					<tr>
						<td>入库类型</td>
						<td>
							<select name = "bizType" id = "bizType" onchange="changeBizType()">
								<option value = "batch">批量入库</option>
								<option value = "purchase">采购入库</option>
							</select>
						</td>
					</tr>
				</table>
				<div id = "purchaseDiv" style="display: none;">
				<table>
					<tr>
						<td>采购员</td>
						<td><input type="text" name = "purchaseOperator"/></td>
					</tr>				
				</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">库位标签&nbsp;&nbsp;库位容量
				<p />
					<input type="text" name="stockPosLabel" size="15" />&nbsp;
					<input type="text" name="stockPosCapacity" size="3" />
				<p />
					<spring:errors delimiter="," path="stockPosLabel" cssStyle="color:red" />
					&nbsp;
					<spring:errors delimiter="," path="stockPosCapacity" cssStyle="color:red" />
				<p />
					<div id="purchaseInStockPos"></div> 
					<input type="button" value="添加" onclick="addStockPosition()" />
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="下一步"></td>
		</tr>
	</table>
</spring:form>

<%@ include file="/footer.jsp"%>