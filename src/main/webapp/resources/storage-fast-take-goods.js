// (3-1)使用ISBN查询图书
function findIsbn2FastTakeGoods(list) {
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
		findAmountGoods(book.id, bookIntro);
	}
	// 多条记录，浮出选择层，待用户选择后，执行下一步逻辑(3-3)入库
	else {
		alert('该条码对应了多种图书，请进行选择');
		showBooks(list);
	}
}

// (3-3)执行出库库操作
function fastTakeGoodsSubmit(bookId, bookIntro, posLabel, amount) {
	// 查询结果临时放置到隐藏域中，供扫描结果调用
	$('bookinfoStr').value = bookIntro;
	$('posLabel').value = posLabel;
	var hasAmount = prompt("您已取的数量:", "");
	var re = /^[0-9]*[1-9][0-9]*$/;
	if (hasAmount) {
		if (!re.test(hasAmount)) {
			alert("请输入正确的数量");
		} else {
			$('amount').value = hasAmount;
			$('skuId').value = bookId;
			// 关闭浮出层
			hideShadow();
			// 调用入库逻辑
			$('takeGoodsForm').submit();
		}
	} else {
		
	}
}

// 展示一码多书，供用户选择
function showBooks(bookList) {
	var selectedContent = '<ol>';
	for ( var i = 0; i < bookList.length; i++) {
		var book = bookList[i];
		var bookIntro = book.isbn + ' / ' + book.bookName + '( '
				+ book.bookEdition + ' ) / ' + book.bookAuthor + ' / '
				+ book.bookPublisher;
		selectedContent = selectedContent + '<li>' + bookIntro
				+ '<input type="button" value="选择" onclick="findAmountGoods('
				+ book.id + ',\'' + bookIntro + '\')"' + '</li><p/>';
	}
	selectedContent = selectedContent + '</ol>';
	$('selectArea').innerHTML = selectedContent;
	showShadow();
}

// 展示多库位，供用户选择

function showPosLabelBooks(data) {
	// 关闭浮出层
	hideShadow();
	if (data.success === true) {
		var selectedContent = '该图书对应多个库位，请选择:<div id="selectArea"><ol>';
		for ( var i = 0; i < data.goods.length; i++) {
			var good = data.goods[i];
			var posBookInfo = "库位:" + good.posLabel + "&nbsp;&nbsp;图书:" + data.bookInfoStr
					+ "&nbsp;&nbsp;可用：" + good.amount + "本";
			selectedContent = selectedContent
					+ '<li>'
					+ posBookInfo
					+ '&nbsp;&nbsp;<input type="button" value="选择" onclick="fastTakeGoodsSubmit('
					+ data.skuId + ',\'' + data.bookInfoStr + '\',\''
					+ good.posLabel + '\',\'' + good.amount + '\')"'
					+ '</li><p/>';
		}
		selectedContent = selectedContent
				+ '</ol></div><p /><input type="button" value="取消" onclick="cancelSelection()" />';
		$('float-to-be-show').innerHTML = selectedContent;
		showShadow();
	} else {
		alert(data.failReason);
	}
}

function fastFind() {
	// 获取用户输入
	var userInput = $('userInput').value;
	if (userInput == '') {
		alert("请输入isbn");
		return;
	}
	// (4-1)查询图书
	bookService.findBookByISBN(userInput, findIsbn2FastTakeGoods);
}

// 查询库位进行选择
function findAmountGoods(bookId, bookIntro) {
	var amount = $('inputAmount').value;
	fastTakeGoods.gotoHaveAmountGoods(bookId, bookIntro, amount,
			showPosLabelBooks);
}

//用户取消选择一码多书
function cancelSelection() {
	// 关闭浮出层
	hideShadow();
}
