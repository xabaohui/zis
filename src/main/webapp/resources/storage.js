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

// (3-1)使用ISBN查询图书
/**
 * 
 * 快速入库使用
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
		var bookIntro = book.bookName + '( ' + book.bookEdition + ' ) / ' + book.bookAuthor + ' / ' + book.bookPublisher;
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
	for(var i=0; i<bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( ' + book.bookEdition + ' ) / ' + book.bookAuthor + ' / ' + book.bookPublisher;
		selectedContent =  selectedContent + '<li>'+ bookIntro + '<input type=button value="选择" onclick="inwarehouse('+book.id + ',\'' + bookIntro +'\')"' +'</li><p/>';
	}
	selectedContent = selectedContent + '</ol>';
	$('selectArea').innerHTML = selectedContent;
	showShadow();
}

// 展示一码多书，供用户选择
/**
 * 快速入库使用
 * @param bookList
 */
function fastShowMultipleBooks(bookList) {
	var selectedContent = '<ol>';
	for(var i=0; i<bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( ' + book.bookEdition + ' ) / ' + book.bookAuthor + ' / ' + book.bookPublisher;
		selectedContent =  selectedContent + '<li>'+ bookIntro + '<input type=button value="选择" onclick="fastInwarehouse('+book.id + ',\'' + bookIntro +'\')"' +'</li><p/>';
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
	storageInwarehouseBO.doStorageInwarehouse(ioBatchId, curPosition, bookId, amount,
			showInwarehouseResult);
}

// (3-3)执行入库操作
/**
 * 快速入库使用
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
	storageInwarehouseBO.fastStorageInwarehouse(oldAmount, curPosition, bookId, amount,
			showInwarehouseResult);
	var nextValue = +oldAmount + +amount;
	document.getElementById('oldAmount').value = nextValue;
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
		var inuser = $('userInput').value;
		inuser = "";
		document.getElementById('userInput').value = "";
		alert(inuser);
		$('userInputAmount').value = 1;
	} else {
		alert('操作失败，' + result.failReason);			
	}
}

//用户取消入库
function cancelInStorage() {
	var i = document.getElementById("storageForm").action = "storage/cancelInStorage";
	alert(i);
	document.getElementById("storageForm").submit();
}

function checkAllOId() {
	var orderIds = document.getElementsByName('oId');
	var checkAll = document.getElementById('checkAll');
	for ( var i = 0; i < batchIds.length; i++) {
		if (checkAll.checked) {
			batchIds[i].checked = true;
		} else {
			batchIds[i].checked = false;
		}
	}
}

/**
 * 选择状态
 */
function checkStatus() {
	var status = document.getElementById("systemStatus");
	var checkValue = document.getElementById("sendStatus").value;
	for(var i = 0; i < status.options.length; i++){
		if(status.options[i].value == checkValue){
			status.options[i].selected = true; 
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
		if(list[i].getAttribute("type") == "text"){
			list[i].value = "";
		}
	}
}