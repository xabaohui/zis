<%@ page language="java" import="javax.servlet.http.HttpServletRequest" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/addAmountBiz.js'></script>
<script type='text/javascript' src='dwr/interface/bookService.js'></script>
<script type='text/javascript' src='resources/common.js'></script>
<script type='text/javascript' src='resources/requirement.js'></script>
<script type="text/javascript">
	window.onload = function() {
		selectRadio();
	};
</script>

<h1>录入图书使用情况</h1>
<spring:form action="requirement/addAmount" method="post" modelAttribute="dto" id="addAmountForm">
	<input type="hidden" name="did" id="departId" value="${did}" />
	<input type="hidden" id="isGrade" value="${grade}" />
	<input type="hidden" id="isTerm" value="${term}" />
	<table>
		<tr>
			<td>学校：</td>
			<td><input name="college" type="text" readonly="readonly" value="${college}" />
			</td>
			<td><spring:errors delimiter="," path="college" cssStyle="color:red" />
			</td>
		</tr>
		<tr>
			<td>学院：</td>
			<td><input name="institute" type="text" readonly="readonly" value="${institute}" />
			</td>
			<td><spring:errors delimiter="," path="institute" cssStyle="color:red" />
			</td>
		</tr>
		<tr>
			<td>专业：</td>
			<td><input name="partName" type="text" readonly="readonly" value="${partName}" />
			</td>
			<td><spring:errors delimiter="," path="partName" cssStyle="color:red" />
			</td>
		</tr>
	</table>
	<%
		int m = (Integer) request.getAttribute("grade");
			out.print("学年：");
			for (int i = 1; i <= m; i++) {
				if (m == i) {
	%>
	<input type="radio" name="grade" value="<%=i%>" checked="checked" />第<%=i%>学年
	<%
		} else {
	%>
	<input type="radio" name="grade" value="<%=i%>" />第<%=i%>学年
	<%
		}
			}
	%>
	<spring:errors delimiter="," path="grade" cssStyle="color:red" />
	<br> 学期：<input name="term" type="radio" value="1" />第一学期 
				<input name="term" type="radio" value="2" />第二学期 <br>
	<spring:errors delimiter="," path="term" cssStyle="color:red" />
	<div id="bookListAdded"></div>
	<input type="button" value="添加新记录" onclick="addNewBook()" />
	<br>
	<div id="searchResult"></div>
	<br>
	<table>
		<tr>
			<td>图书数量：</td>
			<td><input name="amount" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" value="${amount}" />
			</td>
			<td><spring:errors delimiter="," path="amount" cssStyle="color:red" />
			</td>
		</tr>
		<tr>
			<td>操作人：</td>
			<td><input name="operator" type="text" value="${operator}" />
			</td>
		</tr>
	</table>
	<input type="button" value="添加教材使用量" onclick="submitForm();">
</spring:form>

<div id="bg-to-be-hidden"></div>
<div id="float-to-be-show">
	<h1>正在查找，请耐心等待...</h1>
</div>
<%@ include file="/footer.jsp"%>