<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

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
			
			h1 {font-size: 32px; display : inline}
			h2 {font-size: 24px; display : inline}
			h3 {font-size: 20px; display : inline}
			
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
		<div class="left">
			<p>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>bookInfo/getAllBooks">图书列表</a> &nbsp;
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>bookInfo/gotoAddBook">新增图书</a> &nbsp;
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>bookInfo/gotoAddYouLuData">批量增加</a> &nbsp;
				<br />
				<br />
				&nbsp;==========
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/requirement/findSchoolInfo">院校信息</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/requirement/updateSchoolPre">添加院校</a> &nbsp;
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/requirement/getAmountAction">教材使用量</a>
				<br />
				<br />
				&nbsp;==========
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>purchase/queryPurchasePlan">采购计划</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>purchase/queryPurchaseDetail">采购明细</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>purchase/viewTempImportTask">数据导入</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>purchase/gotoInWarehouse">扫描入库</a>
				<br />
				<br />
				&nbsp;==========
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/purchase/gotoSysFunc">系统功能</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/purchase/querySysVarAction">系统设置</a>
				<br />
				<br />
				&nbsp;
				<a href="<%=basePath%>/toolkit/gotoToolkit">内容修复</a>
				<br />
				<br />
				&nbsp;
				<a href="https://github.com/xabaohui/zis/issues" target="_blank" onclick="alert('账户名：zisuser，密码：hello1234')">提个建议</a>

			</p>
		</div>
		<div class="right">