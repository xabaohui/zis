<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
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
				<a href="">等待配货</a>
			</th>
			<th>
				<a href="">配货中</a>
			</th>
			<th>
				<a href="">等待打印</a>
			</th>
			<th>
				<a href="">已打印</a>
			</th>
			<th style="background-color: blue">
				全部订单
			</th>
		</tr>
		</table>
		<table id = "common-table">
			<tr>
				<td colspan="7" align="left">
					<input type="button" value = "批量配货" onclick=""/>
					<input style="margin-left: 800px" type="button" value = "批量取消" onclick=""/>
				</td>
			</tr>
			<tr>
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选
				</th>
				<th>订单Id</th>
				<th>创建时间</th>
				<th>网店订单号</th>
				<th>收件人</th>
				<th>商品清单</th>
				<th>状态</th>
				<th>物流信息</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
				<td><input name = "orderId" type="checkbox" value="${order.orderId}"/></td>
				<td>${order.orderId}</td>
				<td>
					<fmt:formatDate value="${order.gmtCreate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:set value = "${order.outOrderNumbers}" var = "outNumbers"/>
						<c:forEach items="${outNumbers}" var="outNumber" >
							${outNumber}<br/>
						</c:forEach>
				</td>
				<td>
					<div id = "address_${order.orderId}">
						${order.receiverName}
					</div>
				</td>
				<td  width="35%">
					<c:set value = "${order.orderDetails}" var = "details"/>
						<c:forEach items="${details}" var="detail" >
							${detail.bookName}&nbsp;*&nbsp;${detail.itemCount}<br/>
						</c:forEach>
				</td>
				<td>
					${order.uniqueStatusDisplay}
					<div id = "blockFlag_${order.orderId}">
						<c:if test="${order.blockFlag}">
							<span title="${order.blockReason}"><font color="red">已拦截</font></span>
						</c:if>
					</div>
				</td>
				<td>
					${order.expressCompany}
					<br/>
					${order.expressNumber}
				</td>
				<td>
					<% int count = 0; %>
					
					<c:if test="${order.canArrangeOrderToPos()}">
						<a href = "#">配货</a>
						<% count++; %>
					</c:if>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
					
					<c:if test="${order.canCancelArrangeOrder()}">
						<a href = "#">取消配货</a>
						<% count++; %>
					</c:if>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
					
					<c:if test="${order.canLackness()}">
						<a href = "#">缺货</a>
						<% count++; %>
					</c:if>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
					
					<c:if test="${order.canPrint()}">
						<a href = "#">打单</a>
						<% count++; %>
					</c:if>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
					
					<c:if test="${order.canFillExpressNumber()}">
						<a href = "#">填单号</a>
						<% count++; %>
					</c:if>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
					
					<div>
						<span title="${order.salerRemark}" onclick="">备注</span>
					</div>
					<% count++; %>
					<% if(count % 2 ==0){%>
						<br/>
					<% } %>
				</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	该条码对应多种图书，请选择：
	<div id="selectArea"></div>
	<p />
	<input type="button" value="取消" onclick="cancelSelection()" />
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>