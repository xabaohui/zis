// (3-1)使用ISBN查询图书
function findBookByIsbn(list) {
	// (3-2)根据查询结果，执行不同的逻辑
	// 无记录，提示用户
	if (list.length == 0) {
		alert('无此图书');
	}
	// 只有一条记录，选择该记录，执行下一步逻辑(3-3)入库
	else if (list.length == 1) {
		var book = list[0];
		// 执行入库操作
		var bookIntro = book.bookName + '( ' + book.bookEdition + ' ) / '
				+ book.bookAuthor + ' / ' + book.bookPublisher;
		inwarehouse(book.id, bookIntro);
	}
	// 多条记录，浮出选择层，待用户选择后，执行下一步逻辑(3-3)入库
	else {
		alert('该条码对应了多种图书，请进行选择');
		showMultipleBooks(list);
	}
}

// (3-1)使用ISBN查询图书
/**
 * 
 * 快速入库使用
 * 
 * @param list
 */
function fastFindBookByIsbn(list) {
	// (3-2)根据查询结果，执行不同的逻辑
	// 无记录，提示用户
	if (list.length == 0) {
		alert('无此图书');
	}
	// 只有一条记录，选择该记录，执行下一步逻辑(3-3)入库
	else if (list.length == 1) {
		var book = list[0];
		// 执行入库操作
		var bookIntro = book.bookName + '( ' + book.bookEdition + ' ) / '
				+ book.bookAuthor + ' / ' + book.bookPublisher;
		fastInwarehouse(book.id, bookIntro);
	}
	// 多条记录，浮出选择层，待用户选择后，执行下一步逻辑(3-3)入库
	else {
		alert('该条码对应了多种图书，请进行选择');
		fastShowMultipleBooks(list);
	}
}

