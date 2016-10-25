<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/header.jsp"%>
<center>
<h1>系统设置</h1>
    <spring:form action="purchase/updateSysVarAction" method="post" modelAttribute="dto">
    	<table>
    		<tr>
    			<td>常量名</td>
    			<td><input type="text" name="depKey" value="${depKey}" readonly="readonly"/></td>
    			<td></td>
    		</tr>
    		<tr>
    			<td>常量值</td>
    			<td><input type="text" name="depValue" value="${depValue}" onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/></td>
    			<td><spring:errors delimiter="," path="depValue" cssStyle="color:red"/></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td colspan="2"><input type="submit" value="修改"/></td>
    			<td></td>
    		</tr>
    	</table>
    </spring:form>
  </center>
<%@ include file="/footer.jsp"%>