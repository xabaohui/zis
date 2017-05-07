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
		$('address_' + dto.id).innerHTML = dto.receiverName;
	} else {
		alert(dto.failReason);
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
		$('blockFlag_' + dto.id).innerHTML = '<span title="'
				+ dto.blockReason + '"><font color="red">已拦截</font></span>';
	} else {
		alert(dto.failReason);
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
		$('buyerMess_' + data.id).innerHTML = '<font color="red">'
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
				+ '" onclick="showAppendSellerRemarkView(\'' + data.id
				+ '\')"><font color="red">备注</font></span>';
		$('desc_' + data.id).innerHTML = desc;
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
				+ data[i].repoId + '\')" value="选择"></td></tr>';
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
		showStorageRepoOne(data.orderId, data.forwardUrl, data.repoList);
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
			window.location.href = "order/payOrder?orderId=" + orderId
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
			+ '<tr><td>快递公司</td><td><input type="text" id = "expressCompany" value=""></td></tr>';
	selectedContent = selectedContent
			+ '<tr><td>快递单号</td><td><input type="text" id = "expressNumber" value=""></td></tr>';
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
		$('express_' + dto.id).innerHTML = dto.expressCompany + "<br/>"
				+ dto.expressNumber;
	} else {
		alert(dto.failReason);
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
		// var audioPlay = document.getElementById("audioPlay");
		// audioPlay.play();
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

// ----------------------------------------创建订单功能---------------------------------------

// 手动自主下单(2-1)
function manualMyselfOrder(data) {
	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	orderController
			.getManualOutOrderNumber(data.value, manualMyselfOrderResult);
}

// 手动自主下单(2-2)
function manualMyselfOrderResult(data) {
	if (data.success) {
		document.getElementById('manualOrderType').value = data.manualOrderType;
		document.getElementById('outOrderNumber').value = data.outOrderNumber;
		document.getElementById('orderNumberDiv').style = "display:none;";
	} else {
		alert(data.failReason);
	}
}

// 手动淘宝下单(1-1)
function manualTaobaoOrder(data) {
	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	document.getElementById('manualOrderType').value = data.value;
	document.getElementById('outOrderNumber').value = "";
	document.getElementById('orderNumberDiv').style = "display:block;";
}

// 检查淘宝订单号并传入session(2-1)
function manualTaobaoOrderUpdate(data) {
	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	// TODO 此处要加入传入店铺Id
	var manualOrderType = document.getElementById('manualOrderType').value;
	var shopId = document.getElementById('shopId').value;
	orderController.getManualTaobaoOrderOutOrderNumber(manualOrderType,
			data.value, shopId, manualTaobaoOrderUpdateResult);
}

// 检查淘宝订单号并传入session(2-2)
function manualTaobaoOrderUpdateResult(data) {
	if (!data.success) {
		alert(data.failReason);
	}
}

// 检查订单类型并传入session(2-1)
function selectOrderType(data) {

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}

	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}
	orderController.setOrderType(data.value, selectOrderTypeResult);
}

// 检查订单类型并传入session(2-2)
function selectOrderTypeResult(data) {
	if (data.success) {
		document.getElementById('checkOrderType').value = data.orderType;
	} else {
		alert(data.failReason);
	}
}

// 将店铺Id传入session(2-1)
function setShopIdToSession(data) {
	orderController.setShopIdToSession(data.value, setShopIdToSessionResult);
}

// 检查订单类型并传入session(2-2)
function setShopIdToSessionResult(data) {
	if (data.success) {
		document.getElementById('checkShopId').value = data.shopId;
		document.getElementById('discount').value = data.discount;
	} else {
		alert(data.failReason);
	}
}

