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
window.onload = function() {
	checkedShopId();
	checkedCreateOrderType();
// 	checkedOrderType();
};
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
	<h1>创建订单</h1>
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
	<p/>
	<div align="left" style="margin-left: 200px">
		<a href="order/gotoCreateOrder">刷新</a>
	</div>
	<div align="right" style="margin-right: 300px">
		<a href="order/gotoExcelCreateOrderUpload">批量上传</a>&nbsp;&nbsp;&nbsp;<a href="order/gotoExcelAddrToOrderUpload">地址导入</a>
	</div>
	<p/>
</div>
<p />
<div  align="center">
	<spring:form action="order/createOrder" method="post" id = "createOrderFrom" modelAttribute="dto">
<!-- 		${errors} -->
		<table>
			<tr>
				<td>选择店铺:</td>
				<td colspan="3">
					<select name = "shopId" id = "shopId" onchange="setShopIdToSession(this)" >
						<option value="">未选择</option>
						<c:forEach items="${shopList}" var="shop">
							<option value="${shop.shopId}">
								${shop.shopName}
							</option>
						</c:forEach>
					</select>
					<input type="hidden" name = "checkShopId" id = "checkShopId" value = "${dto.shopId}"/>
					<input type="hidden" name = "discount" id = "discount" value = "${dto.discount}"/>
				</td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'shopId'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>手动下单类型:</td>
				<td>
					<input type="radio" name = "createOrderType" id = "manualMyself" value = "manualMyself" onclick = "manualMyselfOrder(this)"/>
					手动自主下单
				</td>
				<td>
					<input type="radio" name = "createOrderType" id = "manualTaobao" value = "manualTaobao" onclick = "manualTaobaoOrder(this)"/>
					手动淘宝下单
					<input type="hidden" name = "manualOrderType" id = "manualOrderType" value = "${dto.manualOrderType}"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div id="orderNumberDiv" <c:if test="${!(dto.manualOrderType eq 'manualTaobao')}"> style="display: none;" </c:if>>
						订单编号:&nbsp;<input type="text" name = "outOrderNumber" id= "outOrderNumber" onchange="manualTaobaoOrderUpdate(this)"  value = "${dto.outOrderNumber}"/>
					</div>
				</td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'outOrderNumber'}">
						<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td>订单类型:</td> -->
<!-- 				<td> -->
<!-- 					<input type="radio" name = "orderType" value = "self" onclick="selectOrderType(this)"/> -->
<!-- 					自发 -->
<!-- 				</td> -->
<!-- 				<td> -->
<!-- 					<input type="radio" name = "orderType" value = "for_other" onclick="selectOrderType(this)" /> -->
<!-- 					代发 -->
<!-- 				</td> -->
<!-- 				<td> -->
<!-- 					<input type="radio" name = "orderType" value = "to_other" onclick="selectOrderType(this)" /> -->
<!-- 					委派 -->
<!-- 					<input type="hidden" name = "checkOrderType" id = "checkOrderType" value = "${dto.orderType}"/> -->
<!-- 				</td> -->
<!-- 				<td><spring:errors delimiter="," path="orderType" cssStyle="color:red"/></td> -->
<!-- 			</tr> -->
			<tr>
				<td>书名/ISBN</td>
				<td><input type="text" id = "bookNameOrIsbn" width="200px"/></td>
				<td colspan="2"><input type="button" onclick="findSkuInfoByBookNameOrIsbn()" value="查询"/></td>
			</tr>
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
			<div id = "skuInfoDiv" 	 <c:if test="${empty dto.skus}"> style="display: none;" </c:if> >
				<table id = "skuInfoTable" class = "common-table-ajax">
					<tr>
						<th>ISBN</th>
						<th>书名</th>
						<th>数量</th>
						<th>售价</th>
						<th>原价</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${dto.skus}" var="sku" varStatus="i" >
						<tr id = "skuTr_${sku.skuId}">
							<td>
								${sku.isbn}
								<input type="hidden" name = "skus[${i.index}].skuId" value = "${sku.skuId}" />
								<input type="hidden" name = "skus[${i.index}].isbn" value = "${sku.isbn}" />
							</td>
							<td>
								${sku.itemName}<input type="hidden" name = "skus[${i.index}].itemName" value = "${sku.itemName}" />
							</td>
							<td>
								<div id = "itemCountDiv_${sku.skuId}">
								${sku.itemCount}<input type="hidden" name = "skus[${i.index}].itemCount" id = "itemCount_${sku.skuId}" value = "${sku.itemCount}" />
								</div>
							</td>
							<td>
								<div id = "itemPriceDiv_${sku.skuId}">
								<fmt:formatNumber type="number" value="${sku.itemPrice}" maxFractionDigits="2"  />
								<input type="hidden" name = "skus[${i.index}].itemPrice" id = "itemPrice_${sku.skuId}" value = "${sku.itemPrice}" />
								</div>
							</td>
							<td>
								${sku.zisPrice}<br/>
								<font color="#A9A9A9" size="1px">(店铺折扣${dto.discount * 10}折)</font>
								<input type="hidden" name = "skus[${i.index}].zisPrice" id = "zisPrice_${sku.skuId}" value = "${sku.zisPrice}" />
							</td>
							<td>
								<input type="button" onclick="updateSkuItemCount('${sku.skuId}')" value="修改数量">
								<input type="button" onclick="updateSkuItemPrice('${sku.skuId}')" value="修改价格">
								<br/>
								<input type="button" onclick="deleteSubOrder('${sku.skuId}')" value="删除">
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<p/>
		<table>
			<tr>
				<td>收件人信息(淘宝格式)</td>
				<td colspan="2"><input type="text" id = "receiverInfo" style="width: 500px" onchange="splitReceiverInfo()"/></td>
			</tr>
			<tr>
				<td>收件人</td>
				<td><input type="text" id = "receiverName" name = "receiverName" value = "${dto.receiverName}" onchange="updateReceiverInfo()" /></td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'receiverName'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>收件人电话</td>
				<td><input type="text" id = "receiverPhone" name = "receiverPhone" value = "${dto.receiverPhone}" onchange="updateReceiverInfo()" /></td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'receiverPhone'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>收件人地址</td>
				<td><input type="text" id = "receiverAddr" name = "receiverAddr" style="width: 500px" value = "${dto.receiverAddr}" onchange="updateReceiverInfo()" /></td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'receiverAddr'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>快递费</td>
				<td><input type="text" id = "postage" name = "postage" value = "${dto.postage}" onchange = "updateOrderPostage()" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" /></td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'postage'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>订单总费用</td>
				<td><input type="text" id = "orderMoney" name = "orderMoney" value = '<fmt:formatNumber type="number" value="${dto.orderMoney}" maxFractionDigits="2"  />' onchange = "updateOrderMoney()" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" /></td>
				<td>
					<c:forEach items="${errors}" var = "item">
						<c:if test="${item.field eq 'orderMoney'}">
							<span><font color="red">${item.defaultMessage}</font></span>
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td>卖家备注</td>
				<td colspan="2"><textarea name = "salerRemark" cols="30" rows="5">${dto.salerRemark}</textarea></td>
			</tr>
			<tr>
				<td>买家留言</td>
				<td colspan="2"><textarea name = "buyerMessage" cols="30" rows="5">${dto.buyerMessage}</textarea></td>
			</tr>
		</table>
		<div align="right" style="margin-right: 300px">
			<input type="button" value = "提交订单" onclick="checkCreateOrderFrom()"/>
		</div>
		<input type="hidden" name = "token" value="${token}"/>
	</spring:form>
</div>
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">	
</div>

<%@ include file="/footer.jsp"%>