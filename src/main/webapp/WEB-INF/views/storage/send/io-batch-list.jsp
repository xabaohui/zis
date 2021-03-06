<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
<script type="text/javascript">
window.onload = function() {
		checkStatusAndType();
	};

</script>
<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	border: 1px solid #98bf21;
	text-align: center;
}
select{
width: 150px;
}
</style>

<div align="center">
	<h1>出入库批次</h1>
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
<div align="center">
	<form action="storage/queryIoBatch" method="post">
		<table>
			<tr>
				<td>操作类型:</td>
				<td>
					<select id = "selectType" name = "bizType" >
						<option value = "">未选择</option>
						<option value = "in_batch">入库</option>
						<option value = "out_batch">出库</option>
					</select>
					<input type="hidden" id = "checkType" value = "${checkType}"/>
				</td>
			</tr>
			<tr>
				<td>状态:</td>
				<td>
					<select id = "selectStatus" name = "status">
						<option value = "">未选择</option>
						<option value = "created">已创建</option>
						<option value = "finish">已完成</option>
						<option value = "cancel">已取消</option>
					</select>
					<input type="hidden" id = "checkStatus" value = "${checkStatus}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="查询" />
				</td>
			</tr>
		</table>
	</form>
</div>
<div style="width: 100%;" id="infoDiv" align="center">
<form action="" method="post" id = "batchForm">
<table id="common-table">
	<c:if test="${checkStatus eq 'finish' && checkType eq 'in_batch'}">
		<tr>
			<td colspan="8" style="border: hidden; text-align: left;"><input type="button" onclick="exportWangqubaoInwarehouse()" value = "入库单导出"/>
			<input type="button" onclick="exportWangqubaoItemByInwarehouse()" value = "网渠宝商品资料导出"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${checkStatus eq 'finish' && checkType eq 'out_batch'}">
		<tr>
			<td colspan="8" style="border: hidden; text-align: left;"><input type="button" onclick="exportWangqubaoSendOut()" value = "出库单导出"/></td>
		</tr>
	</c:if>
		<tr>
			<c:if test="${checkStatus eq 'finish' && (checkType eq 'in_batch' || checkType eq 'out_batch')}">
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllBatchId()"/>全选
				</th>
			</c:if>
			<th>批次号</th>
			<th>创建时间</th>
			<th>操作类型</th>
			<th>操作员</th>
			<th>操作备注</th>
			<th>数量</th>
			<th>状态</th>
		</tr>
		<c:forEach items="${batchList}" var="batch">
			<tr>
				<c:if test="${checkStatus eq 'finish' && (checkType eq 'in_batch' || checkType eq 'out_batch')}">
					<td>
						<input name = "batchId" type="checkbox" value="${batch.batchId}"/>
					</td>
				</c:if>
				<td>${batch.batchId}</td>
				<td>
					<fmt:formatDate value="${batch.gmtCreate}" pattern="yyyy-MM-dd"/>
					<br/>
					<fmt:formatDate value="${batch.gmtCreate}" pattern="HH:mm:ss"/>
				</td>
				<td>
					${batch.zhCnType}
				</td>
				<td>
					${batch.realName}
				</td>
				<td>
					${batch.memo}
				</td>
				<td>
					<a href="storage/findIoDetailByBatchId?batchId=${batch.batchId}">${batch.amount}</a>
				</td>
				<td>
				${batch.zhCnStatus}
				<c:if test="${batch.status eq 'created'&& batch.bizType eq 'in_batch'}">
					<a href="storage/recoverScan?ioBatchId=${batch.batchId}">继续扫描</a>
					&nbsp;&nbsp;
					<a href="storage/cancelInStorage?ioBatchId=${batch.batchId}">取消</a>
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
		<a href="storage/queryIoBatch?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/queryIoBatch?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>