// 查询图书(5-1)
function findSkuInfoByBookNameOrIsbn() {
	var bookNameOrIsbn = document.getElementById('bookNameOrIsbn').value;
	var discount = document.getElementById('discount').value;
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	orderController.findSkuInfoByBookNameOrIsbn(bookNameOrIsbn, discount,
			findSkuInfoByBookNameOrIsbnResult);
}
// 查询图书(5-2) 返回查询结果显示
function findSkuInfoByBookNameOrIsbnResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var selectedContent = '修改收件人相关信息<div id="selectArea"><table class = "common-table-ajax">';
	selectedContent = selectedContent
			+ '<tr><th>ISBN</th><th>书名</th><th>版次</th><th>作者</th><th>出版社</th><th>可用量</th><th>操作</th></tr>';
	for ( var i = 0; i < data.skuList.length; i++) {
		selectedContent = selectedContent
				+ '<tr><td>'
				+ data.skuList[i].isbn
				+ '</td><td>'
				+ data.skuList[i].bookName
				+ '</td><td>'
				+ data.skuList[i].bookEdition
				+ '</td><td>'
				+ data.skuList[i].bookAuthor
				+ '</td><td>'
				+ data.skuList[i].bookPublisher
				+ '</td><td>'
				+ data.skuList[i].bookAmount
				+ '</td><td><input type="button" onclick = "selectBookRequirementAmount(\''
				+ data.skuList[i].id + '\',\'' + data.skuList[i].isbn + '\',\''
				+ data.skuList[i].bookName + '\',\''
				+ data.skuList[i].bookPrice + '\',\''
				+ data.skuList[i].bookAmount + '\')" value = "选择" /></td></tr>';

	}
	selectedContent = selectedContent
			+ '</table></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
	$('float-to-be-show').innerHTML = selectedContent;
	showShadow();
}
// 查询图书(5-3) 选择图书并输入数量
function selectBookRequirementAmount(skuId, isbn, itemName, itemPrice,
		availableAmount) {
	var amount = prompt("需要图书数量:", "");
	var re = /^[0-9]*[1-9][0-9]*$/;
	if (amount) {
		if (!re.test(amount)) {
			alert("请输入正整数");
		} else {
			if (+amount > +availableAmount) {
				alert("需求量大于可用数量");
				return;
			}
			orderController.addSkuInfoToSession(skuId, isbn, itemName, amount,
					itemPrice, selectBookRequirementAmountResult);
		}
	} else {

	}
	hideShadow();
}

// 查询图书(5-4) 写入页面，并增加订单金额
function selectBookRequirementAmountResult(data) {
	if (data.success) {
		document.getElementById('skuInfoDiv').style = "display:block;";
		var orderMoney = data.orderMoney;
		document.getElementById('orderMoney').value = +orderMoney.toFixed(2);
		createTableNewTrOrTd(data.skuOld);
	} else {
		alert(data.failReason);
	}
}

// 查询图书(5-5) 创建table新的行
function createTableNewTrOrTd(newData) {
	var objTable = document.getElementById('skuInfoTable');
	var objTR = objTable.insertRow();
	objTR.id = "skuTr_" + newData.skuId;
	var objTD1 = objTR.insertCell();
	var objTD2 = objTR.insertCell();
	var objTD3 = objTR.insertCell();
	var objTD4 = objTR.insertCell();
	var objTD5 = objTR.insertCell();
	var price = newData.itemPrice;
	price = +price.toFixed(2);
	objTD1.innerHTML = newData.isbn + '<input type="hidden" name = "skus['
			+ newData.resultInt + '].skuId" value = "' + newData.skuId
			+ '" /> <input type="hidden" name = "skus[' + newData.resultInt
			+ '].isbn" value = "' + newData.isbn + '" />';
	objTD2.innerHTML = newData.itemName + '<input type="hidden" name = "skus['
			+ newData.resultInt + '].itemName" value = "' + newData.itemName
			+ '" />';
	objTD3.innerHTML = '<div id = "itemCountDiv_' + newData.skuId + '"> '
			+ newData.itemCount + '<input type="hidden" name = "skus['
			+ newData.resultInt + '].itemCount" id = "itemCount_'
			+ newData.skuId + '" value = "' + newData.itemCount + '" /> </div>';
	objTD4.innerHTML = '<div id = "itemPriceDiv_' + newData.skuId + '"> '
			+ price + ' <input type="hidden" name = "skus[' + newData.resultInt
			+ '].itemPrice" id = "itemPrice_' + newData.skuId + '" value = "'
			+ price + '" /></div>';
	objTD5.innerHTML = '<input type="button" onclick="updateSkuItemCount(\''
			+ newData.skuId + '\')" value="修改数量"> '
			+ ' <input type="button" onclick="updateSkuItemPrice(\''
			+ newData.skuId + '\')" value="修改价格">'
			+ ' <br/> <input type="button" onclick="deleteSubOrder(\''
			+ newData.skuId + '\')" value="删除">';
}

// 修改商品数量(2-1)
function updateSkuItemCount(skuId) {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var amount = prompt("需要图书数量:", "");
	var re = /^[0-9]*[1-9][0-9]*$/;
	if (amount) {
		if (!re.test(amount)) {
			alert("请输入正整数");
		} else {
			orderController.updateSkuItemCount(skuId, amount,
					updateSkuItemCountResult);
		}
	} else {

	}
}

// 修改商品数量(2-2)
function updateSkuItemCountResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var orderMoney = data.orderMoney;
	document.getElementById("orderMoney").value = +orderMoney.toFixed(2);
	$('itemCountDiv_' + data.skuOld.skuId).innerHTML = data.skuOld.itemCount
			+ '<input type="hidden" name = "skus[' + data.skuOld.resultInt
			+ '].itemCount" id = "itemCount_' + data.skuOld.skuId
			+ '" value = "' + data.skuOld.itemCount + '" />';
}

