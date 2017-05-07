<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/trade.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/orderController.js'></script>
<script type="text/javascript">
	document.onkeydown = function(e) {
		if (!e) {
			e = window.event;
		}//火狐中是 window.event
		if ((e.keyCode || e.which) == 13) {
			sendOut();
		}
		$('expressNumber').value = "";
	};
</script>
<div align="center">
	<h1>出库扫描</h1>
	<br />
</div>

<!-- 播放器 -->
<audio src="/zis/resources/failPaly.wav" id="failPlay"></audio>
<audio src="/zis/resources/scanPass.wav" id="passPlay"></audio>
<div align="center" id = "sendResultDiv">
</div>
<p />
<p />
<div align="center">
	<table>
		<tr>
			<td>快递单号</td>
			<td>
			<div id = "userInputDiv">
				<input type="text" name="expressNumber" id="expressNumber"/>
			</div>
			</td>
		</tr>
	</table>
	<p />
</div>
<%@ include file="/footer.jsp"%>