<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<s:property value="activeTaskCount" />&nbsp;个任务正在运行...
	<s:form action="addYouLuData" method="post" id="addYouLuDataForm">
		<s:hidden name="operateType" id="operateTypeId"/>
		<p/>
		起始id<input type="text" name="startId" value="${finalId}"/>
		<p/>
		终止id<input type="text" name="finalId"/>
		<p/>
		<p/>
	   <input type="button" name="submitButton" value="抓取图书数据" onclick="doSubmit('addBookInfo')"/>
	   <input type="button" name="submitButton" value="抓取销售数据" onclick="doSubmit('addSales')"/>
	</s:form>
	
	</div>
 <%@ include file="/footer.jsp"%>
