<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/header.jsp"%>
<h1>快速入库输入库位</h1>
<h3>
	
</h3>
<p />
请使用IE浏览器，其他浏览器可能无法正确播放声音提示
<p />
<font color="red">${actionError}</font>
<spring:form action="storage/fastInWarehouse">
	<table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">库位标签
				<p />
					<input type="text" name="stockPosLabel" size="15" />&nbsp;
				<p />
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="下一步"></td>
		</tr>
	</table>
</spring:form>

<%@ include file="/footer.jsp"%>