<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<script type="text/javascript">
	function showShadow() {
		document.getElementById("bg-to-be-hidden").style.display = "block";
		document.getElementById("float-to-be-show").style.display = "block";
	}
</script>

<%-- 记住这里需要设置enctype="multipart/form-data"--%>
<h2>批量导入数据</h2>
<s:form action="purchase/uploadTempRecord" method="post"
	enctype="multipart/form-data">
	<s:file name="excelFile" label="导入Excel文件" labelposition="left"
		required="true" />
	<s:textfield name="memo" label="备注" />
	<s:submit value="导入" onclick="showShadow();" />
</s:form>
表格格式:
<table border="1px">
	<tr>
		<td>条形码</td>
		<td>库存量/店铺状态/标题/类目ID/禁止发布（5选1）</td>
		<td>附加信息(可选)</td>
	</tr>
	<tr>
		<td>9787800878442</td>
		<td>5</td>
		<td>&nbsp;</td>
	</tr>
</table>
<p/>
<h3>说明：</h3>
<ul>
<li>库存量：直接填写数字；</li>
<li>店铺状态：请填写“on_sales”或“sold_out”；</li>
<li>禁止发布：请填写“是”或“否”；</li>
</ul>
<script type="text/javascript">
	document.getElementsByName('bizType')[0].checked = true;
</script>
<br />
<br />
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	正在导入，请耐心等待，大约需要10分钟。<br />
	<br />如果你很无聊，可以先抠一会儿鼻屎，或者<b><a href="#" target="_blank">点此</a>
	</b>开始新的工作<br />
</div>
<%@ include file="/footer.jsp"%>