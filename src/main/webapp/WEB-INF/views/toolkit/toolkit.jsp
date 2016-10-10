<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<h1>系统小工具</h1>
<ul>
	<c:forEach items="${showResult}" var="entry">
		<li>${entry}</li>
	</c:forEach>
</ul>

<p />
<h3>
	批量修复书名：删除多余内容<font color="red">（高危操作，慎用）</font>
</h3>
<form action="toolkit/batchFixBookName" method="post">
	<table>
		<tr>
			<td>起始字符</td>
			<td><input type="text" name="startLabel" /></td>
		</tr>
		<tr>
			<td>关键字</td>
			<td><input type="text" name="keyword" /></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2"><input type="submit" value="批量删除" /></td>
		</tr>
	</table>
</form>

<h3>修复修复书名：批量替换</h3>
<form action="toolkit/batchReplaceBookName" method="post">
	把标题中的 <input type="text" name="orig" /> 替换为 <input type="text"
		name="replace" />(留空表示删除)
	<p />
	<input type="submit" value="批量替换" />
</form>
<p />
<h3>批量修复作者：删除作者中的“著”、“编”字样</h3>
<form action="toolkit/fixBookAuthor" method="post">
	<input type="submit" value="批量删除" />
</form>
<p />
<h3>批量修复版次：删除书名中“修订版”、“修订本”，并追加到版次最后</h3>
<form action="toolkit/batchFixEditionByBookName" method="post">
	<input type="submit" value="批量修复" />
</form>

<p />
<h3>批量替换版次：使用书名中的“第X版”代替版次的值</h3>
<form action="toolkit/batchReplaceEditionByBookName" method="post">
	<input type="submit" value="批量替换" />
</form>

<p />
<h3>批量加入黑名单：将包含指定关键词的图书加入黑名单</h3>
<form action="toolkit/batchAddIntoBlackList" method="post" target="_blank">
	关键词<input name="keyword" type="text" />
	<p />
	<input type="submit" value="批量拉黑" />
</form>
<%@ include file="/footer.jsp"%>