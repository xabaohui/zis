<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
<script type="text/javascript">
	window.onload = function() {
		checkStatus();
	};
</script>
<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	z-index: 2;
	font-size: 1em;
	border: 1px solid #98bf21;
	text-align: center;
}
</style>
<div align="center">
	<h1>订单列表</h1>
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
<form action="storage/queryStorageOrder" method="post">
	<div align="center">
		<table>
			<tr>
				<td>订单号:</td>
				<td colspan="3"><input type="text" name="outTradeNo" value="${param.outTradeNo}" /></td>
			</tr>
			<tr>
				<td>收件人:</td>
				<td colspan="3"><input type="text" name="buyerName" value="${param.buyerName}" /></td>
			</tr>
			<tr>
				<td>商品状态</td>
				<td>
					<select id = "systemStatus" name = "systemStatus" >
						<option value = "created">新创建</option>
						<option value = "sent">已出库</option>
						<option value = "cancel">已取消</option>
						<option value = "processing">配货中</option>
					</select>
					<input type="hidden" id = "sendStatus" value = "${sendStatus}"/>
					<input type="hidden" name = "size" value = "100"/>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="查询" /></td>
				<td><input type="reset" value="重置" /></td>
				<td><input type="button" value="清除条件" onclick="clearAll()"/></td>
			</tr>
		</table>
	</div>
</form>
<div style="width: 100%;" id="infoDiv" align="center">
	<form id="sendForm" action="">
		<table>
			<c:if test="${sendStatus eq 'created'}">
				<tr>
					<th colspan="7" align="left">
						<input type="button" value = "批量配货" onclick="pickingUpOrder()"/>
						<input style="margin-left: 800px" type="button" value = "批量取消" onclick="cancelOrder()"/>
					</th>
				</tr>
			</c:if>
			<tr>
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选
				</th>
				<th>创建时间</th>
				<th>订单号</th>
				<th>收件人姓名</th>
				<th>状态</th>
				<th>订单详情</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
				<td><input name = "orderId" type="checkbox" value="${order.storageOrder.orderId}"/></td>
				<td>
					<fmt:formatDate value="${order.storageOrder.gmtCreate}" pattern="yyyy-MM-dd"/>
					<br/>
					<fmt:formatDate value="${order.storageOrder.gmtCreate}" pattern="HH-mm-ss"/>
				</td>
				<td>${order.storageOrder.outTradeNo}</td>
				<td>${order.storageOrder.buyerName}</td>
				<td>
					${order.storageOrder.tradeStatus}
				</td>
				<td  width="35%">
					<c:set value = "${order.oList}" var = "dtoList"/>
						<c:forEach items="${dtoList}" var="dto" >
							<table>
								<tr>
									<td>
										${dto.bookTitle}&nbsp;X&nbsp;${dto.bookAmount}
									</td>
								</tr>
							</table>
						</c:forEach>
				</td>
				<td>
					<c:if test="${order.storageOrder.tradeStatus eq '新创建'}">
						<input type="button" value = "配货" onclick="pickingUpOrder('${order.storageOrder.orderId}')"/>
						<br/>
						<br/>
						<input type="button" value = "取消" onclick="cancelOrder('${order.storageOrder.orderId}')"/>
					</c:if>
				</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="storage/queryStorageOrder?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/queryStorageOrder?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>