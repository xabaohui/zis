<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/storage.js'></script>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/storageInwarehouseBO.js'></script>
<script type="text/javascript">

</script>
<style type="text/css">
#takeButton{
width: 150;
height: 50;
font-size: 20px;
}
.lackButton{
width: 75;
height: 40;
font-size: 15px;
}
</style>
<div align="center">
	<h1>取件</h1>
	<p />
	<p />
	<p />
	<form action="" id = "takeGoodsForm" method="post">
		<input type="hidden" name = "ioDetailId" value = "${ioDetail.detailId}">
		<input type="hidden" id =  "actualAmt" name = "actualAmt" value = "">
	</form>
	<div style="font-size:50px;color:green;font-weight:bold;font-family:楷体">
		批次号：${ioDetail.batchId}
	</div>
	<br/>
	<br/>
	<div style="font-size:50px;color:green;font-weight:bold;font-family:楷体">
		${ioDetail.posLabel}&nbsp;&nbsp;*&nbsp;${ioDetail.amount}
		<br/>
		${title}
	</div>
</div>
<br/>
<br/>
<div align="center">
	<table>
	  <tr>
	    <td colspan="3"><input type="button" id = "takeButton" value="下一步" onclick="nextTakeGoods()" /></td>
	  </tr>
	  <tr>
	  <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
	  </tr>
	  <tr>
	    <td><input type="button" class =  "lackButton" value="全部缺货" onclick="lackAll()" /></td>
	    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	    <td><input type="button" class =  "lackButton" value="部分缺货" onclick="lackPart()" /></td>
	  </tr>
	</table>
</div>
<%@ include file="/footer.jsp"%>