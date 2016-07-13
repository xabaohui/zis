// 检查系统中是否存在相同记录，如果没有，则从网络采集数据，辅助录入信息
function checkExistBook() {
	var isbn = document.getElementById('isbn').value;
	isbn = trim(isbn);
	if(isbn == '') {
		return;
	}
	$("showExistBook").style="display:none";
	// 增加遮罩，提示用户系统处理中
	document.getElementById("float-to-be-show").style.display = "block";
	bookService.findAndCaptureBookByISBN(isbn, showCheckExistBookResult);
}

function showCheckExistBookResult(data) {
	// 去除遮罩
	document.getElementById("float-to-be-show").style.display = "none";
	// 处理结果
	if(data != null) {
		if(data.sysData) {
			showExistSysData(data);
		} else {
			showCapturedData(data.bookCaptured);
		}
	}
}

// 展示系统中已存在的记录
function showExistSysData(data) {
	var json = {
		"options" : data.booksExist
	};
	json = eval(json.options);
	var str = "系统中的记录有：<p/><ul>";
	for ( var j = 0; j < json.length; j++) {
		var isbn = json[j].isbn;
		var bookName = json[j].bookName;
		var bookId = json[j].id;
		var bookPublisher = json[j].bookPublisher;
		var bookAuthor = json[j].bookAuthor;
		var bookEdition = json[j].bookEdition;
		var line = '<li>' + isbn + " / " + bookName + " (" + bookEdition + ") / "
				+ bookAuthor + " / " + bookPublisher
				+ "</li>";
		str += line;
	}
	alert('系统里已存在相同条码的记录');
	$("showExistBook").innerHTML = str + "</ul>";
	$("showExistBook").style="color:red";
}

// 展示网络采集到的数据
function showCapturedData(bookCaptured) {
	if(bookCaptured == null) {
		// 未采集到任何记录，不做任何操作
		return;
	}
	$("bookName").value = bookCaptured.name;
	$("bookAuthor").value = bookCaptured.author;
	$("bookPublisher").value = bookCaptured.publisher;
	$("publishDate").value = bookCaptured.publishDateStr;
	$("bookPrice").value = bookCaptured.price;
	$("bookEdition").value = bookCaptured.edition;
}

// 迁移图书使用量
function immigrate(oldBookId) {
	var newBookId = prompt('要迁移到哪本图书，请输入bookId');
	if(newBookId == null) {
		return false;
	}
	document.getElementById("float-to-be-show").style.display = "block";
	addAmountBiz.updateForImmigrateBookRequirement(oldBookId, newBookId, showImmigrateResult);
	return false;
}

function showImmigrateResult(result) {
	if(result == null) {
		alert('操作成功');
	} else {
		document.getElementById("float-to-be-show").style.display = "none";
		alert(result);
	}
}

// 批量操作
function batchOperate(operateType) {
	var tips = '';
	if(operateType == 'setToRelated') {
		tips = '设置成相关图书';
	}
	if(operateType == 'setToGroup') {
		tips = '设置成不同版本图书';
	}
	if(operateType == 'batchDelete') {
		tips = '批量删除（从网站彻底删除）';
	}
	if(operateType == 'batchAddToBlackList') {
		tips = '批量加入黑名单（仅影响采购）';
	}
	var userInput = confirm('确定执行操作【' + tips + '】吗？');
	if(userInput == true) {
		document.getElementById("float-to-be-show").style.display = "block";
		document.getElementById("operateType").value = operateType;
		document.getElementById("form_checkBox").submit();
	}
}