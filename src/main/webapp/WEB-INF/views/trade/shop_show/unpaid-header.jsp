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
	<h1>订单列表-店铺视角</h1>
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
			<th style="background-color: #A7C942">
				<font color="#00000">未支付</font>
			</th>
			<th>
				<a href="order/getRefundingList?sort=updateTime&direction=desc">退款中</a>
			</th>
			<th>
				<a href="order/getWaitArrangeHeaderList?sort=updateTime&direction=desc">未分配</a>
			</th>
			<th>
				<a href="order/getAllShopOrderList?sort=updateTime&direction=desc">全部订单</a>
			</th>
		</tr>
		</table>
		<table id = "common-table">
			<tr>
				<td colspan="11" align="left" height="60px">
					<input style="margin-left: 800px;" type="button" value = "批量取消" onclick="cancelOrder()"/>
				</td>
			</tr>
			<tr>
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选
				</th>
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
				<td>
					<c:if test="${order.canCancelOrder()}">
						<input name = "orderId" type="checkbox" value="${order.id}"/>
					</c:if>
					<c:if test="${!order.canCancelOrder()}">
						<input name = "orderId" type="checkbox" value="${order.id}" disabled="disabled"/>
					</c:if>
				</td>
				<%@ include file="/WEB-INF/views/trade/shop_show/shop-list.jsp"%>
				<td>
					${order.getUniqueStatusDisplay("unpaid")}
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
					<% int count = 0; %>
					
					<c:if test="${order.canCancelOrder()}">
						<input type="button" value = "取消订单" onclick="cancelOrder('${order.id}','getUnpaidList')" />
						&nbsp;
						<% count++; %>
						<% if(count % 2 ==0){%>
							<br/>
						<% } %>
					</c:if>
					
					<c:if test="${order.canPay()}">
						<input type="button" value = "支付" onclick="payOrder('${order.id}','${order.orderMoney}','getUnpaidList')" />
						&nbsp;
						<% count++; %>
						<% if(count % 2 ==0){%>
							<br/>
						<% } %>
					</c:if>
					
					<c:if test="${order.canChangeOrderAddress()}">
						<input type="button" value = "改地址" onclick="showOrderAddress('${order.id}','${order.receiverName}','${order.receiverPhone}','${order.receiverAddr}')" />
						&nbsp;
						<% count++; %>
						<% if(count % 2 ==0){%>
							<br/>
						<% } %>
					</c:if>
					
					<c:if test="${order.canBlock()}">
						<input type="button" value = "拦截" onclick="ifBlockOrder('${order.id}')" />
						&nbsp;
						<% count++; %>
						<% if(count % 2 ==0){%>
							<br/>
						<% } %>
					</c:if>
					
					<c:if test="${order.canUnblock()}">
						<input type="button" value = "取消拦截" onclick="unblockOrder('${order.id}','getUnpaidList')" />
						&nbsp;
						<% count++; %>
						<% if(count % 2 ==0){%>
							<br/>
						<% } %>
					</c:if>
					
					<div id = "desc_${order.id}">
						<c:if test="${not empty order.salerRemark}">
							<span title="${order.salerRemark}" onclick="showAppendSellerRemarkView('${order.id}')"><font color="red">备注</font></span>
						</c:if>
						<c:if test="${empty order.salerRemark}">
							<span onclick="showAppendSellerRemarkView('${order.id}')">备注</span>
						</c:if>
					</div>
					<% count = 0; %>
				</td>
				</tr>
			</c:forEach>
		</table>
		<input type="hidden" name = "forwardUrl" value = "getUnpaidList"/>
	</form>
</div>
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">	
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="order/getUnpaidList?page=${prePage}&sort=updateTime&direction=desc">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="order/getUnpaidList?page=${nextPage}&sort=updateTime&direction=desc">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>