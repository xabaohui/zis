<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
  	<center>
  		<h1>维护院校信息</h1>
  		<s:form action="addSchoolAction" method="post">
  			<h2 style="color:green"><s:property value="MSG"/></h2>
		  	<s:hidden name="id"/>
		  	<s:textfield name="college" label="学校"/>
		  	<s:textfield name="institute" label="院系"/>
		  	<s:textfield name="partName" label="专业"/>
		  	<s:radio name="years" list="#{'3':'专科三年','4':'本科四年','5':'医学五年'}"/>
		  	<s:submit value="提交"/>
		</s:form>
	</center>
  <%@ include file="/footer.jsp"%>