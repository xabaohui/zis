<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>
<script src="resources/JTimer_1.3.js"></script>

<h1>新增图书</h1>
<h2>
	<font color="green">${actionMessage}</font>
</h2>
<p />
<div id="showExistBook" style="font-weight: bold;color: green"></div>
<spring:form action="bookInfo/saveOrUpdate" method="post" modelAttribute="bookInfoDTO">
	<input type="hidden" name="urlType" value="addBook" />
	<table>
		<tr>
			<td>ISBN</td>
			<td><input type="text" name="isbn" onchange="return checkExistBook()" id="isbn" value="${param.isbn}" /></td>
			<td><spring:errors delimiter="," path="isbn" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>图书名称 <br /></td>
			<td><input type="text" name="bookName" id="bookName" value="${param.bookName}" /></td>
			<td><spring:errors delimiter="," path="bookName" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>作者</td>
			<td><input type="text" name="bookAuthor" id="bookAuthor" value="${param.bookAuthor}" /></td>
			<td><spring:errors delimiter="," path="bookAuthor" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>出版社</td>
			<td><input type="text" name="bookPublisher" id="bookPublisher" value="${param.bookPublisher}" /></td>
			<td><spring:errors delimiter="," path="bookPublisher" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>出版日期</td>
			<td><input type="text" onclick="JTC.setday()" name="publishDate" id="publishDate" readonly="readonly" value="${param.publishDate}" /></td>
			<td><spring:errors delimiter="," path="publishDate" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>价格</td>
			<td><input type="text" name="bookPrice" id="bookPrice" value="${param.bookPrice}" onkeyup="this.value=this.value.replace(/[^\d\.]/g,'')" /></td>
			<td><spring:errors delimiter="," path="bookPrice" cssStyle="color:red"/></td>
		</tr>
		<tr>
			<td>图书版本</td>
			<td><input type="text" name="bookEdition" id="bookEdition" value="${param.bookEdition}" /></td>
			<td><spring:errors delimiter="," path="bookEdition" cssStyle="color:red" /></td>
		</tr>
		<tr>
			<td colspan="2"><b>以下内容选填</b></td>
		</tr>
		<tr>
			<td>图片网址</td>
			<td><input type="text" name="imageUrl" id="imageUrl" value="${param.imageUrl}" /></td>
			<td></td>
		</tr>
		<tr>
			<td>网店标题</td>
			<td><input type="text" name="taobaoTitle" id="taobaoTitle" value="${param.taobaoTitle}" /></td>
			<td></td>
		</tr>
		<tr>
			<td>淘宝类目ID</td>
			<td><input type="text" name="taobaoCatagoryId" id="taobaoCatagoryId" value="${param.taobaoCatagoryId}" /></td>
			<td></td>
		</tr>
		<tr>
			<td>内容摘要</td>
			<td><textarea name="summary" id="summary" cols="25" rows="5">${param.summary}</textarea></td>
			<td></td>
		</tr>
		<tr>
			<td>目录</td>
			<td><textarea name="catalog" id="catalog" cols="25" rows="5">${param.catalog}</textarea></td>
			<td></td>
		</tr>
	</table>
	<br>
	<br>
	<input type="submit" value="提交">&nbsp;&nbsp;&nbsp; <input type="reset" value="取消">
</spring:form>

<div id="float-to-be-show">
	<h2>处理中，请稍后...</h2>
</div>
<%@ include file="/footer.jsp"%>