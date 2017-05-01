<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/shop.js'></script>
<script type="text/javascript">
window.onload = function() {
		checkStatus();
};
</script>
<style type="text/css">
</style>
<div align="center">
	 <h1>店铺商品管理</h1> 
	<br/>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
	<p/>
	<h2>
		<font color="red">${notResult}</font>
	</h2>
	<p/>
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
</div>
<p />
<div align="center" >
	<form action="shop/queryShopItemMapping">
	<table>
		<tr>
			<td>ISBN</td>
			<td><input name = "isbn" type="text" value = "${param.isbn}"/></td>
		</tr>
		<tr>
			<td>店铺名称</td>
			<td>
				<select name = "shopId" >
					<c:forEach items="${shopList}" var="shop">
						<option value="${shop.shopId}" <c:if test="${shop.shopId eq shopId}"> selected="selected" </c:if>>
						${shop.shopName}
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>商品状态</td>
			<td>
				<select id = "systemStatus" name = "status" >
					<option value = "wait">等待</option>
					<option value = "fail">失败</option>
					<option value = "success">成功</option>
					<option value = "delete">网店删除</option>
					<option value = "processing">处理中</option>
				</select>
				<input type="hidden" id = "mappingStatus" value = "${mappingStatus}"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit"  value="查询"></td>
		</tr>
	</table>
	</form>
	<p/>
	<p/>
	<form action="" method="post" id = "mForm" >
		<table id ="common-table">
		<tr>
			<th>
				<input type="checkbox" id = "checkAll" onclick = "checkAllmId()"/>全选
			</th>
			<th>商品标题</th>
			<th>商家编码</th>
			<th>宝贝ID</th>
			<c:if test ="${'fail' eq mappingStatus}">
				<th>失败原因</th>
			</c:if>
			<c:if test ="${'wait' eq mappingStatus || 'fail' eq mappingStatus}">
				<th>设置</th>
			</c:if>
		</tr>
		<c:forEach items="${mappingList}" var="mapping">
			<tr>
				<td>
					<input name = "mId" type="checkbox" value="${mapping.id}"/>
				</td>	
				<td>
					${mapping.title}
				</td>	
				<td>
					${mapping.itemOutNum}
				</td>	
				<td>
					${mapping.pItemId}
				</td>
				<c:if test ="${'fail' eq mappingStatus}">
					<td>
						${mapping.failReason}
					</td>
				</c:if>	
				<c:if test ="${'wait' eq mappingStatus}">
					<td>
						<input type="button" onclick="addMIdToMapping('${shopId}','${mapping.id}','${token}','${mappingStatus}')" value = "发布"/>
					</td>
				</c:if>	
				<c:if test ="${'fail' eq mappingStatus}">
					<td>
						<input type="button" onclick="failAddMIdToMapping('${shopId}','${mapping.id}','${token}','${mappingStatus}')" value = "发布"/>
					</td>
				</c:if>	
				</tr>
		</c:forEach>
		<c:if test="${not empty mappingList}">
			<c:if test ="${'wait' eq mappingStatus}">
				<tr>
					<td colspan="5" align="left">
						<input type="button" value= "批量发布" onclick="verifySubmit()" />
					</td>
				</tr>
			</c:if>
			<c:if test ="${'fail' eq mappingStatus}">
				<tr>
					<td colspan="6" align="left">
						<input type="button" value= "批量发布" onclick="failVerifySubmit()" />
					</td>
				</tr>
			</c:if>
		</c:if>
		</table>
		<input type="hidden" name = "token" value = "${token}">
		<input type="hidden" name = "shopId" value = "${shopId}">
		<input type="hidden" id = "mappingStatus" value = "${mappingStatus}"/>
	</form>
</div>
<div align="center">
	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="shop/queryShopItemMapping?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page} &nbsp;
	<c:if test="${not empty nextPage}">
		<a href="shop/queryShopItemMapping?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>

<div align="left">
<table>
	<tr>
		<td width="10%">
			</td>
		<td align="left">
			<c:if test = "${shopPName eq 'youzan'}">
			 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			</c:if>
			<c:if test = "${shopPName eq 'taobao'}">
				<input type = "button" onclick="taobaoDownloadItem2zis('${shopId}','${token}')" value = "网店数据更新至zis">	
			</c:if>
		</td>
		<td width="70%">
		</td>
		<td align="right">
			<c:if test ="${'wait' eq mappingStatus}">
				<input type="button" value= "全部发布" onclick="addAllItems('${shopId}','${token}','${mappingStatus}')" />
			</c:if>
			<c:if test ="${'fail' eq mappingStatus}">
				<input type="button" value= "全部发布" onclick="failAddAllItems('${shopId}','${token}','${mappingStatus}')" />
			</c:if>
		</td>
		<td width="10%">
		</td>
	</tr>
</table>
</div>

<%@ include file="/footer.jsp"%>