// 改地址(3-1)
function showOrderAddress(orderId, receiverName, receiverPhone, receiverAddr) {
	var selectedContent = '修改收件人相关信息<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>收件人：</td><td><input type="text" id = "receiverName" value="'
			+ receiverName + '"></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td>收件人电话：</td><td><input type="text" id = "receiverPhone" value="'
			+ receiverPhone + '"></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td>收件人地址：</td><td><input type="text" id = "receiverAddr" value="'
			+ receiverAddr + '"></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="modifyOrderAddress()" value="提交"></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 改地址(3-2)
function modifyOrderAddress() {
	var orderId = document.getElementById("modifyOrderId").value;
	var receiverName = document.getElementById("receiverName").value;
	var receiverPhone = document.getElementById("receiverPhone").value;
	var receiverAddr = document.getElementById("receiverAddr").value;
	orderController.changeOrderAddress(orderId, receiverName, receiverPhone,
			receiverAddr, modifyOrderAddressResult);
	hideShadow();
}

// 改地址(3-3)
function modifyOrderAddressResult(dto) {
	if (dto.success) {
		alert("操作成功");
		$('address_' + dto.orderId).innerHTML = dto.receiverName;
	} else {
		alert(dto.failMessage);
	}
}

// 拦截订单(4-1)
function ifBlockOrder(orderId) {
	if (confirm("你确定拦截此订单么")) {
		showBlockOrderView(orderId);
	} else {
	}
}

// 拦截订单(4-2)
function showBlockOrderView(orderId) {
	if (orderId == null || orderId == '') {
		alert("订单未找到");
		return;
	}
	var selectedContent = '拦截原因填写<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>拦截原因：</td><td><textarea rows="10" cols="30" id = "blockReason"></textarea></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="blockOrder()" value="提交"/></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 拦截订单(4-3)
function blockOrder() {
	var orderId = document.getElementById("modifyOrderId").value;
	var blockReason = document.getElementById("blockReason").value;
	if (blockReason == null || blockReason == '') {
		alert("请填写拦截原因");
		return;
	}
	orderController.blockOrder(orderId, blockReason, blockOrderResult);
	hideShadow();
}

// 拦截订单(4-4)
function blockOrderResult(dto) {
	if (dto.success) {
		alert("操作成功");
		$('blockFlag_' + dto.orderId).innerHTML = '<span title="'
				+ dto.blockReason + '"><font color="red">已拦截</font></span>';
	} else {
		alert(dto.failMessage);
	}
}

// 用户取消选择一码多书
function cancelSelection() {
	// 关闭浮出层
	hideShadow();
}

function checkAllOId() {
	var orderIds = document.getElementsByName('orderId');
	var checkAll = document.getElementById('checkAll');
	for ( var i = 0; i < orderIds.length; i++) {
		if (checkAll.checked) {
			orderIds[i].checked = true;
		} else {
			orderIds[i].checked = false;
		}
	}
}

// 批量取消订单
function cancelOrders() {
	if (ifCheckedOrderId) {
		alert("请选择订单后再进行批量处理");
		return;
	} else {
		if (confirm("你确定取消吗？")) {
			document.getElementById("orderForm").action = "trade/cancelOrder";
			document.getElementById("orderForm").submit();
		} else {
		}
	}
}

// 单个取消订单
function cancelOrder(orderId, forwardUrl) {
	if (confirm("你确定取消吗？")) {
		window.location.href = "trade/cancelOrder?orderId=" + orderId
				+ "&forwardUrl=" + forwardUrl;
	} else {
	}
}

// 单个支付订单
function payOrder(orderId, forwardUrl) {
	var paymentAmount = prompt("支付金额:", "");
	var re = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if (paymentAmount) {
		if (!re.test(paymentAmount)) {
			alert("请输入正确金额");
		} else {
			window.location.href = "trade/cancelOrder?orderId=" + orderId
					+ "&forwardUrl=" + forwardUrl + "&paymentAmount="
					+ paymentAmount;
		}
	} else {
	}
}

//申请退款()
function showApplyRefundView(orderId,forwardUrl) {
	if (orderId == null || orderId == '') {
		alert("订单未找到");
		return;
	}
	var selectedContent = '请填写退款说明<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>退款说明：</td><td><textarea rows="10" cols="30" id = "blockReason"></textarea></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="applyRefund(\''+forwardUrl+'\')" value="提交"/></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

function applyRefund(forwardUrl){
	
}

function ifCheckedOrderId() {
	var oIds = document.getElementsByName('orderId');
	var checkedEmpty = true;
	for ( var i = 0; i < oIds.length; i++) {
		if (oIds[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	return checkedEmpty;
}

/**
 * 清除所有查询框
 */
function clearAll() {
	var list = document.getElementsByTagName('input');
	for ( var i = 0; i < list.length; i++) {
		if (list[i].getAttribute("type") == "text") {
			list[i].value = "";
		}
	}
}

// 批量配货
function xxxxxxxxxxxxx() {
	var oIds = document.getElementsByName('orderId');
	var checkedEmpty = true;
	for ( var i = 0; i < oIds.length; i++) {
		if (oIds[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	if (checkedEmpty) {
		alert("请选择订单后再进行批量处理");
		return;
	} else {
		document.getElementById("sendForm").action = "storage/pickingUpOrder";
		document.getElementById("sendForm").submit();
	}
}

// 批量取消
function xxxxxxx() {
	var oIds = document.getElementsByName('orderId');
	var checkedEmpty = true;
	for ( var i = 0; i < oIds.length; i++) {
		if (oIds[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	if (checkedEmpty) {
		alert("请选择订单后再进行批量处理");
		return;
	} else {
		if (confirm("你确定取消吗？")) {
			document.getElementById("sendForm").action = "storage/cancelOrder";
			document.getElementById("sendForm").submit();
		} else {
		}
	}
}

/**
 * 部分缺货
 */
function lackPart() {
	var amount = prompt("已取图书数量:", "");
	var re = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if (amount) {
		if (!re.test(amount)) {
			alert("请输入正整数");
		} else {
			document.getElementById('actualAmt').value = amount;
			document.getElementById('takeGoodsForm').action = "storage/lackPart";
			document.getElementById('takeGoodsForm').submit();
		}
	} else {

	}
}