// 修改商品价格(2-1)
function updateSkuItemPrice(skuId) {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var price = prompt("请输入金额:", "");
	var re = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if (price) {
		if (!re.test(price)) {
			alert("请输入正确数字");
		} else {
			orderController.updateSkuItemPrice(skuId, price,
					updateSkuItemPriceResult);
		}
	} else {

	}
}

// 修改商品价格(2-2)
function updateSkuItemPriceResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var orderMoney = data.orderMoney;
	document.getElementById("orderMoney").value = +orderMoney.toFixed(2);
	var price = data.skuOld.itemPrice;
	price = +price.toFixed(2);
	$('itemPriceDiv_' + data.skuOld.skuId).innerHTML = price
			+ '<input type="hidden" name = "skus[' + data.skuOld.resultInt
			+ '].itemCount" id = "itemCount_' + data.skuOld.skuId
			+ '" value = "' + price + '" />';
}

// 删除商品(2-1)
function deleteSubOrder(skuId) {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	if (confirm("你确定删除此商品?")) {
		orderController.deleteSubOrder(skuId, deleteSubOrderResult);
	} else {
	}
}

// 删除商品(2-2)
function deleteSubOrderResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var orderMoney = data.orderMoney;
	document.getElementById("orderMoney").value = +orderMoney.toFixed(2);
	if (+orderMoney == 0) {
		document.getElementById("postage").value = +orderMoney.toFixed(2);
	}
	var tr = document.getElementById("skuTr_" + data.skuOld.skuId);
	tr.parentNode.removeChild(tr);
}

// 分隔字符串(淘宝格式)并写入页面及session
function splitReceiverInfo() {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var receiverInfo = document.getElementById("receiverInfo").value;
	var strs = new Array();
	strs = receiverInfo.split("，");
	document.getElementById("receiverName").value = strs[0];
	document.getElementById("receiverPhone").value = strs[1];
	document.getElementById("receiverAddr").value = strs[3];
	updateReceiverInfo();
}

// 收件人信息更新(2-1)
function updateReceiverInfo() {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var receiverName = document.getElementById("receiverName").value;
	var receiverPhone = document.getElementById("receiverPhone").value;
	var receiverAddr = document.getElementById("receiverAddr").value;
	orderController.updateReceiverInfo(receiverName, receiverPhone,
			receiverAddr, updateReceiverInfoResult);
}

// 收件人信息更新(2-2)
function updateReceiverInfoResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	document.getElementById("receiverName").value = data.receiverName;
	document.getElementById("receiverPhone").value = data.receiverPhone;
	document.getElementById("receiverAddr").value = data.receiverAddr;
}

// 修改邮费(2-1)
function updateOrderPostage() {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var postage = document.getElementById("postage").value;
	var re = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if (!re.test(postage)) {
		alert("请输入正确数字");
	} else {
		orderController.updateOrderPostage(postage, updateOrderPostageResult);
	}
}

// 修改邮费(2-2)
function updateOrderPostageResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var orderMoney = data.orderMoney;
	document.getElementById("orderMoney").value = +orderMoney.toFixed(2);
}

// 修改订单总价(2-1)
function updateOrderMoney() {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var orderMoney = document.getElementById("orderMoney").value;
	var re = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if (!re.test(orderMoney)) {
		alert("请输入正确数字");
	} else {
		orderController.updateOrderMoney(orderMoney, updateOrderMoneyResult);
	}
}

// 修改订单总价(2-2)
function updateOrderMoneyResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var orderMoney = data.orderMoney;
	document.getElementById("orderMoney").value = +orderMoney.toFixed(2);
}

// 手动订单提交检查
function checkCreateOrderFrom() {
	if (ifCheckedCreateOrderType()) {
		alert("请选择下单类型");
		return;
	}

	if (ifCheckedOrderType()) {
		alert("请选择订单类型");
		return;
	}

	if (ifSelectShopId()) {
		alert("请选择店铺");
		return;
	}
	var objTr = document.getElementById('skuInfoTable').rows;
	if (objTr.length < 2) {
		alert("没有选择图书");
		return;
	}
	document.getElementById('createOrderFrom').submit();
}

// 批量订单提交检查
function checkXlsCreateOrderFrom() {
	if (ifSelectShopId()) {
		alert("请选择店铺");
		return false;
	}
	return true;
}

// 地址导入提交检查
function checkXlsAddressInOrderFrom() {
	if (ifSelectShopId()) {
		alert("请选择店铺");
		return false;
	}
	return true;
}

