<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>


<div align="center">
<h1>图书列表</h1>
	<div>
		<a href="bookinfo/getWaitCheckList">还有<s:property
				value="waitCheckCount" />条待审核</a>
		<s:actionmessage/>
		<s:form action="getAllBooks" method="post">
			<s:textfield name="bookISBN" label="ISBN" id="bookISBN"/>
			<s:textfield name="bookName" label="书名" id="bookName"/><input type="checkbox" name="strictBookName" value="true"/>精确匹配书名
			<s:textfield name="bookAuthor" label="作者" id="bookAuthor"/>
			<s:textfield name="bookPublisher" label="出版社" id="bookPublisher"/>
			<s:submit value="查询" />
			<s:reset value="清除条件"/>
		</s:form>
	</div>

	<!-- 嵌入展示数据页面 -->
	<%@ include file="showBookList_Include.jsp"%>

	<!-- 分页查询start -->
	<s:if test="#prePage != null">
		<a
			href="bookinfo/getAllBooks?<s:property value="#queryCondition"/>pageSource=pagination&pageNow=<s:property value="#prePage"/>">上一页</a>&nbsp;
	</s:if>
	<s:property value="pageNow" />
	&nbsp;
	<s:if test="#nextPage != null">
		<a
			href="bookinfo/getAllBooks?<s:property value="#queryCondition"/>pageSource=pagination&pageNow=<s:property value="#nextPage"/>">下一页</a>&nbsp;
	</s:if>
	<!-- 分页查询end -->
</div>
<%@ include file="/footer.jsp"%>
