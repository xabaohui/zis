<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<script type="text/javascript" src="resources/storage.js"></script>
<script type="text/javascript">
/**
 * 新增库位
 * 
 */
function savePosition () {
	window.location.href = "storage/gotoSavePosition" ;
}
</script>
<style type="text/css">
#infoDiv table tr td,#infoDiv table tr th {
	border: 1px solid #98bf21;
	text-align: center;
}
</style>
<div align="center">
	<h1>库位列表</h1>
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
	<form action="storage/findPosition" method="post">
		<table>
			<tr>
				<td>库位名称:</td>
				<td>
					<input type="text" name = "label" value = "${param.label}"/>
					<input type="checkbox" name = "ifLike" value = "true" checked="checked"/>精确查找
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="查询" />
				</td>
				<td>
					<input type="button" value="新增库位" onclick="savePosition()" />
				</td>
			</tr>
		</table>
	</form>
</div>
<div style="width: 100%;" id="infoDiv" align="center">
<table id="common-table">
		<tr>
			<th>创建时间</th>
			<th>库位Id</th>
			<th>库位标签</th>
			<th>图书数量</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${pList}" var="p">
			<tr>
				<td>
					<fmt:formatDate value="${p.gmtCreate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${p.posId}
				</td>
				<td>
					${p.label}
				</td>
				<td>
					<a href="storage/findPositionSkus?posId=${p.posId}">${p.lastAmount}</a>
				</td>
				<td>
					<a href="storage/gotoUpdatePosition?posId=${p.posId}">修改</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="storage/findPosition?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="storage/findPosition?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>