// 选择店铺(回调使用)
function checkedShopId() {
	var shopIds = document.getElementById("shopId");
	var checkValue = document.getElementById("checkShopId").value;
	for ( var i = 0; i < shopIds.options.length; i++) {
		if (shopIds.options[i].value == checkValue) {
			shopIds.options[i].selected = true;
			break;
		}
	}
}

// 选择下单类型(回调使用)
function checkedCreateOrderType() {
	var checked = document.getElementById("manualOrderType").value;
	var createOrderTypes = document.getElementsByName("createOrderType");
	for ( var j = 0; j < createOrderTypes.length; j++) {
		var createOrderTypeValue = createOrderTypes[j].value;
		if (checked == createOrderTypeValue) {
			createOrderTypes[j].checked = true;
		}
	}
}

// 选择订单类型(回调使用)
function checkedOrderType() {
	var checked = document.getElementById("checkOrderType").value;
	var createOrderTypes = document.getElementsByName("orderType");
	for ( var j = 0; j < createOrderTypes.length; j++) {
		var createOrderTypeValue = createOrderTypes[j].value;
		if (checked == createOrderTypeValue) {
			createOrderTypes[j].checked = true;
		}
	}
}

// 判断是否选中手动下单类型
function ifCheckedCreateOrderType() {
	var createOrderType = document.getElementsByName('createOrderType');
	var checkedEmpty = true;
	for ( var i = 0; i < createOrderType.length; i++) {
		if (createOrderType[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	return checkedEmpty;
}

// 是否选中店铺
function ifSelectShopId() {
	var shopId = document.getElementById('shopId').value;
	var checkedEmpty = true;
	if (shopId != null && shopId != '') {
		checkedEmpty = false;
	}
	return checkedEmpty;
}

// 判断是否选中订单类型
function ifCheckedOrderType() {
	var orderType = document.getElementsByName('orderType');
	var checkedEmpty = true;
	for ( var i = 0; i < orderType.length; i++) {
		if (orderType[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	return checkedEmpty;
}

// ------------------------------------------出库相关------------------------------------

// 出库(2-1)
function sendOut() {
	var expressNumber = document.getElementById("expressNumber").value;
	if (expressNumber == null || expressNumber == '') {
		alert("请输入快递单号");
		return;
	}
	orderController.toSendOut(expressNumber, sendOutResult);
}

// 出库(2-2)
function sendOutResult(data) {
	if (!data.success) {
		alert(data.failReason);
		return;
	}
	var selectedContent = '';
	var result = data.orderVO.canSendOut;
	var passPlay = document.getElementById("passPlay");
	var failPlay = document.getElementById("failPlay");
	if (result) {
		selectedContent = '<h2><font color="green">检查通过</font></h2>';
		$("passPlay").play();
	} else {
		selectedContent = '<h2><font color="red">不能出库</font></h2>';
		$("failPlay").play();
	}
	var outOrderNumber = ' ';
	for ( var i = 0; i < data.orderVO.outOrderNumbers.length; i++) {
		outOrderNumber = outOrderNumber + data.orderVO.outOrderNumbers[i];
		if (!(data.orderVO.outOrderNumbers.length - 1)) {
			outOrderNumber = outOrderNumber + "<br/>";
		}
	}

	var orderDetail = '';
	for ( var i = 0; i < data.orderVO.orderDetailVOs.length; i++) {
		orderDetail = orderDetail + data.orderVO.orderDetailVOs[i].itemName
				+ ' &nbsp; * &nbsp; '
				+ data.orderVO.orderDetailVOs[i].itemCount;
		if (!(data.orderVO.orderDetailVOs.length - 1)) {
			orderDetail = orderDetail + "<br/>";
		}
	}

	selectedContent = selectedContent + '<table>';
	selectedContent = selectedContent + '<tr><td>网店订单号</td><td>'
			+ outOrderNumber + '</td></tr>';
	selectedContent = selectedContent + '<tr><td>快递单号</td><td>'
			+ data.orderVO.expressNumber + '</td></tr>';
	selectedContent = selectedContent + '<tr><td>收件人</td><td>'
			+ data.orderVO.receiverName + '</td></tr>';
	selectedContent = selectedContent + '<tr><td>商品明细</td><td>' + orderDetail
			+ '</td></tr>';
	selectedContent = selectedContent + '<tr><td>买家留言</td><td>'
			+ data.orderVO.buyerMessage + '</td></tr>';
	selectedContent = selectedContent + '<tr><td>买家备注</td><td>'
			+ data.orderVO.salerRemark + '</td></tr>';
	if (data.orderVO.canSendOut) {
		selectedContent = selectedContent
				+ '<tr><td><font color="red">拦截说明</font></td><td><font color="red">'
				+ data.orderVO.canSendOutWithMessage + '</font></td></tr>';
	}
	selectedContent = selectedContent + '</table>';
	$('sendResultDiv').innerHTML = selectedContent;
}
