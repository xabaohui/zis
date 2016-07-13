<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>
<script src="JTimer_1.3.js"></script>

<h1>新增图书</h1>
<h2><font color="green"><s:actionmessage /></font></h2>
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
			<td><input type="text" name="bookName" id="bookName"/>
			</td>
		</tr>
		<tr>
			<td>作者</td>
			<td><input type="text" name="bookAuthor" id="bookAuthor"/>
			</td>
		</tr>
		<tr>
			<td>出版社</td>
			<td><input type="text" name="bookPublisher" id="bookPublisher"/>
			</td>
		</tr>
		<tr>
			<td>出版日期</td>
			<td><input type="text" onclick="JTC.setday()" name="publishDate" id="publishDate"
				readonly="readonly" /></td>
		</tr>
		<tr>
			<td>价格</td>
			<td><input type="text" name="bookPrice" id="bookPrice"/>
			</td>
		</tr>
		<tr>
			<td>图书版本</td>
			<td><input type="text" name="bookEdition" id="bookEdition"/>
			</td>
		</tr>

	</table>
	<br>
	<br>
	<input type="submit" value="提交">
    &nbsp;&nbsp;&nbsp;
    <input type="reset" value="取消">
</s:form>

<div id="float-to-be-show">
	<h2>处理中，请稍后...</h2>
</div>
<%@ include file="/footer.jsp"%>