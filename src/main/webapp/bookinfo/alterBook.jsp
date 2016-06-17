<%@page import="com.zis.bookinfo.util.ConstantString"%>
<%@page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<script src="JTimer_1.3.js"></script>
<html>
<head>
<title>修改图书</title>

<script type="text/javascript" src="JTimer_1.3.js"></script>

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
		<font color="pink"> <s:property value="showValue" /> </font>
	</h3>

	<s:form action="saveOrUpdateBook" method="post" id="form_update">
		<!-- 用来传操作符的值 -->
		<s:hidden name="operateType" id="operateTypeId" />
		<s:hidden name="bookId" />
		<s:hidden name="isNewEdition" />
		<s:hidden name="repeatIsbn" />
		<table width="301" height="395" border="1">
			<tr>
				<td>图书id <br /></td>
				<td><input type="text" name="id" readonly="readonly"
					value="${book.id }" />
				</td>
			</tr>
			<tr>
				<td>外部id</td>
				<td><input type="text" name="outId" value="${book.outId }"
					readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="isbn" value="${book.isbn }" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>图书名称 <br /></td>
				<td><input type="text" name="bookName"
					value="${book.bookName }" />
				</td>
			</tr>
			<tr>
				<td>图书版次</td>
				<td><input type="text" name="bookEdition"
					value="${book.bookEdition }" />
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td><input type="text" name="bookAuthor"
					value="${book.bookAuthor }" />
				</td>
			</tr>
			<tr>
				<td>出版社</td>
				<td><input type="text" name="bookPublisher"
					value="${book.bookPublisher }" /> <%--      <s:textfield  name="#book.bookPublisher"/> --%>
				</td>
			</tr>
			<tr>
				<td>出版日期</td>
				<td><input type="text" onclick="JTC.setday()"
					name="publishDate"
					value="<s:date name="#book.publishDate"  format="yyyy-MM-dd"  />" />
			</tr>
			<tr>
				<td>价格</td>
				<td><input type="text" name="bookPrice"
					value="${book.bookPrice }" />
				</td>
			</tr>
			<tr>
				<td>是否最新版</td>
				<td>
					<s:if test="#book.isNewEdition==true">是</s:if>
					<s:else>否</s:else>
				</td>
			</tr>
			<tr>
				<!-- 判断状态条件，自动判断 -->
				<td>是否一码多书</td>
				<td>
					<s:if test="#book.repeatIsbn==true">是</s:if>
					<s:else>否</s:else>
				</td>

			</tr>
			<tr>
				<td>状态</td>
				<td>${book.bookStatus }</td>
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
    <input type="button" value="删除" onclick="doModify('notuse')">


		<%
			//当为待审核数据时候显示审核和废弃
				}
				if (ConstantString.WAITCHECK.equals(status)) {
		%>

		<input type="button" value="审核" onclick="doModify('checkok')">
		<input type="reset" value="恢复">
    &nbsp;&nbsp;&nbsp;
    <input type="button" value="删除" onclick="doModify('notuse')">

		<%
			}
		%>



	</s:form>
</body>
</html>
