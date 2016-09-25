<%@ page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<h2 align="center">
	<font>待处理图书 </font>
</h2>

<!-- 嵌入展示数据页面 -->
<%@ include file="showBookList_Include.jsp"%>

<%@ include file="/footer.jsp"%>