// 展示一码多书，供用户选择
function showMultipleBooks(bookList) {
	var selectedContent = '<ol>';
	for ( var i = 0; i < bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( '
				+ book.bookEdition + ' ) / ' + book.bookAuthor + ' / '
				+ book.bookPublisher;
		selectedContent = selectedContent + '<li>' + bookIntro
				+ '<input type=button value="选择" onclick="inwarehouse('
				+ book.id + ',\'' + bookIntro + '\')"' + '</li><p/>';
	}
	selectedContent = selectedContent + '</ol>';
	$('selectArea').innerHTML = selectedContent;
	showShadow();
}

// 展示一码多书，供用户选择
/**
 * 快速入库使用
 * 
 * @param bookList
 */
function fastShowMultipleBooks(bookList) {
	var selectedContent = '<ol>';
	for ( var i = 0; i < bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( '
				+ book.bookEdition + ' ) / ' + book.bookAuthor + ' / '
				+ book.bookPublisher;
		selectedContent = selectedContent + '<li>' + bookIntro
				+ '<input type=button value="选择" onclick="fastInwarehouse('
				+ book.id + ',\'' + bookIntro + '\')"' + '</li><p/>';
	}
	selectedContent = selectedContent + '</ol>';
	$('selectArea').innerHTML = selectedContent;
	showShadow();
}

// 用户取消选择一码多书
function cancelSelection() {
	// 关闭浮出层
	hideShadow();
}

// (3-3)执行入库操作
function inwarehouse(bookId, bookIntro) {
	// 查询结果临时放置到隐藏域中，供扫描结果调用
	$('bookinfoStr').value = bookIntro;
	// 关闭浮出层
	hideShadow();
	// 调用入库逻辑
	var ioBatchId = $('ioBatchId').value;
	var curPosition = $('curPosition').value;
	var amount = $('userInputAmount').value;
	var purchaseOperator = document.getElementById("purchaseOperator").value;
	var bizType = document.getElementById("bizType").value;
	storageInwarehouseBO.doStorageInwarehouse(ioBatchId, curPosition, bookId,
			amount, purchaseOperator, bizType, showInwarehouseResult);
}

// (3-3)执行入库操作
/**
 * 快速入库使用
 * 
 * @param bookId
 * @param bookIntro
 */
function fastInwarehouse(bookId, bookIntro) {
	// 查询结果临时放置到隐藏域中，供扫描结果调用
	$('bookinfoStr').value = bookIntro;
	// 关闭浮出层
	hideShadow();
	// 调用入库逻辑
	var oldAmount = $('oldAmount').value;
	var curPosition = $('curPosition').value;
	var amount = $('userInputAmount').value;
	storageInwarehouseBO.fastStorageInwarehouse(oldAmount, curPosition, bookId,
			amount, showInwarehouseResult);
	var nextValue = +oldAmount + +amount;
	document.getElementById('oldAmount').value = nextValue;
}

// 展示入库结果
function showInwarehouseResult(result) {
	var currentPosLabel;
	if (result.success === true) {
		if (result.positionChange === true) {
			alert('当前库位已满，自动切换到' + result.curPosLabel);
			currentPosLabel = result.curPosLabel;
			$('curPosition').value = result.curPosLabel;
		} else {
			currentPosLabel = result.prePosLabel;
		}
		// 修改库位和扫描总量
		$('currentPosDisplay').innerHTML = '当前库位 ' + currentPosLabel
				+ ' | 已入库 ' + result.totalAmount + ' 本';
		// 展示本次扫描结果
		$('lastScanResult').innerHTML = '最后一次扫描：' + $('bookinfoStr').value;
		// 播放声音提示
		$('passVoice').play();
		// 清空输入
		var inuser = $('userInput').value;
//		inuser = "";
//		document.getElementById('userInput').value = "";
//		alert(inuser);
		$('userInputAmount').value = 1;
	} else {
		alert('操作失败，' + result.failReason);
	}
}

// 用户取消入库
function cancelInStorage() {
	var i = document.getElementById("storageForm").action = "storage/cancelInStorage";
	document.getElementById("storageForm").submit();
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

// 动态设置sku
function addSku() {
	var i = document.getElementById('skuNo').value;
	var origPos = document.getElementById('createSkuDiv').innerHTML;
	document.getElementById('createSkuDiv').innerHTML = origPos
			+ '图书Id:<input type="text" name="dList[' + i
			+ '].skuId" size="15" />&nbsp;图书数量：<input type="text" name="dList['
			+ i + '].amount" size="3" /><p/>';
	i++;
	document.getElementById('skuNo').value = i;
}
// 动态设置sku清空使用
function clearSku() {
	var i = document.getElementById('skuNo').value = 1;
	var origPos = document.getElementById('createSkuDiv').innerHTML = "";
}

/**
 * 选择状态
 */
function checkStatus() {
	var status = document.getElementById("systemStatus");
	var checkValue = document.getElementById("sendStatus").value;
	for ( var i = 0; i < status.options.length; i++) {
		if (status.options[i].value == checkValue) {
			status.options[i].selected = true;
			break;
		}
	}
}

/**
 * 选择状态
 */
function checkStatusAndType() {
	var status = document.getElementById("selectStatus");
	var checkValue = document.getElementById("checkStatus").value;
	for ( var i = 0; i < status.options.length; i++) {
		if (status.options[i].value == checkValue) {
			status.options[i].selected = true;
			break;
		}
	}

	var type = document.getElementById("selectType");
	var checkTypeValue = document.getElementById("checkType").value;
	for ( var i = 0; i < type.options.length; i++) {
		if (type.options[i].value == checkTypeValue) {
			type.options[i].selected = true;
			break;
		}
	}
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

// 单个配货
function pickingUpOrder(orderId) {
	window.location.href = "storage/pickingUpOrder?orderId=" + orderId;

}

// 单个取消
function cancelOrder(orderId) {
	if (confirm("你确定取消吗？")) {
		window.location.href = "storage/cancelOrder?orderId=" + orderId;
	} else {
	}
}

// 批量配货
function pickingUpOrders() {
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
function cancelOrders() {
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
 * 入库单导出
 */
function exportWangqubaoInwarehouse(){
	document.getElementById("batchForm").action = "storage/exportWangqubaoInwarehouse";
	document.getElementById("batchForm").submit();
}

/**
 * 商品明细导出
 */
function exportWangqubaoItemByInwarehouse(){
	document.getElementById("batchForm").action = "storage/exportWangqubaoItemByInwarehouse";
	document.getElementById("batchForm").submit();
}

/**
 * 出库单导出
 */
function exportWangqubaoSendOut(){
	document.getElementById("batchForm").action = "storage/exportWangqubaoSendOut";
	document.getElementById("batchForm").submit();
}

/**
 * 批次全选
 */
function checkAllBatchId() {
	var orderIds = document.getElementsByName('batchId');
	var checkAll = document.getElementById('checkAll');
	for ( var i = 0; i < orderIds.length; i++) {
		if (checkAll.checked) {
			orderIds[i].checked = true;
		} else {
			orderIds[i].checked = false;
		}
	}
}

/**
 * 部分缺货
 */
function lackPart() {
	var amount = prompt("已取图书数量:", "");
	var re = /^[0-9]*[1-9][0-9]*$/;
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

/**
 * 全部缺货
 */
function lackAll() {
	var result = confirm('此库位是否全部缺货');
	if (result) {
		document.getElementById('takeGoodsForm').action = "storage/lackAll";
		document.getElementById('takeGoodsForm').submit();
	} else {
	}
}

/**
 * 下一步取件
 */
function nextTakeGoods() {
	document.getElementById('takeGoodsForm').action = "storage/nextTakeGoods";
	document.getElementById('takeGoodsForm').submit();
}

/**
 * 取件
 * 
 * @param batchId
 */
function takeGoods(batchId) {
	window.location.href = "storage/takeGoods?batchId=" + batchId;
}

/**
 * 完成取件
 */
function finishTakeGoods(batchId) {
	window.location.href = "storage/finishTakeGoods?batchId=" + batchId;
}
