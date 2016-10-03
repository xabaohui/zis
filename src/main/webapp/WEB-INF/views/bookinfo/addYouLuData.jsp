<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>

<script type="text/javascript">
	function doSubmit(operType) {
		document.getElementById('operateTypeId').value = operType;
		document.getElementById('addYouLuDataForm').submit();
	}
</script>

<div align="center">
	 <h2><font color="green">批量增加有路网数据</font></h2>
	<br>
		${activeTaskCount}&nbsp;个任务正在运行...
	<form action="bookInfo/addYouLuData" method="post" id="addYouLuDataForm">
		<input type="hidden" name="operateType" id="operateTypeId"/>
		<p/>
		<b>起始id</b>&nbsp;<input type="text" name="startId" value="${finalId}"/>
		<p/>
		<b>终止id</b>&nbsp;<input type="text" name="finalId"/>
		<p/>
		<p/>
	   <input type="button" name="submitButton" value="抓取图书数据" onclick="doSubmit('addBookInfo')"/>
	   <input type="button" name="submitButton" value="抓取销售数据" onclick="doSubmit('addSales')"/>
	</form>
	
	</div>
 <%@ include file="/footer.jsp"%>
