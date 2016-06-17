<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- 分页查询start -->
<div align="center">
<p/>
<s:if test="#prePage != null">
	<s:a href="%{actionUrl}?%{queryCondition}pageSource=pagination&pageNow=%{prePage}">上一页</s:a>
</s:if>
&nbsp;
<s:property value="#pageNow" />
&nbsp;
<s:if test="#nextPage != null">
	<s:a href="%{actionUrl}?%{queryCondition}pageSource=pagination&pageNow=%{nextPage}">下一页</s:a>
</s:if>
</div>
<!-- 分页查询end -->