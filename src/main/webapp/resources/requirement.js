// 添加新记录
function addNewBook() {
	// 检查年级
	var gradeChecked = isRadioChecked('grade');
	if(gradeChecked == false) {
		alert('请选择年级');
		return;
	}
	// 检查学期
	var termChecked = isRadioChecked('term');
	if(termChecked == false) {
		alert('请选择学期');
		return;
	}
	// 展示搜索框
	$("searchResult").innerHTML = "书名："
			+ "<input type='text' name='bookName' id='bookName'/>" + "<br>"
			+ "作者：" + "<input type='text' name='bookAuthor' id='bookAuthor'/>"
			+ "<br>" + "ISBN：" + "<input type='text' name='ISBN' id='ISBN'/>"
			+ "  " + "<input type='button' value='查询' onclick='searchBook()'>";
}

// 检查单选框是否被选择
function isRadioChecked(elementName) {
	var arr = document.getElementsByName(elementName);
	for(var i=0; i<arr.length; i++) {
		if(arr[i].checked == true) {
			return true;
		}
	}
	return false;
}

// 获取单选框的值
function getRadioValue(elementName) {
	var arr = document.getElementsByName(elementName);
	for(var i=0; i<arr.length; i++) {
		if(arr[i].checked == true) {
			return arr[i].value;
		}
	}
	return -1;
}

// 查找图书
function searchBook() {
	showShadow();
	var bookName = $("bookName").value;
	var bookAuthor = $("bookAuthor").value;
	var ISBN = $("ISBN").value;
	bookService.getBookInfo(bookName, bookAuthor, ISBN, showSearchResult);
}

// 展示查找结果
function showSearchResult(data) {
	hideShadow();
	if (data != null && data.length > 0) {
		var json = {
			"options" : data
		};
		json = eval(json.options);
		var str = "<ul>";
		for ( var j = 0; j < json.length; j++) {
			var isbn = json[j].isbn;
			var bookName = json[j].bookName;
			var bookId = json[j].id;
			var bookPublisher = json[j].bookPublisher;
			var bookAuthor = json[j].bookAuthor;
			var bookEdition = json[j].bookEdition;
			var line = '<li>' + isbn + " / " + bookName + " (" + bookEdition + ") / "
					+ bookAuthor + " / " + bookPublisher
					+ "<input type='button' value='添加' onclick=\"addBookToSession(" + bookId + ")\"/></li>";
			str += line;
		}
		$("searchResult").innerHTML = str + "</ul>";
	} else {
		$("searchResult").innerHTML = "<span>无此图书</span>";
	}
}

// 添加图书记录到Session中
function addBookToSession(bookId) {
	var departId = document.getElementById('departId').value;
	var grade = getRadioValue('grade');
	var term = getRadioValue('term');
	$("searchResult").innerHTML = '';
	addAmountBiz.addSelectedBookToDwrSession(bookId, departId, grade, term, showAddResult);
}

// 展示添加结果
function showAddResult(data) {
	if(data.success === true) {
		var displayContent = '<ol>';
		var bookList = data.bookList;
		var json = {
				"options" : bookList
			};
		json = eval(json.options);
		for(var i=0; i<json.length; i++) {
			var bookId = json[i].id;
			var isbn = json[i].isbn;
			var bookName = json[i].bookName;
			var bookEdition = json[i].bookEdition;
			var bookAuthor = json[i].bookAuthor;
			var bookPublisher = json[i].bookPublisher;
			var line = isbn + ' / ' + bookName + ' ('+ bookEdition +') / ' + bookAuthor + ' / ' + bookPublisher
				+ "<input type=\"button\" value=\"删除\" onclick=\"removeBook("+ bookId +")\"/>";
			displayContent += ('<li>' + line + '</li>');
		}
		displayContent += '</ol>'
		$("bookListAdded").innerHTML = displayContent;
	} else {
		alert('操作失败，' + data.failReason);
	}
}

// 删除已添加记录
function removeBook(bookId) {
	addAmountBiz.removeSelectedBookToDwrSession(bookId, showAddResult);
}