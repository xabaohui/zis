function showAddResult(result) {
	if(result.lastRecordSuccess === true) {
		// 匹配库位
		var selectedContent = '匹配的库位有：<p/><ul>';
		var comparablePos = result.comparablePos;
		for(var i=0; i<comparablePos.length; i++) {
			var pos = comparablePos[i];
			selectedContent = selectedContent + '<li>' + pos + '</li>';
		}
		selectedContent = selectedContent + '</ul>';
		// 已扫描记录
		selectedContent = selectedContent + '<p/>已扫描记录:<p/><ol>';
		var scannedBookList = result.scannedBookList;
		for(var i=0; i<scannedBookList.length; i++) {
			var book = scannedBookList[i];
			var bookIntro = book.isbn + ' / ' + book.bookName;
			selectedContent =  selectedContent + '<li>'+ bookIntro +'</li>'
		}
		selectedContent = selectedContent + '</ol>';
		
		$('lastScanResult').innerHTML = selectedContent;
		$('userInput').value='';
	} else {
		//按照乾坤要求，不提示出错
		//alert('操作失败，' + result.failReason);
		$('lastScanResult').innerHTML = '操作失败，请继续扫描其他记录，原因：' + result.failReason;
		$('userInput').value='';
	}
}

function clearSession() {
	
	stockPosCheckBO.clearSession();
	$('lastScanResult').innerHTML = '';
}