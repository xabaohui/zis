<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/storage.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/storageInwarehouseBO.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript">
	document.onkeydown = function(e) {
		if (!e) {
			e = window.event;
		}//火狐中是 window.event
		if ((e.keyCode || e.which) == 13) {
			// 获取用户输入
			var userInput = $('userInput').value;
			var ioBatchId = $('ioBatchId').value;
			// (4-1)查询图书
				bookService.findBookByISBN(userInput, findBookByIsbn);
		}
		$('userInput').value = '';
	};
</script>

<!-- 播放器 -->
<div style="display:none">
	<embed src="/zis/resources/scanPass.wav" autoplay="false" Volume="100"
		id="passVoice">
</div>

<div align="center">
	<h1>入库扫描</h1>
	<p />
	<p />
	<table>
		<tr>
			<td>操作员</td>
			<td>${inwarehouseOperator}</td>
		</tr>
		<tr>
			<td>备注</td>
			<td>${memo}</td>
		</tr>
		<tr>
			<td colspan="2">
					<input type="hidden" name="curPosition" id="curPosition" value="${curPosition}">
					<input type="hidden"name="bookinfoStr" id="bookinfoStr" value="${bookinfoStr}">
				<form action="storage/confirmInStorage" id = "storageForm">
					<input type="hidden" name="ioBatchId" id="ioBatchId" value="${ioBatchId}">
					<input type="submit" value="完成">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消"onclick="cancelInStorage()">
				</form>
			</td>
		</tr>
	</table>
	<p />
	<div id="currentPosDisplay" style="font-size:50px;color:green;font-weight:bold;font-family:楷体">
		当前库位 ${stockPosLabel[0]}  已入库 ${inwarehouse.amount} 个
	</div>
	<table>
		<tr>
			<td>数量</td>
			<td><input type="text" name="amount" id="userInputAmount" value="1" size="1"></td>
		</tr>
		<tr>
			<td>ISBN</td>
			<td><input type="text" name="isbn" id="userInput" value="${userInput}" ></td>
		</tr>
	</table>
	<p />
	<div id="lastScanResult" style="font-size:20px;font-weight:bold"></div>
</div>

<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	该条码对应多种图书，请选择：
	<div id="selectArea"></div>
	<p />
	<input type="button" value="取消" onclick="cancelSelection()" />
</div>
<%@ include file="/footer.jsp"%>