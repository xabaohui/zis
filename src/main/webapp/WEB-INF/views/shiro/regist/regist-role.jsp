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
	<p/>
	<div id="common-table" style="width: 100%">
		<div id = "registDiv">
			<table>
			<tr>
			<th colspan="3">用户相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allRegist" onclick="checkAll('registList', 'check-allRegist')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${registList}" var="regist">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="registList" value="${regist.id}"/>
						</td>
						<td>
							${regist.permissionName}
						</td>
						<td>
							${regist.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "bookInfoDiv">
			<table>
			<tr>
			<th colspan="3">图书相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allBookInfo" onclick="checkAll('bookInfoList', 'check-allBookInfo')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${bookInfoList}" var="bookInfo">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="bookInfoList" value="${bookInfo.id}"/>
						</td>
						<td>
							${bookInfo.permissionName}
						</td>
						<td>
							${bookInfo.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "purchaseDiv">
			<table>
			<tr>
			<th colspan="3">采购相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allPurchase" onclick="checkAll('purchaseList', 'check-allPurchase')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${purchaseList}" var="purchase">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="purchaseList" value="${purchase.id}"/>
						</td>
						<td>
							${purchase.permissionName}
						</td>
						<td>
							${purchase.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "requirementDiv">
			<table>
			<tr>
			<th colspan="3">院校相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allRequirement" onclick="checkAll('requirementList', 'check-allRequirement')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${requirementList}" var="requirement">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="requirementList" value="${requirement.id}"/>
						</td>
						<td>
							${requirement.permissionName}
						</td>
						<td>
							${requirement.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "toolkitDiv">
			<table>
			<tr>
			<th colspan="3">系统相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allToolkit" onclick="checkAll('toolkitList', 'check-allToolkit')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${toolkitList}" var="toolkit">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="toolkitList" value="${toolkit.id}"/>
						</td>
						<td>
							${toolkit.permissionName}
						</td>
						<td>
							${toolkit.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "stockDiv">
			<table>
			<tr>
			<th colspan="3">库存相关权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allStock" onclick="checkAll('stockList', 'check-allStock')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${stockList}" var="stock">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="stockList" value="${stock.id}"/>
						</td>
						<td>
							${stock.permissionName}
						</td>
						<td>
							${stock.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p/>
		<div id = "dataDiv">
			<table>
			<tr>
			<th colspan="3">数据处理权限</th>
			</tr>
			<tr>
				<th><input type="checkbox" id="check-allData" onclick="checkAll('dataList', 'check-allData')" />全选</th>
				<th>权限名称</th>
				<th>权限描述</th>
			</tr>
				<c:forEach items="${dataList}" var="datas">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="dataList" value="${datas.id}"/>
						</td>
						<td>
							${datas.permissionName}
						</td>
						<td>
							${datas.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id = "shopDiv">
			<table>
			<tr>
			<th colspan="3">公司店铺管理权限</th>
			</tr>
			<tr>
				<td><input type="checkbox" id="check-allShop" onclick="checkAll('shopList', 'check-allShop')" />全选</td>
				<td>权限名称</td>
				<td>权限描述</td>
			</tr>
				<c:forEach items="${shopList}" var="shop">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="shopList" value="${shop.id}"/>
						</td>
						<td>
							${shop.permissionName}
						</td>
						<td>
							${shop.permissionDescription}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div id = "orderDiv">
			<table>
			<tr>
			<th colspan="3">订单及库存管理权限</th>
			</tr>
			<tr>
				<td><input type="checkbox" id="check-allOrder" onclick="checkAll('orderList', 'check-allOrder')" />全选</td>
				<td>权限名称</td>
				<td>权限描述</td>
			</tr>
				<c:forEach items="${orderList}" var="order">
					<tr>
						<td>
							<input type="checkbox" name="permissionIds" class="orderList" value="${order.id}"/>
						</td>
						<td>
							${order.permissionName}
						</td>
						<td>
							${order.permissionDescription}
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