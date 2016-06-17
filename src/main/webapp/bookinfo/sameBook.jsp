<%@ page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<h2 align="center">
	<font>重复的图书 </font>
</h2>
<ol>
	<s:iterator value="sameBookList" var="record">
		<li>
			<s:property value="shortBookName" /> 
			（<s:property value="totalCount" />条疑似重复）
			&nbsp;&nbsp;<a href="bookinfo/showSameBooks?sameBookIds=${record.ids}" target="_blank">去处理</a>
			<p/>
		</li>
	</s:iterator>
</ol>
<p/>
<!-- 分页查询start -->
<s:if test="#prePage != null">
	<a
		href="bookinfo/showSameBooksList?pageNow=<s:property value="#prePage"/>&similarityCheckLevel=<s:property value="#similarityCheckLevel"/>">上一页</a>&nbsp;
	</s:if>
<s:property value="pageNow" />
&nbsp;
<s:if test="#nextPage != null">
	<a
		href="bookinfo/showSameBooksList?pageNow=<s:property value="#nextPage"/>&similarityCheckLevel=<s:property value="#similarityCheckLevel"/>">下一页</a>&nbsp;
	</s:if>
<!-- 分页查询end -->

<%@ include file="/footer.jsp"%>
