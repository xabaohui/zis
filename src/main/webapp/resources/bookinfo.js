// 检查系统中是否存在相同记录
function checkExistBook() {
	var isbn = document.getElementById('isbn').value;
	isbn = trim(isbn);
	if(isbn == '') {
		return;
	}
	bookService.findBookByISBN(isbn, showCheckExistBookResult);
}

function showCheckExistBookResult(data) {
	if (data != null && data.length > 0) {
		var json = {
			"options" : data
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
}

// 迁移图书使用量
function immigrate(oldBookId) {
	var newBookId = prompt('要迁移到哪本图书，请输入bookId');
	if(newBookId == null) {
		return false;
	}
	addAmountBiz.updateForImmigrateBookRequirement(oldBookId, newBookId, showImmigrateResult);
	return false;
}

function showImmigrateResult(result) {
	if(result == null) {
		alert('操作成功');
	} else {
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
		document.getElementById("operateType").value = operateType;
		document.getElementById("form_checkBox").submit();
	}
}