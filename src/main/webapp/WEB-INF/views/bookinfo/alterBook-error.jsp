<%@page import="com.zis.bookinfo.util.ConstantString"%>
<%@page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<script src="resources/JTimer_1.3.js"></script>
<html>
<head>
<title>修改图书</title>

<script type="text/javascript" src="resources/JTimer_1.3.js"></script>

<script type="text/javascript">
	function doModify(operateType) {
		document.getElementById("operateTypeId").value = operateType;
		document.getElementById("form_update").submit();
	}
</script>
</head>

<body>
	<h2>
		<font color="green">修改图书信息</font>
	</h2>
	<h3>
		<font color="pink"></font>
	</h3>

	<spring:form action="bookInfo/saveOrUpdate" method="post" modelAttribute="bookInfoDTO" id="form_update">
		<!-- 用来传操作符的值 -->
		<input type="hidden" name="operateType" id="operateTypeId" value="${operateType}">
		<input type="hidden" name="bookId" value="${bookId}">
		<input type="hidden" name="isNewEdition" value="${isNewEdition}">
		<input type="hidden" name="repeatIsbn" value="${repeatIsbn}">
		<input type="hidden" name="urlType" value="alterBook">
		<table width="301" height="395" border="1">
			<tr>
				<td>图书id <br /></td>
				<td><input type="text" name="id" readonly="readonly" value="${book.id}" /></td>
			</tr>
			<tr>
				<td>外部id</td>
				<td><input type="text" name="outId" value="${book.outId}" readonly="readonly" /></td>
				<td><spring:errors delimiter="," path="outId" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="isbn" value="${book.isbn}" readonly="readonly" /></td>
				<td><spring:errors delimiter="," path="isbn" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>图书名称 <br /></td>
				<td><input type="text" name="bookName" value="${book.bookName}" /></td>
				<td><spring:errors delimiter="," path="bookName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>图书版次</td>
				<td><input type="text" name="bookEdition" value="${book.bookEdition}" /></td>
				<td><spring:errors delimiter="," path="bookEdition" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>作者</td>
				<td><input type="text" name="bookAuthor" value="${book.bookAuthor}" /></td>
				<td><spring:errors delimiter="," path="bookAuthor" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>出版社</td>
				<td><input type="text" name="bookPublisher" value="${book.bookPublisher}" /></td>
				<td><spring:errors delimiter="," path="bookPublisher" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>出版日期</td>
				<td><input type="text" onclick="JTC.setday()" name="publishDate" value="${book.publishDate}" /></td>
			</tr>
			<tr>
				<td>价格</td>
				<td><input type="text" name="bookPrice" value="${book.bookPrice}" /></td>
				<td><spring:errors delimiter="," path="bookPrice" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>是否最新版</td>
				<td>
					<c:if test="${book.isNewEdition eq true}">是</c:if>
					<c:if test="${book.isNewEdition eq false}">否</c:if>
				</td>
			</tr>
			<tr>
				<!-- 判断状态条件，自动判断 -->
				<td>是否一码多书</td>
				<td>
					<c:if test="${book.repeatIsbn eq true}">是</c:if>
					<c:if test="${book.repeatIsbn eq false}">否</c:if>
				</td>

			</tr>
			<tr>
				<td>状态</td>
				<td>${book.bookStatus}</td>
			</tr>
			<tr>
				<td colspan="2"><b>以下内容选填</b></td>
			</tr>
			<tr>
				<td>图片网址</td>
				<td><input type="text" name="imageUrl" value="${book.imageUrl}" />
				<a href="${book.imageUrl}" target="_blank">图</a></td>
			</tr>
			<tr>
				<td>网店标题</td>
				<td><input type="text" name="taobaoTitle" value="${book.taobaoTitle}" /></td>
			</tr>
			<tr>
				<td>淘宝类目ID</td>
				<td><input type="text" name="taobaoCatagoryId" value="${book.taobaoCatagoryId}" /></td>
			</tr>
			<tr>
				<td>内容摘要</td>
				<td><textarea name="summary" cols="25" rows="5">${book.summary}</textarea></td>
			</tr>
			<tr>
				<td>目录</td>
				<td><textarea name="catalog" cols="25" rows="5">${book.catalog}</textarea></td>
			</tr>
		</table>
		<br>
		<br>
		<%
			Bookinfo bk = (Bookinfo) request.getAttribute("book");
				String status = bk.getBookStatus();
				//当为正式数据时候显示修改和废弃
				if (ConstantString.USEFUL.equals(status)) {
		%>

		<input type="button" value="修改" onclick="doModify('update')">
		<input type="reset" value="恢复">
   		&nbsp;&nbsp;&nbsp;
   		<shiro:hasPermission name="bookInfo:delete">
    		<input type="button" value="删除" onclick="doModify('notuse')">
   		</shiro:hasPermission>


		<%
			//当为待审核数据时候显示审核和废弃
				}
				if (ConstantString.WAITCHECK.equals(status)) {
		%>

		<input type="button" value="审核" onclick="doModify('checkok')">
		<input type="reset" value="恢复">
    	&nbsp;&nbsp;&nbsp;
    	<shiro:hasPermission name="bookInfo:delete">
    		<input type="button" value="删除" onclick="doModify('notuse')">
		</shiro:hasPermission>
		<%
			}
		%>

	</spring:form>
</body>
</html>
