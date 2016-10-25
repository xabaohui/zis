<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type="text/javascript">
	function showShadow() {
		var fileValue = document.getElementById("excelFile").value;
		if (fileValue != "") {
			document.getElementById("bg-to-be-hidden").style.display = "block";
			document.getElementById("float-to-be-show").style.display = "block";
		}
	}
</script>

<%-- 记住这里需要设置enctype="multipart/form-data"--%>
<h2>导入书单</h2>
<form action="requirement/uploadBookRequirement" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td>学校</td>
			<td><input type="text" name="college" required="required" /></td>
		</tr>
		<tr>
			<td>操作员</td>
			<td><input type="text" name="operator" required="required" /></td>
		</tr>
		<tr>
			<td>操作备注</td>
			<td><input type="text" name="memo" required="required" /></td>
		</tr>
		<tr>
			<td>导入Excel文件</td>
			<td><input type="file" name="excelFile" required="required" /></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2"><input type="submit" value="导入" onclick="showShadow();" /></td>
		</tr>
	</table>
</form>
<br />
<br />
<table id="common-table">
	<tr>
		<th>条形码</th>
		<th>书名（必填）</th>
		<th>作者（必填）</th>
		<th>版次</th>
		<th>出版社（必填）</th>
		<th>学校（必填）</th>
		<th>学院（必填）</th>
		<th>专业</th>
		<th>年级</th>
		<th>学期</th>
		<th>班级代码</th>
		<th>人数</th>
	</tr>
	<tr>
		<td>9787506436496</td>
		<td>鞋楦设计与制作</td>
		<td>丘理</td>
		<td>第一版</td>
		<td>中国纺织出版社</td>
		<td>长安大学</td>
		<td>电子与控制工程学院</td>
		<td>自动化（交通信息与控制）</td>
		<td>4</td>
		<td>1</td>
		<td>自动化21</td>
		<td>100</td>
	</tr>
</table>
<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	正在导入，请耐心等待，大约需要10分钟。<br /> <br />如果你很无聊，可以先抠一会儿鼻屎，或者<b><a
		href="#" target="_blank">点此</a> </b>开始新的工作<br />
</div>
<%@ include file="/footer.jsp"%>