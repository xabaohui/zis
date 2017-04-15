<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<script type="text/javascript">
</script>
<style type="text/css">
</style>
<div align="center">
	<h1>更新仓库信息</h1>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<form action="storage/updateStorageRepoInfo" method="post">
	<div align="center">
		<table>
			<tr>
				<td>仓库名称:</td>
				<td>
					<input type="text" name="stockName" value = "${stockName}"/>
				</td>
				<td>${errorName}</td>
			</tr>
		</table>
	</div>
	<input type="hidden" name = "token" value ="${token}"/>
	<input type="hidden" name = "repoId" value ="${repoId}"/>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</form>

<%@ include file="/footer.jsp"%>