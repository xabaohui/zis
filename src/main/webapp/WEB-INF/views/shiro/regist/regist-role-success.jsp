<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<style type="text/css">
#registDiv table tr td, #registDiv table tr th{
	z-index: 1;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#bookInfoDiv table tr td, #bookInfoDiv table tr th{
	z-index: 2;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#purchaseDiv table tr td, #purchaseDiv table tr th{
	z-index: 3;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#requirementDiv table tr td, #requirementDiv table tr th{
	z-index: 4;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#toolkitDiv table tr td, #toolkitDiv table tr th{
	z-index: 5;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#stockDiv table tr td, #stockDiv table tr th{
	z-index: 6;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#dataDiv table tr td, #dataDiv table tr th{
	z-index: 7;
	font-size:1em;
	border:1px solid #98bf21;
	text-align: center;
}
#allPermissionDiv div{
	float:inherit;
	display: inline-table;
}
</style>
<div align="center">
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
	<div align="center">
		<table>
			<tr>
				<td>使用者角色:</td>
				<td><input type="text" name="roleName" value="${param.roleName}" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>角色Code:</td>
				<td><input type="text" name="roleCode" value="${param.roleCode}" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>角色描述:</td>
				<td><textarea name="roleDescription" cols="22" rows="3" readonly="readonly">${param.roleDescription}</textarea></td>
			</tr>
		</table>
	</div>
	<div id="allPermissionDiv" style="width: 100%">
		<div style="width: 420px" id = "registDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">用户权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${registList}" var="regist">
					<tr>
						<td width="3%">
							${regist.id}
						</td>
						<td width="6%">
							${regist.permissionName}
						</td>
						<td width="13%">
							${regist.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "bookInfoDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">图书相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${bookInfoList}" var="bookInfo">
					<tr>
						<td width="3%">
							${bookInfo.id}
						</td>
						<td width="6%">
							${bookInfo.permissionName}
						</td>
						<td width="13%">
							${bookInfo.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "purchaseDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">采购相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${purchaseList}" var="purchase">
					<tr>
						<td width="3%">
							${purchase.id}
						</td>
						<td width="6%">
							${purchase.permissionName}
						</td>
						<td width="13%">
							${purchase.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "requirementDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">院校相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${requirementList}" var="requirement">
					<tr>
						<td width="3%">
							${requirement.id}
						</td>
						<td width="6%">
							${requirement.permissionName}
						</td>
						<td width="13%">
							${requirement.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "toolkitDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">系统相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${toolkitList}" var="toolkit">
					<tr>
						<td width="3%">
							${toolkit.id}
						</td>
						<td width="6%">
							${toolkit.permissionName}
						</td>
						<td width="13%">
							${toolkit.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "stockDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">库存相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${stockList}" var="stock">
					<tr>
						<td width="3%">
							${stock.id}
						</td>
						<td width="6%">
							${stock.permissionName}
						</td>
						<td width="13%">
							${stock.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div style="width: 420px" id = "dataDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">数据相关权限</th>
			</tr>
			<tr>
				<th>id</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${dataList}" var="data">
					<tr>
						<td width="3%">
							${data.id}
						</td>
						<td width="6%">
							${data.permissionName}
						</td>
						<td width="13%">
							${data.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div align="center">
		<a href="goIndex">返回首页</a>
	</div>
	<br>
	<br>
<%@ include file="/footer.jsp"%>