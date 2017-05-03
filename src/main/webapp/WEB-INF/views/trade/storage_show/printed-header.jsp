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
		<table class = "common-table-new">
		<tr>
			<th>
				<a href="order/getWaitPickUpList">等待配货</a>
			</th>
			<th>
				<a href="order/getPickupList">配货中</a>
			</th>
			<th>
				<a href="order/getWaitForPrintList">等待打印</a>
			</th>
			<th style="background-color: #A7C942">
				<font color="#00000">已打印</font>
			</th>
			<th>
				<a href="order/getAllStorageOrderList">全部订单</a>
			</th>
		</tr>
		</table>
		<table id = "common-table">
			<tr>
				<td colspan="9" align="left" height="60px">
					<form action="" method="post">
						<table>
							<tr>
								<td style="border: hidden;">上传回填单号文件: &nbsp;<input type="file" name = "excelFile" required="required"/></td>
								<td style="border: hidden;"><input type="submit" value="上传"/></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<th>订单Id</th>
				<th>创建时间</th>
				<th>所属店铺</th>
				<th>网店订单号</th>
				<th>收件人</th>
				<th>商品清单</th>
				<th>状态</th>
				<th>物流信息</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
				<%@ include file="/WEB-INF/views/trade/storage_show/storage-list.jsp"%>
				<td>
					${order.getUniqueStatusDisplay("arranged")}
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
					<c:if test="${order.canFillExpressNumber()}">
						<a href = "#">填单号</a>
						&nbsp;
					</c:if>
					<div id = "desc_${order.orderId}">
						<c:if test="${not empty order.salerRemark}">
							<span title="${order.salerRemark}" onclick=""><font color="red">备注</font></span>
						</c:if>
						<c:if test="${empty order.salerRemark}">
							<span onclick="">备注</span>
						</c:if>
					</div>
				</td>
				</tr>
			</c:forEach>
		</table>
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
		<a href="order/getPrintedList?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="order/getPrintedList?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>