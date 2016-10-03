<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 分页查询start -->
<div align="center">
<p/>
<c:if test="${not empty prePage}">
	<a href="${actionUrl}?${queryCondition}pageSource=pagination&pageNow=${prePage}" >上一页</a>
	<!--<a href="%{actionUrl}?%{queryCondition}pageSource=pagination&pageNow=%{prePage}"></a>-->
</c:if>
&nbsp;
${pageNow}
&nbsp;
<c:if test="${not empty nextPage}">
	<a href="${actionUrl}?${queryCondition}pageSource=pagination&pageNow=${nextPage}" >下一页</a>
</c:if>
</div>
<!-- 分页查询end -->