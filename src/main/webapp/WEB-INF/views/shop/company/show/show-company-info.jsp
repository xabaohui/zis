<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<script type="text/javascript" src='resources/regist.js'></script>
<style type="text/css">
</style>
<script type="text/javascript">
	function updateShop(shopId) {
		window.location.href = "shop/gotoUpdateShop?shopId=" + shopId;
	}
	
	function registShop() {
		window.location.href = "shop/gotoSaveShop";
	}

	function updateCompany() {
		window.location.href = "shop/gotoUpdateUserCompany";
	}
	
	function updateStock(repoId) {
		window.location.href = "storage/gotoUpdateStorageRepoInfo?repoId=" + repoId;
	}
</script>
<div>
	<h1>公司管理</h1>
	<br />
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
<div id="common-table">
	<table>
		<tr>
			<th colspan="5">公司管理</th>
		</tr>
		<tr>
			<td>公司名称</td>
			<td>联系人</td>
			<td>联系人手机</td>
			<td>公司地址</td>
			<td>操作</td>
		</tr>
		<tr>
			<td>${company.companyName}</td>
			<td>${company.contacts}</td>
			<td>${company.mobile}</td>
			<td>${company.address}</td>
			<td><input type="button" value="修改" onclick="updateCompany()" /></td>
		</tr>
	</table>
	<p />
	<table>
		<tr>
			<th colspan="3">店铺管理</th>
			<th><input type="button" value="新增店铺" onclick="registShop()" />
			</th>
		</tr>
		<tr>
			<td>店铺名称</td>
			<td>平台名称</td>
			<td>E-Mail</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${shopList}" var="shop">
			<tr>
				<td>${shop.shopName}</td>
				<td>
					<c:if test="${'youzan' eq shop.pName}">有赞</c:if> 
					<c:if test="${'taobao' eq shop.pName}">淘宝</c:if>
				</td>
				<td>${shop.emails}</td>
				<td>
					<input type="button" value="管理商品" onclick="queryShopItemMapping('${shop.shopId}')" />
					&nbsp; &nbsp;
					<input type="button" value="修改信息" onclick="updateShop('${shop.shopId}')" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<p />
	<table>
		<tr>
			<th colspan="3">仓库管理</th>
		</tr>
		<tr>
			<td>仓库名称</td>
			<td>操作</td>
		</tr>
			<tr>
				<td>${stock.name}</td>
				<td>
					<input type="button" value="修改信息" onclick="updateStock('${stock.repoId}')" />
				</td>
			</tr>
	</table>
	<p />
	<table>
		<tr>
			<th colspan="3">员工管理</th>
		</tr>
		<tr>
			<td>用户名</td>
			<td>员工姓名</td>
			<td>角色名称</td>
		</tr>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>${user.user.userName}</td>
				<td>${user.user.realName}</td>
				<td>${user.role.roleName}</td>
			</tr>
		</c:forEach>
	</table>
</div>


<%@ include file="/footer.jsp"%>