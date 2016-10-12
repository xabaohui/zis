<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script language="javascript" type="text/javascript">
	function showShadow() {
		var fileValue = document.getElementById("excelFile").value;
		if (fileValue != "") {
			document.getElementById("bg-to-be-hidden").style.display = "block";
			document.getElementById("float-to-be-show").style.display = "block";
		}
	}
</script>

<%-- 记住这里需要设置enctype="multipart/form-data"--%>
<h2>批量导入教材使用量</h2>
<form action="requirement/importRequirement" method="post"
	enctype="multipart/form-data">
	<table>
		<tr>
			<td>导入Excel文件</td>
			<td><input type="file" name="excelFile" id="excelFile" required="required" />
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td><input type="submit" value="导入" onclick="showShadow();"/>
			</td>
		</tr>
	</table>
</form>
<br />
<br />
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	正在导入，请耐心等待，大约需要10分钟。<br />
	<br />如果你很无聊，可以先抠一会儿鼻屎，或者<b><a href="#" target="_blank">点此</a>
	</b>开始新的工作<br />
</div>
<%@ include file="/footer.jsp"%>