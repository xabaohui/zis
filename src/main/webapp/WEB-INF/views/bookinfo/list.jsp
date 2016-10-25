<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>


<div align="center">
<h1>图书列表</h1>
	<div>
		<a href="bookInfo/getWaitCheckList">还有${waitCheckCount}条待审核</a>
		<br/>
		<h3><font color="red">${actionMessage}</font></h3>
		<form action="bookInfo/getAllBooks" method="post">
		<table>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="bookISBN" id="bookISBN" value="${param.bookISBN}"></td>
				<td></td>
			</tr>
			<tr>
				<td>书名</td>
				<td><input type="text" name="bookName" id="bookName" value="${param.bookName}"></td>
				<td><input type="checkbox" name="strictBookName" value="true" checked/>精确匹配书名</td>
			</tr>
			<tr>
				<td>作者</td>
				<td><input type="text" name="bookAuthor" id="bookAuthor" value="${param.bookAuthor}"></td>
				<td></td>
			</tr>
			<tr>
				<td>出版社</td>
				<td><input type="text" name="bookPublisher" id="bookPublisher" value="${param.bookPublisher}"></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="submit" value="查询"></td>
				<td><input type="reset" value="清除条件"></td>
				<td></td>
			</tr>
		</table>
		</form>
		 
	</div>

	<!-- 嵌入展示数据页面 -->
	<%@ include file="showBookList_Include.jsp"%>


	<!-- 分页查询start-->
	<c:if test="${not empty prePage}">
		<a href="bookInfo/getAllBooks?${queryCondition}page=${prePage}">上一页</a>&nbsp;
	</c:if>
	${page}
	&nbsp;
	<c:if test="${not empty nextPage}">
		<a href="bookInfo/getAllBooks?${queryCondition}page=${nextPage}">下一页</a>&nbsp;
	</c:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>
