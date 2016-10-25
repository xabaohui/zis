<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<script type="text/javascript">
	function doSubmit(operType) {
		document.getElementById('operateTypeId').value = operType;
		document.getElementById('addYouLuDataForm').submit();
	}
</script>

<div align="center">
	<h2>
		<font color="green">批量增加有路网数据</font>
	</h2>
	<br> ${activeTaskCount}&nbsp;个任务正在运行...
	<spring:form action="bookInfo/addYouLuData" method="post" id="addYouLuDataForm" modelAttribute="addYouLuDataDTO">
		<input type="hidden" name="operateType" id="operateTypeId" />
		<p />
		<b>起始id</b>&nbsp;<input type="text" name="startId" value="${finalId}" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" />
						   <spring:errors delimiter="," path="startId" cssStyle="color:red" />
		<p />
		<b>终止id</b>&nbsp;<input type="text" name="finalId" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" />
						   <spring:errors delimiter="," path="finalId" cssStyle="color:red" />
		<p />
		<p />
		<input type="button" name="submitButton" value="抓取图书数据" onclick="doSubmit('addBookInfo')" />
		<input type="button" name="submitButton" value="抓取销售数据" onclick="doSubmit('addSales')" />
	</spring:form>

</div>
<%@ include file="/footer.jsp"%>
