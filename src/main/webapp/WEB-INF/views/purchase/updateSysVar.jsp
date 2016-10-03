<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<center>
<h1>系统设置</h1>
    <form action="purchase/updateSysVarAction" method="post">
    	<table>
    		<tr>
    			<td>常量名</td>
    			<td><input type="text" name="depKey" value="${depKey}" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td>常量值</td>
    			<td><input type="text" name="depValue" value="${depValue}"/></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td colspan="2"><input type="submit" value="修改"/></td>
    		</tr>
    	</table>
    </form>
  </center>
<%@ include file="/footer.jsp"%>