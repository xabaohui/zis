<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>在见书城 - 后台管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<title>左右分开</title>
		<style type="text/css">
			html,body {
				height: 100%;
				margin: 0px;
			}
			
			.left {
				float: left;
				width: 10%;
				background: #FFFAF0;
			}
			
			.right {
				float: right;
				width: 90%;
				background: #F8F8FF;
			}
		</style>
		<style type="text/css">
			#common-table
			  {
			  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			  width:95%;
			  border-collapse:collapse;
			  }
			
			#common-table td, #common-table th 
			  {
			  font-size:1em;
			  border:1px solid #98bf21;
			  padding:3px 7px 2px 7px;
			  }
			
			#common-table th 
			  {
			  font-size:1.1em;
			  text-align:left;
			  padding-top:5px;
			  padding-bottom:4px;
			  background-color:#A7C942;
			  color:#ffffff;
			  }
			
			#common-table tr.alt td 
			  {
			  color:#000000;
			  background-color:#EAF2D3;
			  }
			#bg-to-be-hidden
			  { 
			  display: none;  position: absolute;
			  top: 0%;  left: 0%;  width: 100%;  height: 100%;
			  background-color: black;
			  z-index:1001;  -moz-opacity: 0.7;
			  opacity:.70;  filter: alpha(opacity=70);
			  }
        	#float-to-be-show
        	  {
        	  display: none;  position: absolute;
        	  top: 25%;  left: 22%;  width: 53%;  height: 49%;
        	  padding: 8px;  border: 8px solid #E8E9F7;
        	  background-color: white;
        	  z-index:1002;  overflow: auto;
        	  }
		</style>
	</head>
	<body>
	<div align="center">
	<h1>图书使用量 - 学生版</h1>
		<s:form action="getAmountForStu" method="post">
			<s:textfield name="school" label="学校" required="true"/>
			<s:textfield name="operator" label="收录人" required="true"/>
			<s:submit value="查询"/>
		</s:form>
		<table id="common-table">
			<tr>
			<th></th>
			<th>学校</th>
			<th>学院</th>
			<th>专业</th>
			<th>学年</th>
			<th>ISBN</th>
			<th>书名</th>
			<th>作者</th>
			<th>使用量</th>
			<th>收录人</th>
			<th>时间</th>
			</tr>
			
			<s:iterator id="ba" value="BookAmount"  >
			<tr>
				<td>${ba.id}</td>
				<td>${ba.college}</td>
				<td>${ba.institute}</td>
				<td>${ba.partName}</td>
				<td>第${ba.grade}学年&nbsp;第${ba.term}学期</td>
				<td>${ba.isbn}</td>
				<td>${ba.bookName}</td>
				<td>${ba.bookAuthor}</td>
				<td>${ba.amount}</td>
				<td>${ba.operator}</td>
				<td><s:date name="#ba.gmtCreate" format="MM月dd日 HH:mm" /></td>
			</tr>
			</s:iterator>
		</table>
		
		<!-- 分页查询start -->
		<s:if test="#prePage != null">
			<a href="requirement/getAmountForStu?pageSource=pagination&pageNow=<s:property value="#prePage"/>&bookName=<s:property value="#bookName"/>&school=<s:property value="#school"/>&institute=<s:property value="#institute"/>&partName=<s:property value="#partName"/>&operator=<s:property value="#operator"/>">上一页</a>&nbsp;
		</s:if>
		<s:property value="pageNow" />
		&nbsp;
		<s:if test="#nextPage != null">
			<a href="requirement/getAmountForStu?pageSource=pagination&pageNow=<s:property value="#nextPage"/>&bookName=<s:property value="#bookName"/>&school=<s:property value="#school"/>&institute=<s:property value="#institute"/>&partName=<s:property value="#partName"/>&operator=<s:property value="#operator"/>">下一页</a>&nbsp;
		</s:if>
		<!-- 分页查询end -->
		</div>
<%@ include file="/footer.jsp"%>