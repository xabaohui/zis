/**
 * 有赞下载数据到zis
 * 
 * @param shopId
 */
function youzanDownloadItem2zis(shopId, token) {
	window.location.href = "/zis/shop/youZanDownloadItems2Mapping?shopId=" + shopId
			+ "&token=" + token;
}

function taobaoDownloadItem2zis(shopId, token) {
	window.location.href = "/zis/shop/gotoTaobaoDownLoadJsp?shopId=" + shopId
	+ "&token=" + token;
}

/**
 * 全部发布
 * @param shopId
 * @param token
 */
function addAllItems(shopId, token) {
	window.location.href = "/zis/shop/addItemAll2Shop?shopId=" + shopId
	+ "&token=" + token;
}

// 单批次处理
function addMIdToMapping(shopId, mId, token) {
	window.location.href = "/zis/shop/addOneItem2Shop?shopId=" + shopId
			+ "&mId=" + mId + "&token=" + token;
}

function checkAllmId() {
	var batchIds = document.getElementsByName('mId');
	var checkAll = document.getElementById('checkAll');
	for ( var i = 0; i < batchIds.length; i++) {
		if (checkAll.checked) {
			batchIds[i].checked = true;
		} else {
			batchIds[i].checked = false;
		}
	}
}

// 提交检查
function verifySubmit() {
	var batchIds = document.getElementsByName('mId');
	var batchForm = document.getElementById('mForm');
	var checkedEmpty = true;
	for ( var i = 0; i < batchIds.length; i++) {
		if (batchIds[i].checked) {
			checkedEmpty = false;
			break;
		}
	}
	if (checkedEmpty) {
		alert("请选择商品后再进行批量处理");
		return;
	} else {
		batchForm.submit();
	}
}

/**
 * 选择状态
 */
function checkStatus() {
	var status = document.getElementById("systemStatus");
	var checkValue = document.getElementById("mappingStatus").value;
	for(var i = 0; i < status.options.length; i++){
		if(status.options[i].value == checkValue){
			status.options[i].selected = true; 
			break;
		}
	}
}