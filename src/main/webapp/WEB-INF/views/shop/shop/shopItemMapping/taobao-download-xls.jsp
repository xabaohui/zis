<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/shop.js'></script>
<script type="text/javascript">
window.onload = function() {
		checkStatus();
	};
</script>
<style type="text/css">
</style>
<div align="left">
	<h2>淘宝网店数据上传至zis</h2>
	<p/>
	<p/>
	<p/>
	
	<form action="shop/taobaoDownloadItems2Mapping" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>导入Excel文件</td>
				<td><input type="file" name="excelFile" id="excelFile" required="required"></td>
			</tr>
			<tr>
				<td colspan="2"></td>
				<td><input type="submit" value="导入"onclick="showShadow()"></td>
			</tr>
		</table>
		<input type = "hidden" name = "token" value = "${token}">
		<input type = "hidden" name = "shopId" value = "${shopId}">
	</form>
	表格格式:
	<table border="1px">
		<tr>
			<td>宝贝ID</td>
			<td>商品标题</td>
			<td>商家编码</td>
		</tr>
		<tr>
			<td>12312333</td>
			<td>二手XXXXXX</td>
			<td>9787506436496-2</td>
		</tr>
	</table>
	<p/>
	<h4>商家编码格式</h4>
	<ol>
		<li>ISBN-bookId</li>
		<li>ISBN</li>
	</ol>
	<h4><font color="red">bookId为zis中查询出的信息</font></h4>
	<h4><font color="red">当商家编码为单ISBN（第二种选择）时确保在ZIS系统中无一码多书，否则会发送错误邮件</font></h4>
</div>

<%@ include file="/footer.jsp"%>