<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>
<script src="JTimer_1.3.js"></script>

<!-- <script type="text/javascript"> -->
<!-- function trim(str){ -->
<!-- 	return str.replace(/(^\s*)|(\s*$)/g, ""); -->
<!-- } -->

<!-- function checkExistBook() { -->
<!-- 	var s = '    abcdefg '; -->
<!-- 	alert('result:' + trim(s)); -->
<!-- } -->
<!-- </script> -->

<h1>新增图书</h1>
<p />
<div id="showExistBook" style="font-weight: bold;color: green"></div>
<s:form action="saveOrUpdateBook" method="post">
	<table width="301" height="395" border="1">
		<tr>
			<td>ISBN</td>
			<td><input type="text" name="isbn" onblur="return checkExistBook()" id="isbn"/>
			</td>
		</tr>
		<tr>
			<td>图书名称 <br /></td>
			<td><input type="text" name="bookName" />
			</td>
		</tr>
		<tr>
			<td>作者</td>
			<td><input type="text" name="bookAuthor" />
			</td>
		</tr>
		<tr>
			<td>出版社</td>
			<td><input type="text" name="bookPublisher" />
			</td>
		</tr>
		<tr>
			<td>出版日期</td>
			<td><input type="text" onclick="JTC.setday()" name="publishDate"
				readonly="readonly" /></td>
		</tr>
		<tr>
			<td>价格</td>
			<td><input type="text" name="bookPrice" />
			</td>
		</tr>
		<tr>
			<td>图书版本</td>
			<td><input type="text" name="bookEdition" />
			</td>
		</tr>

	</table>
	<br>
	<br>
	<input type="submit" value="提交">
    &nbsp;&nbsp;&nbsp;
    <input type="reset" value="取消">

</s:form>
<%@ include file="/footer.jsp"%>