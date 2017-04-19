<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/storage-fast-take-goods.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript">
	function fastFind() {
	// 获取用户输入
	var userInput = $('userInput').value;
	if(userInput == ''){
		alert("请输入isbn");
		return;
	}
	// (4-1)查询图书
	bookService.findBookByISBN(userInput, findIsbn2FastTakeGoods);
}
</script>

<!-- 播放器 -->
<div style="display:none">
	<embed src="/zis/resources/scanPass.wav" autoplay="false" Volume="100" id="passVoice">
</div>
<div align="center">
	<h1>快速出库</h1>
	<br />
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
</div>
<br />
<div align="center">
	<spring:form action="storage/fastTakeGoods" id="takeGoodsForm" method="post" modelAttribute="dto">
		<table>
			<tr>
				<td>库位：</td>
				<td><input type="text" name="posLabel" value = "${param.posLabel}" /></td>
				<td><spring:errors delimiter="," path="posLabel" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>ISBN:</td>
				<td><input type="text" name="userInput" id="userInput" value="${param.userInput}" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/></td>
				<td><spring:errors delimiter="," path="skuId" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>数量：</td>
				<td><input type="text" name="amount" value="${param.amount}" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/></td>
				<td><spring:errors delimiter="," path="amount" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td colspan="3"><input type="button" value="直接出库" onclick="fastFind()" /></td>
			</tr>
		</table>
		<input type="hidden" name="skuId" id="skuId" value="">
		<input type="hidden" name="token"  value="${token}">
		<input type="hidden" name="bookinfoStr" id="bookinfoStr" value="${bookinfoStr}">
	</spring:form>
</div>

<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	该条码对应多种图书，请选择：
	<div id="selectArea"></div>
	<p />
	<input type="button" value="取消" onclick="cancelSelection()" />
</div>
<%@ include file="/footer.jsp"%>