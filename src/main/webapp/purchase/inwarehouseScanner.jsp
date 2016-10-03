<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/purchase.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/inwarehouseBO.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript">
	document.onkeydown = function(e) {
		if (!e)
			e = window.event;//火狐中是 window.event
		if ((e.keyCode || e.which) == 13) {
			// 获取用户输入
			var userInput = $('userInput').value;
			var inwarehouseId = $('inwarehouseId').value;
			// (4-1)查询图书
			bookService.findBookByISBN(userInput, findBookByIsbn);
		}
	};
</script>

<!-- 播放器 -->
<div style="display:none">
	<embed src="/zis/resources/scanPass.wav" autoplay="false" Volume="100" id="passVoice">
</div>

<div align="center">
<h2>
	入库扫描 - <s:property value="bizTypeDisplay" />
</h2>
<table>
	<tr>
		<td>操作员</td>
		<td><s:property value="inwarehouseOperator"/></td>
	</tr>
	<tr>
		<td>采购员</td>
		<td><s:property value="purchaseOperator"/></td>
	</tr>
	<tr>
		<td>备注</td>
		<td><s:property value="memo"/></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<s:form action="purchase/terminateInwarehouse">
				<s:hidden name="curPosition" id="curPosition"/>
				<s:hidden name="bookinfoStr" id="bookinfoStr"/>
				<s:hidden name="inwarehouseId" id="inwarehouseId"/>
				<s:submit value="完成"/>
			</s:form>
		</td>
	</tr>
</table>
<p/>
<div id="currentPosDisplay" style="font-size:50px;color:green;font-weight:bold;font-family:楷体">当前库位 <s:property value="#stockPosLabel[0]"/> | 已入库 <s:property value="#inwarehouse.amount"/> 个</div>
<p>
<s:textfield name="amount" label="数量" value="1" id="userInputAmount" size="1"/>
<s:textfield name="isbn" label="ISBN" id="userInput" />
</p>
<p />
<div id="lastScanResult" style="font-size:20px;font-weight:bold"></div>
</div>

<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	该条码对应多种图书，请选择：
	<div id="selectArea"></div>
	<p/>
	<input type="button" value="取消" onclick="cancelSelection()"/>
</div>
<%@ include file="/footer.jsp"%>