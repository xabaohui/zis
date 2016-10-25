<%@page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<h2 align="center">
	<font> 
		<c:if test="${pageType == 'pageGroup'}">不同版次的图书</c:if>
		<c:if test="${pageType == 'pageRelate'}">相关图书</c:if> 
	</font>
</h2>

<form action="bookInfo/removeAll" method="post" id="from_checkBox">
	<input type="hidden" name="pageType" value="${pageType}">
	<table id="common-table">
		<tr>
			<th>ID</th>
			<th>ISBN</th>
			<th>书名</th>
			<th width='105'>版次</th>
			<th>作者</th>
			<th>出版社</th>
			<th>出版日期</th>
			<th>价格</th>
			<th>状态</th>
			<th height="30" width="100">操作</th>
		</tr>

		<c:forEach items="${relateList}" var="book">
			<tr>
				<td>${book.id}</td>
				<td><a href="bookInfo/getAllBooks?bookISBN=${book.isbn}">${book.isbn}</a>
					<c:if test="${book.repeatIsbn eq true}">
						<br />
						<font style="font-weight:bold;color:red">[一码多书]</font>
					</c:if></td>
				<td>${book.bookName}&nbsp; [<a href="http://www.youlu.net/${book.outId}" target="_blank">有</a>] [<a href="https://s.taobao.com/search?q=${book.isbn}" target="_blank">淘</a>]</td>
				<td>${book.bookEdition}
					<c:if test="${book.isNewEdition eq true}">
						<font style="font-weight:bold;color:green">[最新]</font>
					</c:if>
					<c:if test="${not empty book.groupId}">
						<a href="bookInfo/showGroupList?groupId=${book.groupId}" target="_blank"><br />其他版本</a>
					</c:if>
				</td>
				<td><a href="bookInfo/getAllBooks?bookAuthor=${book.bookAuthor}&bookPublisher=${book.bookPublisher}">${book.bookAuthor}</a></td>
				<td>${book.bookPublisher}</td>
				<td>${book.publishDate}</td>
				<td>${book.bookPrice}</td>
				<td>${book.bookStatus}</td>
				<td>
					<c:if test="${pageType == 'pageGroup'}">
						<a href="bookInfo/removeRelateId?id=${book.id}&pageType=${pageType}&groupId=${book.groupId}">解除关联</a>
					</c:if>
					<c:if test="${pageType == 'pageRelate'}">	
						<a href="bookInfo/removeRelateId?id=${book.id}&pageType=${pageType}&relateId=${book.relateId}">解除关联</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="submit" value="解除所有关联">

</form>

<%@ include file="/footer.jsp"%>
