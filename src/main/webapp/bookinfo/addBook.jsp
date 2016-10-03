<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>
<script src="JTimer_1.3.js"></script>

<h1>新增图书</h1>
<h2><font color="green"><!--<s:actionmessage />  --></font></h2>
<p />
<div id="showExistBook" style="font-weight: bold;color: green"></div>
<form action="bookInfo/saveOrUpdate" method="post">
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
		<tr>
		<td colspan="2"><b>以下内容选填</b></td>
		</tr>
		<tr>
			<td>图片网址</td>
			<td><input type="text" name="imageUrl" value="${book.imageUrl }" /></td>
		</tr>
		<tr>
			<td>网店标题</td>
			<td><input type="text" name="taobaoTitle" value="${book.taobaoTitle }" /></td>
		</tr>
		<tr>
			<td>淘宝类目ID</td>
			<td><input type="text" name="taobaoCatagoryId" value="${book.taobaoCatagoryId }" /></td>
		</tr>
		<tr>
			<td>内容摘要</td>
			<td><textarea name="summary" cols="25" rows="5">${book.summary }</textarea></td>
		</tr>
		<tr>
			<td>目录</td>
			<td><textarea name="catalog" cols="25" rows="5">${book.catalog }</textarea></td>
		</tr>
	</table>
	<br>
	<br>
	<input type="submit" value="提交">
    &nbsp;&nbsp;&nbsp;
    <input type="reset" value="取消">
</form>

<div id="float-to-be-show">
	<h2>处理中，请稍后...</h2>
</div>
<%@ include file="/footer.jsp"%>