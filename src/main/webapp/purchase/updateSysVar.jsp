<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
<h1>系统设置</h1>
    <s:form action="updateSysVarAction" method="post">
    	<s:textfield name="depKey" label="常量名" readonly="readonly"/>
    	<s:textfield name="depValue" label="常量值"/>
    	<s:submit value="修改"/>
    </s:form>
  </center>
<%@ include file="/footer.jsp"%>