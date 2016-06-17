<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
  		<h1>系统小工具</h1>
  		<s:actionerror cssStyle="color:red;"/>
  		<s:fielderror cssStyle="color:red;"/>
  		<s:actionmessage cssStyle="color:green;"/>
  		<ul>
  		<s:iterator value="showResult" var="entry">
			<li><s:property value="entry"/></li>
  		</s:iterator>
  		</ul>
  		
  		<p/>
  		<h3>批量修复书名：删除多余内容<font color="red">（高危操作，慎用）</font></h3>
  		<s:form action="batchFixBookName" method="post">
		  	<s:textfield name="startLabel" label="起始字符"/>
		  	<s:textfield name="keyword" label="关键字"/>
		  	<s:submit value="批量删除"/>
		</s:form>
		
		<h3>修复修复书名：批量替换</h3>
  		<s:form action="batchReplaceBookName" method="post" theme="simple">
		  	把标题中的<s:textfield name="orig" label="原始内容"/>替换为
		  	<s:textfield name="replace" label="替换内容"/>(留空表示删除)
		  	<p/>
		  	<s:submit value="批量替换"/>
		</s:form>
		
		<p/>
		<h3>批量修复作者：删除作者中的“著”、“编”字样</h3>
  		<s:form action="fixBookAuthor" method="post">
		  	<s:submit value="批量删除"/>
		</s:form>
		
		<p/>
		<h3>批量修复版次：删除书名中“修订版”、“修订本”，并追加到版次最后</h3>
  		<s:form action="batchFixEditionByBookName" method="post">
		  	<s:submit value="批量修复"/>
		</s:form>
		
		<p/>
		<h3>批量替换版次：使用书名中的“第X版”代替版次的值</h3>
  		<s:form action="batchReplaceEditionByBookName" method="post">
		  	<s:submit value="批量替换"/>
		</s:form>
		
		<p/>
		<h3>批量加入黑名单：将包含指定关键词的图书加入黑名单</h3>
		<s:form action="batchAddIntoBlackList" method="post" target="_blank">
			<s:textfield name="keyword" label="关键词"/>
			<s:submit value="批量拉黑"/>
		</s:form>
  <%@ include file="/footer.jsp"%>