<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
  	<center>
  		<h1>维护院校信息</h1>
  		<form action="requirement/addSchoolAction" method="post">
  			<h2 style="color:green">${MSG}</h2>
  			<input name="id" type="hidden" value="${id}">
  			<table>
  			<tr>
  			<td>学校</td>
  			<td><input name="college" type="text"></td>
  			</tr>
  			<tr>
  			<td>院系</td>
  			<td><input name="institute" type="text"></td>
  			</tr>
  			<tr>
  			<td>专业</td>
  			<td><input name="partName" type="text"></td>
  			</tr>
  			<tr>
  			<td></td>
  			<td>
  			<input name="years" type="radio" value="3">专科三年
  			<input name="years" type="radio" value="4">本科四年
  			<input name="years" type="radio" value="5">医学五年
  			</td>
  			</tr>
  			<tr>
  			<td></td>
  			<td colspan="2"><input value="提交" type="submit"></td>
  			</tr>
  			</table>
		</form>
	</center>
  <%@ include file="/footer.jsp"%>