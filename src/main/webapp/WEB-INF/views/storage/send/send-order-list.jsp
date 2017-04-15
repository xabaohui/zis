<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
<script type="text/javascript">
	function updateCompany(companyId) {
		window.location.href = "shop/gotoUpdateCompany?companyId=" + companyId;
	}
	function registCompany() {
		window.location.href = "shop/gotoSaveCompany";
	}
	function registStock(companyId) {
		window.location.href = "storage/gotoSaveStorageRepoInfo?companyId="+companyId;
	}
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
<form action="#" method="post">
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
					<select id = "systemStatus" name = "status" >
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
			<tr>
				<td>
					<input type="button" value = "批量配货" onclick=""/>
				</td>
				<td>
					<input type="button" value = "批量取消"/>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选
				</th>
				<th>公司名称</th>
				<th>联系人</th>
				<th>手机号</th>
				<th>地址</th>
				<th>创建日期</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
				<td><input name = "oId" type="checkbox" value="${order.storageOrder.id}"/></td>
				<td><a href="shop/gotoUpdateCompany?companyId=${companyStock.company.companyId}">${companyStock.company.companyName}</a></td>
				<td>${companyStock.company.contacts}</td>
				<td>${companyStock.company.mobile}</td>
				<td  width="30%">${companyStock.company.address}</td>
				<td><fmt:formatDate value="${companyStock.company.createTime}" pattern="yyyy年MM月dd日"/></td>
				<td>
					<input type="button" value = "修改" onclick="updateCompany('${companyStock.company.companyId}')"/>
					&nbsp; &nbsp;
					<c:if test="${empty companyStock.storageRepoInfo}">
						<input type="button" value = "新增仓库" onclick="registStock('${companyStock.company.companyId}')"/>
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
		<a href="shop/showCompanys?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="shop/showCompanys?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>