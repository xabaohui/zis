<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/orderController.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src="resources/trade.js"></script>
<script type="text/javascript">
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
	<h1>订单列表-仓库视角</h1>
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
<div style="width: 100%;" id="infoDiv" align="center">
	<form id="orderForm" action="">
		<table class = "common-table-new">
		<tr>
			<th>
				<a href="order/getWaitPickUpList?sort=updateTime&direction=desc">等待配货</a>
			</th>
			<th style="background-color: #A7C942">
				<font color="#00000">配货中</font>
			</th>
			<th>
				<a href="order/getWaitForPrintList?sort=updateTime&direction=desc">等待打印</a>
			</th>
			<th>
				<a href="order/getPrintedList?sort=updateTime&direction=desc">已打印</a>
			</th>
			<th>
				<a href="order/getAllStorageOrderList?sort=createTime&direction=desc">全部订单</a>
			</th>
		</tr>
		</table>
		<table id = "common-table">
<!-- 			<tr> -->
<!-- 				<td colspan="10" align="left" height="60px"> -->
<!-- 					<input style="margin-left: 800px;" type="button" value = "批量缺货" onclick="lacknessList()"/> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
<!-- 				<th> -->
<!-- 					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选 -->
<!-- 				</th> -->
				<th>订单Id</th>
				<th>创建时间</th>
				<th>所属店铺</th>
				<th>网店订单号</th>
				<th>收件人</th>
				<th>商品清单</th>
				<th>商品总数</th>
				<th>状态</th>
				<th>物流信息</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
<!-- 				<td> -->
<!-- 					<c:if test="${order.canLackness()}"> -->
<!-- 						<input name = "orderId" type="checkbox" value="${order.id}"/> -->
<!-- 					</c:if> -->
<!-- 					<c:if test="${!order.canLackness()}"> -->
<!-- 						<input name = "orderId" type="checkbox" value="${order.id}" disabled="disabled"/> -->
<!-- 					</c:if> -->
<!-- 				</td> -->
				<%@ include file="/WEB-INF/views/trade/storage_show/storage-list.jsp"%>
				<td>
					${order.getUniqueStatusDisplay("arranged")}
					<div id = "blockFlag_${order.id}">
						<c:if test="${order.blockFlag}">
							<span title="${order.blockReason}"><font color="red">已拦截</font></span>
						</c:if>
					</div>
				</td>
				<td>
					<div id = "express_${order.id}">
						${order.expressCompany}
						<br/>
						${order.expressNumber}
					</div>
				</td>
				<td>
<!-- 					<c:if test="${order.canLackness()}"> -->
<!-- 					<input type="button" value = "缺货" onclick="lackness('${order.id}','getPickupList')"/> -->
<!-- 						&nbsp; -->
<!-- 					</c:if> -->
					<div id = "desc_${order.id}">
						<c:if test="${not empty order.salerRemark}">
							<span title="${order.salerRemark}" onclick="showAppendSellerRemarkView('${order.id}')"><font color="red">备注</font></span>
						</c:if>
						<c:if test="${empty order.salerRemark}">
							<span onclick="showAppendSellerRemarkView('${order.id}')">备注</span>
						</c:if>
					</div>
				</td>
				</tr>
			</c:forEach>
		</table>
		<input type="hidden" name = "forwardUrl" value = "getPickupList"/>
	</form>
</div>
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="order/getPickupList?${queryCondition}page=${prePage}&sort=updateTime&direction=desc">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="order/getPickupList?${queryCondition}page=${nextPage}&sort=updateTime&direction=desc">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>