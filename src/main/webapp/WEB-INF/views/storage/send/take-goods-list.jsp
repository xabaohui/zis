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
	border: 1px solid #98bf21;
	text-align: center;
}
input{
width: 75;
height: 50;
font-size: 15px;
}
</style>

<div align="center">
	<h1>开始配货</h1>
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
<table>
		<tr>
			<th>批次号</th>
			<th>创建时间</th>
			<th>数量</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${batchList}" var="batch">
			<tr>
				<td>${batch.batchId}</td>
				<td>
					<fmt:formatDate value="${batch.gmtCreate}" pattern="yyyy-MM-dd"/>
					<br/>
					<fmt:formatDate value="${batch.gmtCreate}" pattern="HH:mm:ss"/>
				</td>
				<td>${batch.amount}</td>
				<td>
					${batch.zhCnStatus}
				</td>
				<td>
					<input type="button"  value = "配货" onclick="takeGoods('${batch.batchId}')"/>
					&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
					<input type="button" value = "完成" onclick="finishTakeGoods('${batch.batchId}')"/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="storage/querytTakeGoods?page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/querytTakeGoods?page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>