<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>欢迎使用系统...</h1>
<div align="center">
	<s:if test="#waitCheckCount>=1">
		<a href="bookinfo/getWaitCheckList">您有${waitCheckCount }条待审核的数据 </a>
	</s:if>
</div>
<%@ include file="/footer.jsp"%>
