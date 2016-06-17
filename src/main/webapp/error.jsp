<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>出错啦.....</h1>
<s:fielderror cssStyle="color:red;" />
<s:actionerror cssStyle="color:red;" />

<%@ include file="/footer.jsp"%>