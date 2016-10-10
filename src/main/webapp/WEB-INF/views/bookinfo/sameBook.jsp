<%@ page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/header.jsp"%>

<h2 align="center">
	<font>重复的图书 </font>
</h2>
<ol>
	<c:forEach items="${sameBookList}" var="record">
		<li>
			${record.shortBookName}
			（${record.totalCount}条疑似重复）
			&nbsp;&nbsp;<a href="bookInfo/showSameBooks?sameBookIds=${record.ids}" target="_blank">去处理</a>
			<p/>
		</li>
	</c:forEach>
</ol>
<p/>
<!-- 分页查询start -->
<c:if test="${not empty prePage}">
	<a
		href="bookInfo/showSameBooksList?page=${prePage}&similarityCheckLevel=${similarityCheckLevel}">上一页</a>&nbsp;
	</c:if>
${pageNow}
&nbsp;
<c:if test="${not empty nextPage}">
	<a
		href="bookInfo/showSameBooksList?page=${nextPage}&similarityCheckLevel=${similarityCheckLevel}">下一页</a>&nbsp;
	</c:if>
<!-- 分页查询end -->

<%@ include file="/footer.jsp"%>
