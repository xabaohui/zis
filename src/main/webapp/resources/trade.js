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

// 申请退款(3-1)
function showApplyRefundView(orderId) {
	if (orderId == null || orderId == '') {
		alert("订单未找到");
		return;
	}
	var selectedContent = '请填写退款说明<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>退款说明：</td><td><textarea rows="10" cols="30" id = "refundMemo"></textarea></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="applyRefund()" value="提交"/></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 申请退款(3-2)
function applyRefund() {
	var orderId = document.getElementById("modifyOrderId").value;
	var refundMemo = document.getElementById("refundMemo").value;
	orderController.applyRefund(orderId, refundMemo, applyRefundResult);
	hideShadow();
}

// 申请退款(3-3)
function applyRefundResult(data) {
	if (data.success) {
		alert("操作成功");
		$('buyerMess_' + data.orderId).innerHTML = '<font color="red">'
				+ data.buyerMessage + '</font>';
	} else {
		alert(data.failReason);
	}
}

// 备注(3-1)
function showAppendSellerRemarkView(orderId) {
	if (orderId == null || orderId == '') {
		alert("订单未找到");
		return;
	}
	var selectedContent = '请填写备注说明<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>备注：</td><td><textarea rows="10" cols="30" id = "remark"></textarea></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="appendSellerRemark()" value="提交"/></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 备注(3-2)
function appendSellerRemark() {
	var orderId = document.getElementById("modifyOrderId").value;
	var remark = document.getElementById("remark").value;
	orderController.appendSellerRemark(orderId, remark,
			appendSellerRemarkResult);
	hideShadow();
}

// 备注(3-3)
function appendSellerRemarkResult(data) {
	if (data.success) {
		alert("操作成功");
		var desc = '<span title="' + data.salerRemark
				+ '" onclick="showAppendSellerRemarkView(\'' + data.orderId
				+ '\')"><font color="red">备注</font></span>';
		$('desc_' + data.orderId).innerHTML = desc;
	} else {
		alert(data.failReason);
	}
}

// 批量分配仓库(3-1)
function queryAllStorageRepo() {
	orderController.queryStorageRepoInfo(showAllStorageRepo);
}

// 批量分配仓库(3-2)
function showAllStorageRepo(data) {
	var selectedContent = '请选择仓库<div id="selectArea"><table>';
	for ( var i = 0; i < data.length; i++) {
		selectedContent = selectedContent + '<tr><td>' + data[i].name
				+ '</td><td><input type="button" onclick="arrangeOrderToPos(\''
				+ data.repoId + '\')" value="选择"></td></tr>';
	}
	selectedContent = selectedContent
			+ '</table></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 批量分配仓库(3-3)
function arrangeOrderToPos(repoId) {
	hideShadow();
	document.getElementById("repoId").value = repoId;
	document.getElementById("orderForm").action = "order/arrangeOrderToPos";
	document.getElementById("orderForm").submit();
}

// 单个分配仓库(4-1)
function queryStorageRepoOne(orderId, forwardUrl) {
	orderController.queryStorageRepoInfoByOnlyOrderId(orderId, forwardUrl,
			queryStorageRepoOneResult);
}

// 单个分配仓库(4-2)
function queryStorageRepoOneResult(data) {
	if (data.success) {
		showStorageRepoOne(data.repoList);
	} else {
		alert(data.failReason);
	}
}

// 单个分配仓库(4-3)
function showStorageRepoOne(orderId, forwardUrl, repoList) {

	var selectedContent = '请选择仓库<div id="selectArea"><table>';
	for ( var i = 0; i < repoList.length; i++) {
		selectedContent = selectedContent
				+ '<tr><td>'
				+ repoList[i].name
				+ '</td><td><input type="button" onclick="arrangeOrderToPosOne(\''
				+ orderId + '\',\'' + forwardUrl + '\',\'' + repoList[i].repoId
				+ '\')" value="选择"></td></tr>';
	}
	selectedContent = selectedContent
			+ '</table></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 单个分配仓库(4-4)
function arrangeOrderToPosOne(orderId, forwardUrl, repoId) {
	hideShadow();
	window.location.href = "order/arrangeOrderToPos?orderId=" + orderId
			+ "&forwardUrl=" + forwardUrl + "&repoId=" + repoId;
}

// 批量取消订单
function cancelOrders() {
	if (ifCheckedOrderId()) {
		alert("请选择订单后再进行批量处理");
		return;
	} else {
		if (confirm("你确定取消吗？")) {
			document.getElementById("orderForm").action = "order/cancelOrder";
			document.getElementById("orderForm").submit();
		} else {
		}
	}
}

// 单个取消订单
function cancelOrder(orderId, forwardUrl) {
	if (confirm("你确定取消吗？")) {
		window.location.href = "order/cancelOrder?orderId=" + orderId
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
			window.location.href = "order/cancelOrder?orderId=" + orderId
					+ "&forwardUrl=" + forwardUrl + "&paymentAmount="
					+ paymentAmount;
		}
	} else {
	}
}

// 同意退款
function agreeRefund(orderId, forwardUrl) {
	var memo = prompt("请填写同意备注:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写备注");
		} else {
			window.location.href = "order/agreeRefund?orderId=" + orderId
					+ "&forwardUrl=" + forwardUrl + "&memo=" + memo;
		}
	} else {
	}
}

// 批量同意退款
function agreeRefundList() {
	var memo = prompt("请填写同意备注:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写备注");
		} else {
			document.getElementById("memo").value = memo;
			document.getElementById("orderForm").action = "order/agreeRefund";
			document.getElementById("orderForm").submit();
		}
	} else {
	}
}

