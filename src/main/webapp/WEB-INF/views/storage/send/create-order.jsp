<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/storage.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/storageInwarehouseBO.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript">
</script>
<div align="center">
	<h1>手动下单至仓库</h1>
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
</div>
<p />
<spring:form action="storage/testOrder" method="post" modelAttribute="dto">
	<div align="center">
		<table>
			<tr>
				<td>店铺选择</td>
				<td>
					<select name = "shopId">
						<c:forEach items="${shopList}" var = "shop">
							<option value = "${shop.shopId}">${shop.shopName}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<spring:errors delimiter="," path="shopId" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>外部订单号</td>
				<td>
					<input type="text" name="outTradeNo" value="${param.outTradeNo}"/>
				</td>
				<td>
					<spring:errors delimiter="," path="outTradeNo" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>收件人:</td>
				<td>
					<input type="text" name="buyerName" value="${param.buyerName}"/>
				</td>
				<td>
					<spring:errors delimiter="," path="buyerName" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					订单详情:
				</td>
			</tr>
			<tr>
				<td colspan="3">
				<p />
					图书Id:<input type="text" name="dList[0].skuId" size="15" />&nbsp;
					图书数量:<input type="text" name="dList[0].amount" size="3" />
				<p />
					<spring:errors delimiter="," path="dList[0].skuId" cssStyle="color:red" />
					&nbsp;
					<spring:errors delimiter="," path="dList[0].amount" cssStyle="color:red" />
				<p />
					<div id="createSkuDiv"></div> 
					<input type="button" value="添加" onclick="addSku()" />
					&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
					<input type="button" value="撤销所有" onclick="clearSku()" />
				</td>
			</tr>
		</table>
		<input type="hidden" id = "skuNo" value = "1"/>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</spring:form>
<%@ include file="/footer.jsp"%>