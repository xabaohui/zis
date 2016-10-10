<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/toolkit.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/stockPosCheckBO.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript">
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;//火狐中是 window.event
		if ((e.keyCode || e.which) == 13) {
			// 获取用户输入
			var userInput = $('userInput').value;
			// (4-1)查询图书
			stockPosCheckBO.addBookRecord(userInput, showAddResult);
		}
	};
</script>

<!-- 播放器 -->
<div style="display:none">
	<embed src="/zis/resources/scanPass.wav" autoplay="false" Volume="100"
		id="passVoice">
</div>

<div align="center">
	<h2>库位校准</h2>
	<table>
		<tr>
			<td></td>
			<td><input type="button" value="清空" onclick="clearSession()" />
			</td>
		</tr>
	</table>
	<p />
	<p>
		ISBN<input type="text" name="isbn" id="userInput" />
	</p>
	<p />
	<div id="lastScanResult"
		style="font-size:35px;color:green;font-weight:bold;font-family:楷体">相关库位</div>
</div>

<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	该条码对应多种图书，请选择：
	<div id="selectArea"></div>
	<p />
	<input type="button" value="取消" onclick="cancelSelection()" />
</div>
<%@ include file="/footer.jsp"%>