// 取消退款
function cancelRefund(orderId, forwardUrl) {
	var memo = prompt("请填写取消原因:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写取消原因");
		} else {
			window.location.href = "order/cancelRefund?orderId=" + orderId
					+ "&forwardUrl=" + forwardUrl + "&memo=" + memo;
		}
	} else {
	}
}

// 批量取消退款
function cancelRefundList() {
	var memo = prompt("请填写取消原因:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写取消原因");
		} else {
			document.getElementById("memo").value = memo;
			document.getElementById("orderForm").action = "order/cancelRefund";
			document.getElementById("orderForm").submit();
		}
	} else {
	}
}

// 单个配货
function pickingUpOrder(orderId, forwardUrl) {
	window.location.href = "order/pickingUpOrder?orderId=" + orderId
			+ "&forwardUrl=" + forwardUrl;

}

// 批量配货
function pickingUpOrderList() {
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
		document.getElementById("orderForm").action = "order/pickingUpOrder";
		document.getElementById("orderForm").submit();
	}
}

// 单个取消配货
function cancelArrangeOrder(orderId, forwardUrl) {
	var memo = prompt("请填写取消原因:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写取消原因");
		} else {
			window.location.href = "order/cancelArrangeOrder?orderId="
					+ orderId + "&forwardUrl=" + forwardUrl + "&memo=" + memo;
		}
	} else {
	}
}

// 批量取消配货
function cancelArrangeOrderList() {
	var memo = prompt("请填写取消原因:", "");
	if (memo) {
		if (memo == null || memo.replace(/(^s*)|(s*$)/g, "").length == 0) {
			alert("请填写取消原因");
		} else {
			document.getElementById("memo").value = memo;
			document.getElementById("orderForm").action = "order/cancelArrangeOrder";
			document.getElementById("orderForm").submit();
		}
	} else {
	}
}

// 单个缺货
function lackness(orderId, forwardUrl) {
	if (confirm("确定缺货吗？")) {
		window.location.href = "order/lackness?orderId=" + orderId
				+ "&forwardUrl=" + forwardUrl;
	} else {
	}
}

// 批量缺货
function lacknessList() {
	if (confirm("确定缺货吗？")) {
		document.getElementById("orderForm").action = "order/lackness";
		document.getElementById("orderForm").submit();
	} else {
	}
}

// 回填单号(3-1)
function showfillExpressNumber(orderId) {
	var selectedContent = '回填单号<div id="selectArea"><table>';
	selectedContent = selectedContent
			+ '<tr><td>快递公司</td><td><input type="text" id = "expressCompany" value="'
			+ receiverName + '"></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td>快递单号</td><td><input type="text" id = "expressNumber" value="'
			+ receiverPhone + '"></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td colspan="2"><input type="button" onclick="modifyFillExpressNumber()" value="提交"></td></tr>';
	selectedContent = selectedContent
			+ '</table><input type="hidden" id = "modifyOrderId" value="'
			+ orderId
			+ '"></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}

// 回填单号(3-2)
function modifyFillExpressNumber() {
	var orderId = document.getElementById("modifyOrderId").value;
	var expressNumber = document.getElementById("expressNumber").value;
	var expressCompany = document.getElementById("expressCompany").value;
	orderController.fillExpressNumber(orderId, expressNumber, expressCompany,
			modifyFillExpressNumberResult);
	hideShadow();
}

// 回填单号(3-3)
function modifyFillExpressNumberResult(dto) {
	if (dto.success) {
		alert("操作成功");
		$('express_' + dto.orderId).innerHTML = dto.expressCompany + "<br/>"
				+ dto.expressNumber;
	} else {
		alert(dto.failMessage);
	}
}

// 打印快递单(批量)
function printExpressList() {
	if (ifCheckedOrderId()) {
		alert("请选择订单");
		return;
	}
	var forwardUrl = document.getElementById("forwardUrl").value;
	if (confirm("确定打印快递单？")) {
		document.getElementById("orderForm").action = "order/printExpress";
		document.getElementById("orderForm").submit();
		setTimeout('myrefresh()', 100);
	} else {
	}
}

// 打印快递单(单个)
function printExpress(orderId, forwardUrl) {
	if (confirm("确定打印快递单？")) {
//		var audioPlay = document.getElementById("audioPlay");
//		audioPlay.play();
		window.open("order/printExpress?orderId=" + orderId + "&forwardUrl="
				+ forwardUrl);
		setTimeout('myrefresh()', 100);
	} else {
	}
}

// 刷新
function myrefresh() {
	window.location.reload();
}

// 判断是否选中
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

// 全选
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

// 用户取消选择一码多书
function cancelSelection() {
	// 关闭浮出层
	hideShadow();
}
