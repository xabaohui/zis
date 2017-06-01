<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<html>
	<head>
		
		<base href="<%=basePath%>">
		<title>在见书城 - 后台管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		
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
			.common-table-new
			  {
			  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			  width:95%;
			  border-collapse:collapse;
			  }
			
			.common-table-new td, .common-table-new th 
			  {
			  font-size:1em;
			  border:1px solid #98bf21;
			  padding:3px 7px 2px 7px;
			  }
			
			.common-table-new th 
			  {
			  font-size:1.1em;
			  text-align:left;
			  padding-top:5px;
			  padding-bottom:4px;
			  color:#ffffff;
			  }
			
			.common-table-new tr.alt td 
			  {
			  color:#000000;
			  background-color:#EAF2D3;
			  }
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
			.common-table-ajax
			  {
			  font-family:"Trebuchet MS", Arial, Helvetica, sans-serif;
			  width:95%;
			  border-collapse:collapse;
			  }
			
			.common-table-ajax td, .common-table-ajax th 
			  {
			  font-size:1em;
			  border:1px solid #98bf21;
			  padding:3px 7px 2px 7px;
			  }
			
			.common-table-ajax th 
			  {
			  font-size:1.1em;
			  text-align:left;
			  padding-top:5px;
			  padding-bottom:4px;
			  background-color:#A7C942;
			  color:#ffffff;
			  }
			
			.common-table-ajax tr.alt td 
			  {
			  color:#000000;
			  background-color:#EAF2D3;
			  }
			#bg-to-be-hidden
			  { 
			  display: none;  position: absolute;
			  top: 0%;  left: 0%;  width: 100%;  height: 800%;
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
        	 ul li{
        	 	list-style-type: none; 
        	 	line-height: 30px;
        	 }
        	 .showListTop{
        	 	position: relative;
        	 	margin-left: -40px;
        	 }
        	 .uls{
        	 margin-left: -10px;
        	 }
		</style>
		<script type="text/javascript">
		function checked(obj){
			var objThis = obj.childNodes[3];
			var uls = document.getElementsByName("showListTop");
			var thisDisplay = objThis.style.display; 
			if (thisDisplay == "block") {
				objThis.style.display= "none";
			} else {
				for(var i = 0; i < uls.length; i++){
					uls[i].style.display= "none";
				}
				objThis.style.display= "block";
			}
		}
		function rs(rs){
		}
		</script>
	</head>
	<body>
		<div class="left" style="height: 100%;">
			<br />
			<br />
			<b>&nbsp;欢迎<font color="green">[<shiro:principal property="realName"/>]</font></b>
			<br />
			&nbsp;&nbsp;<b>登录</b>
			<br />
			<br />
			<br />
			<shiro:authenticated>
				&nbsp;&nbsp;&nbsp;<b><a href="logout">退出</a></b>
			</shiro:authenticated>
			<br />
			<br />
			<c:if test="${headerChecked eq '1'}"></c:if>
			&nbsp;
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">信息管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop" >
					<shiro:hasPermission name="bookInfo:view">
						<li><a href="<%=basePath%>bookInfo/getAllBooks">商品列表</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="bookInfo:saveOrUpdate">
						<li><a href="<%=basePath%>bookInfo/gotoAddBook">新增商品</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="bookInfo:saveOrUpdate">
						<li><a href="<%=basePath%>bookInfo/gotoAddYouLuData">批量增加</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="toolkit:toolkit">
						<li><a href="https://github.com/xabaohui/zis/issues" target="_blank" onclick="alert('账户名：zisuser，密码：hello1234')">提个建议</a></li>
						<br/>
					</shiro:hasPermission>
				</ul>
			</ul>
			<shiro:hasPermission name="order:storageOrder">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">发货管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop" >
					<br/>
					<li><a href="order/getWaitPickUpList?sort=updateTime&direction=desc">订单信息</a></li>
					<br/>
					<li><a href="storage/querytTakeGoods">开始配货</a></li>
					<br/>
					<li><a href="storage/gotoFastTakeGoods">快速出库</a></li>
<!-- 				<br/> -->
<!-- 				<li><a href="storage/gotoManualTakeGoods">手动出库</a></li> -->
					<br/>
					<li><a href="order/sendOut">出库扫描</a></li>
					<br/>
				</ul>
			</ul>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="order:shopOrder">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">订单管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
						<br/>
						<li><a href="order/getUnpaidList?sort=updateTime&direction=desc">订单信息</a></li>
						<br/>
						<li><a href="order/gotoCreateOrder">订单生成</a></li>
						<br/>
				</ul>
			</ul>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="order:storageOrder or order:shopOrder">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">商品管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop" >
					<br/>
					<li><a href="storage/stock/listProducts">商品查询</a></li>
					<br/>
				</ul>
			</ul>
			</shiro:hasPermission>
<!-- 			<ul class = "uls" onclick="checked(this);"> -->
<!-- 			<font style="font-weight: bolder;">院校模块</font> -->
<!-- 				<ul name = "showListTop" style="display: none" class = "showListTop"> -->
<!-- 					<shiro:hasPermission name="requirement:school:view"> -->
<!-- 						<li><a href="/requirement/findSchoolInfo">院校信息</a></li> -->
<!-- 						<br/> -->
<!-- 					</shiro:hasPermission> -->
<!-- 					<shiro:hasPermission name="requirement:school:saveOrUpdate"> -->
<!-- 						<li><a href="/requirement/updateSchoolPre">添加院校</a></li> -->
<!-- 						<br/> -->
<!-- 					</shiro:hasPermission> -->
<!-- 					<shiro:hasPermission name="requirement:books:view or requirement:books:input or requirement:books:output"> -->
<!-- 						<li><a href="/requirement/getAmountAction">教材使用量</a></li> -->
<!-- 						<br/> -->
<!-- 					</shiro:hasPermission> -->
<!-- 				</ul> -->
<!-- 			</ul> -->
<!-- 			<ul class = "uls" onclick="checked(this);"> -->
<!-- 			<font style="font-weight: bolder;">库存管理</font> -->
<!-- 				<ul name = "showListTop" style="display: none" class = "showListTop"> -->
<!-- 					<shiro:hasPermission name="stock:input"> -->
<!-- 						<li><a href="purchase/gotoInWarehouse">扫描入库</a></li> -->
<!-- 						<br/> -->
<!-- 					</shiro:hasPermission> -->
<!-- 					<shiro:hasPermission name="stock:view or stock:input or stock:output or stock:delete"> -->
<!-- 						<li><a href="purchase/viewInwarehouseList">入库单列表</a></li> -->
<!-- 					</shiro:hasPermission> -->
<!-- 				</ul> -->
<!-- 			</ul> -->
			<shiro:hasPermission name="order:storageOrder">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">库存进销存</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
						<br/>
						<li><a href="<%=basePath%>storage/gotoInwarehouse">批量入库</a></li>
						<br/>
						<li><a href="storage/gotoFastInwarehouse">快速入库</a></li>
						<br/>
						<li><a href="storage/queryIoBatch">出入库批次</a></li>
						<br/>
				</ul>
			</ul>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="order:storageOrder">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">库位管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
						<br/>
						<li><a href="storage/findPosition">库位列表</a></li>
						<br/>
				</ul>
			</ul>
			</shiro:hasPermission>
			
			<shiro:hasPermission name="purchase:view or purchase:management or toolkit:toolkit">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">采购管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
					<shiro:hasPermission name="purchase:view or purchase:management">
						<li><a href="<%=basePath%>purchase/queryPurchasePlan">采购计划</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="purchase:view or purchase:management">
						<li><a href="<%=basePath%>purchase/queryPurchaseDetail">采购明细</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="toolkit:toolkit">
						<li><a href="<%=basePath%>/purchase/gotoSysFunc">采购设置</a></li>
						<br/>
					</shiro:hasPermission>
				</ul>
			</ul>
			</shiro:hasPermission>
			
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">数据处理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
					<shiro:hasPermission name="data:dataInfo">
						<li><a href="<%=basePath%>purchase/viewTempImportTask">数据导入</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="data:dataInfo">
						<li><a href="purchase/gotoTempImportUpload">新表导入</a></li>
					</shiro:hasPermission>
				</ul>
			</ul>
			
				<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">系统管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
					<shiro:hasPermission name="shop:shopInfo">
						<li><a href="<%=basePath%>shop/showCompanyInfo">公司管理</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="toolkit:toolkit">
						<li><a href="<%=basePath%>/purchase/querySysVarAction">系统设置</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="toolkit:toolkit">
						<li><a href="<%=basePath%>/toolkit/gotoToolkit">内容修复</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:authenticated>
						<li><a href="<%=basePath%>shiro/gotoGeneralUserUpdatePassword">密码修改</a></li>
						<br/>
					</shiro:authenticated>
				</ul>
			</ul>
			
			<shiro:hasPermission name="shiro:shiro">
			<ul class = "uls" onclick="checked(this);">
			<font style="font-weight: bolder;">商家管理</font>
				<ul name = "showListTop" style="display: none" class = "showListTop">
					<shiro:hasPermission name="shiro:gotoCreatePermission">
						<li><a href="<%=basePath%>shiro/gotoCreatePermission">创建权限测试用</a></li>
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="shiro:shiro">
						<li><a href="<%=basePath%>shiro/updateWaitUser">用户管理</a></li> 
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="shiro:shiro">
						<li><a href="<%=basePath%>shiro/updateWaitRole">角色管理</a></li> 
						<br/>
					</shiro:hasPermission>
					<shiro:hasPermission name="shiro:shiro">
						<li><a href="<%=basePath%>shop/showCompanys">公司管理</a></li>
						<br/> 
					</shiro:hasPermission>
					<shiro:authenticated>
						<li><a href="<%=basePath%>shiro/gotoGeneralUserUpdatePassword">密码修改</a></li>
					</shiro:authenticated>
				</ul>
			</ul>
			</shiro:hasPermission>
		</div>
		<div class="right">