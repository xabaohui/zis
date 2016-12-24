<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<script type="text/javascript">
	window.onload = function() {
		var checkedIds = document.getElementsByName("checkedId");
		var permissionIds= document.getElementsByName("permissionIds");
		for(var i = 0; i < checkedIds.length; i++){
			var checkedIdValue = checkedIds[i].value;
			for(var j = 0; j < permissionIds.length; j++){
				var permissionIdValue = permissionIds[j].value;
				if(+checkedIdValue == +permissionIdValue){
					permissionIds[j].checked=true;
				}
			}
		}
	};
</script>
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
#allPermissionDiv div{
	float:inherit;
	display: inline-table;
}
</style>
<div align="center">
	<h1 >新建角色及授权</h1>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="shiro/registRole" method="post" modelAttribute="registRoleDto" id = "registFrom">
	<div align="center">
		<table>
			<tr>
				<td>角色:</td>
				<td><input type="text" name="roleName" value="${param.roleName}"/></td>
				<td class = "showError">
				<spring:errors delimiter="," path="roleName" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>角色Code:</td>
				<td><input type="text" name="roleCode" value="${param.roleCode}" /></td>
				<td class = "showError">
				<spring:errors delimiter="," path="roleCode" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>角色描述:</td>
				<td><textarea name="roleDescription" cols="22" rows="3">${param.roleDescription}</textarea></td>
				<td class = "showError">
					<spring:errors delimiter="," path="roleDescription" cssStyle="color:red"/>
				</td>
			</tr>
		</table>
	</div>
	<div id="allPermissionDiv" style="width: 100%">
		<div style="width: 420px" id = "registDiv">
			<table >
			<tr>
			<th colspan="3" width="420px" height="40px" bgcolor="cyan">用户相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allRegist" onclick="checkAll('registList', 'check-allRegist')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${registList}" var="regist">
					<tr>
						<td width="3%">
							<input type="checkbox" name="permissionIds" class="registList" value="${regist.id}"/>
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
				<th><input type="checkbox" id="check-allBookInfo" onclick="checkAll('bookInfoList', 'check-allBookInfo')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${bookInfoList}" var="bookInfo">
					<tr>
						<td width="3%">
							<input type="checkbox" name="permissionIds" class="bookInfoList" value="${bookInfo.id}"/>
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
				<th><input type="checkbox" id="check-allPurchase" onclick="checkAll('purchaseList', 'check-allPurchase')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${purchaseList}" var="purchase">
					<tr>
						<td width="3%">
							<input type="checkbox" name="permissionIds" class="purchaseList" value="${purchase.id}"/>
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
				<th><input type="checkbox" id="check-allRequirement" onclick="checkAll('requirementList', 'check-allRequirement')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${requirementList}" var="requirement">
					<tr>
						<td width="3%">
							<input type="checkbox" name="permissionIds" class="requirementList" value="${requirement.id}"/>
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
				<th><input type="checkbox" id="check-allToolkit" onclick="checkAll('toolkitList', 'check-allToolkit')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${toolkitList}" var="toolkit">
					<tr>
						<td width="3%">
							<input type="checkbox" name="permissionIds" class="toolkitList" value="${toolkit.id}"/>
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
	</div>
	<br>
	<br>
	<div align="center">
		<input type="button" value="提交" style="font-size: 20px;font-weight:bolder;" onclick="permissionCheckedSubmit()">
	</div>
</spring:form>
<c:forEach items="${checkedIds}" var="checkedId">
	<input type="hidden" name = "checkedId" value="${checkedId}"/>
</c:forEach>

<%@ include file="/footer.jsp"%>