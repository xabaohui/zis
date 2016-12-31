<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>欢迎使用系统...</h1>
<div align="center">
	<c:if test="${waitCheckCount ge 1}">
		<a href="bookinfo/getWaitCheckList">您有${waitCheckCount }条待审核的数据 </a>
	</c:if>
</div>
<%@ include file="/footer.jsp"%>
