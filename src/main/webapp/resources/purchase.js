
// 手动设置采购量
function editPurchasePlanManualDecision(bookId) {
	var plan = prompt('请输入计划采购量');
	if (plan === false || plan == null) {
		alert('未输入');
		return false;
	}
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.addManualDecision(bookId, plan, showPurchasePlanOperateResult);
	return false;
}

//去除人工定量
function removeManualDecision(bookId) {
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.removeManualDecision(bookId, showPurchasePlanOperateResult);
	return false;
}

// 重新计算计划采购量
function recalculatePurchasePlan(bookId) {
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.recalculateRequireAmount(bookId, showPurchasePlanOperateResult);
	return false;
}

// 手动设置库存量
function editPurchasePlanStock(bookId) {
	var stock = prompt('请输入库存量');
	if (stock === false || stock == null) {
		alert('未输入');
		return false;
	}
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.updateBookStock(bookId, stock, showPurchasePlanOperateResult);
	return false;
}

// 加入黑名单
function addToBlackList(bookId) {
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.addBlackList(bookId, showPurchasePlanOperateResult);
	return false;
}

// 加入白名单
function addToWhiteList(bookId) {
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.addWhiteList(bookId, showPurchasePlanOperateResult);
	return false;
}

// 去除黑名单和白名单标记
function removePurchasePlanFlag(bookId) {
	document.getElementById("float-to-be-show").style.display = "block";
	purchaseService.deleteBlackOrWhiteList(bookId, showPurchasePlanOperateResult);
	return false;
}

// 展示采购计划相关操作的处理结果，成功则刷新页面，失败则提示原因
function showPurchasePlanOperateResult(result) {
	if (result == '') {
		location.reload();
	} else {
		document.getElementById("float-to-be-show").style.display = "none";
		alert('操作失败，请联系管理员:' + result);
	}
}

// 从一码多书的结果中选出正确的
var selectedBookId = null;
function selectBookForMultipleIsbn(tempImportDetailId, bookId) {
	selectedBookId = 'book_' + tempImportDetailId + '_' + bookId;
	purchaseService.updateAssociateTempImportDetailWithBookInfo(tempImportDetailId, bookId, viewAfterSelected);
}

function viewAfterSelected(result) {
	if(result == null) {
		document.getElementById(selectedBookId).style.color="green";
	} else {
		alert('操作失败，' + result);
	}
}

/** 
 * 采购入库 --START------
 */
// 动态设置库位信息
function addStockPosition() {
	var origPos = document.getElementById('purchaseInStockPos').innerHTML;
	document.getElementById('purchaseInStockPos').innerHTML = origPos + '<input type="text" name="stockPosLabel" size="15" />&nbsp;<input type="text" name="stockPosCapacity" size="3" /><p/>';
}

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
		var bookIntro = book.bookName + '( ' + book.bookEdition + ' ) / ' + book.bookAuthor + ' / ' + book.bookPublisher;
		inwarehouse(book.id, bookIntro);
	}
	// 多条记录，浮出选择层，待用户选择后，执行下一步逻辑(3-3)入库
	else {
		alert('该条码对应了多种图书，请进行选择');
		showMultipleBooks(list);
	}
}

// 展示一码多书，供用户选择
function showMultipleBooks(bookList) {
	var selectedContent = '<ol>';
	for(var i=0; i<bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( ' + book.bookEdition + ' ) / ' + book.bookAuthor + ' / ' + book.bookPublisher;
		selectedContent =  selectedContent + '<li>'+ bookIntro + '<input type=button value="选择" onclick="inwarehouse('+book.id + ',\'' + bookIntro +'\')"' +'</li><p/>';
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
	var inwarehouseId = $('inwarehouseId').value;
	var curPosition = $('curPosition').value;
	var amount = $('userInputAmount').value;
	inwarehouseBO.doInwarehouse(inwarehouseId, curPosition, bookId, amount,
			showInwarehouseResult);
}

// 展示入库结果
function showInwarehouseResult(result) {
	var currentPosLabel;
	if(result.success === true) {
		if(result.positionChange === true) {
			alert('当前库位已满，自动切换到' + result.curPosLabel);
			currentPosLabel = result.curPosLabel;
			$('curPosition').value = result.curPosLabel;
		} else {
			currentPosLabel = result.prePosLabel;
		}
		// 修改库位和扫描总量
		$('currentPosDisplay').innerHTML = '当前库位 '+currentPosLabel+' | 已入库 '+result.totalAmount+' 本';
		// 展示本次扫描结果
		$('lastScanResult').innerHTML = '最后一次扫描：' + $('bookinfoStr').value;
		// 播放声音提示
		$('passVoice').play();
		// 清空输入
		$('userInput').value = '';
		$('userInputAmount').value = 1;
	} else {
		alert('操作失败，' + result.failReason);			
	}
}

// 导出网渠宝入库单
function exportInwarehouseData(operateType) {
	var batchSelectedItem = document.getElementsByName("batchSelectedItem");
	for(var i = 0;i < batchSelectedItem.length ;i++){
		if(batchSelectedItem[i].checked){
			document.getElementById('operateType').value = operateType;
			document.getElementById('form_checkBox').submit();
			return;
		}
	}
	alert("您没有选择导出的数据");
}

// 删除入库明细记录
function deleteInwarehouseDetail(detailId) {
	var bookinfo = document.getElementById('inwarehouseDetail_' + detailId).innerText;
	var userInput = confirm('确定删除【'+bookinfo+'】吗');
	if(userInput == true) {
		inwarehouseBO.deleteInwarehouseDetail(detailId, showDeleteInwarehouseDetailResult);
	}
}

function showDeleteInwarehouseDetailResult(result) {
	if(result == "") {
		alert('操作成功');
		location.reload();
	} else {
		alert(result);
	}
}

// 删除入库单
function deleteInwarehouse(inwarehouseId) {
	var userInput = confirm('确定删除吗');
	if(userInput == true) {
		inwarehouseBO.deleteInwarehouse(inwarehouseId, showDeleteInwarehouseResult);
	}
	return false;
}

function showDeleteInwarehouseResult(result) {
	if(result == "") {
		alert('操作成功');
		location.reload();
	} else {
		alert(result);
	}
}
/** 
 * 采购入库 --END------
 */