<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript" src='resources/trade.js'></script>
<script type="text/javascript">
	window.onload = function() {
		checkedShopId();
	};
</script>
<style type="text/css">
</style>
<div align="left">
	<h1>地址导入</h1>
	<br />
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
	<h2>
		<font color="red">${notResult}</font>
	</h2>
	<p />
	<p />
	<p />
	<form action="order/excelAddrToOrderUpload" method="post" enctype="multipart/form-data" id="xlsAddressInOrderFrom"
		onsubmit="return checkXlsAddressInOrderFrom()">
		<table>
			<tr>
				<td>选择店铺</td>
				<td><select name="shopId" id="shopId">
						<option value="">未选择</option>
						<c:forEach items="${shopList}" var="shop">
							<option value="${shop.shopId}">${shop.shopName}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td>订单信息文件</td>
				<td><input type="file" name="orderFile" id="orderFile" required="required"></td>
			</tr>
			<tr>
				<td colspan="2"></td>
				<td><input type="submit" value="导入"></td>
			</tr>
		</table>
		<input type="hidden" name="token" value="${token}"> <input type="hidden" id="checkShopId"
			value="${param.shopId}">
	</form>
</div>

<%@ include file="/footer.jsp"%>