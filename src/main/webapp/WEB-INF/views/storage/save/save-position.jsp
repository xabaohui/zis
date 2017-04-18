<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
</script>
<style type="text/css">
</style>
<div align="center">
	<h1>新增库位</h1>
	<br />
	<h2>
		<font color="green">${actionMessage}</font>
	</h2>
	<h2>
		<font color="red">${actionError}</font>
	</h2>
	<h2>
		<font color="red">${notResult}</font>
	</h2>
</div>
<p />
<form action="storage/savePosition" method="post">
	<div align="center">
		<table>
			<tr>
				<td>库位标签:</td>
				<td>
					<input type="text" name="label" value = "${param.label}"/>
				</td>
			</tr>
		</table>
	</div>
	<input type="hidden" name = "token" value ="${token}"/>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</form>

<%@ include file="/footer.jsp"%>