<%@ page language="java" import="java.util.*,com.opensymphony.xwork2.ActionContext" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
  	<script type='text/javascript' src='dwr/engine.js'></script>
  	<script type='text/javascript' src='dwr/util.js'></script>
  	<script type='text/javascript' src='dwr/interface/addAmountBiz.js'></script>
  	<script type='text/javascript' src='dwr/interface/bookService.js'></script>
  	<script type='text/javascript' src='resources/common.js'></script>
  	<script type='text/javascript' src='resources/requirement.js'></script>
  	
    	<h1>录入图书使用情况</h1>
    	<s:form action="addAmount" method="post" theme="simple">
    		<s:hidden name="did" id="departId"/>
    		学校：<s:textfield name="college" disabled="true"/><br>
    		学院：<s:textfield name="institute" disabled="true"/><br>
    		专业：<s:textfield name="partName" disabled="true"/><br>
	<%
		int m = (Integer) ActionContext.getContext().get("grade");
		out.print("学年：");
		for (int i = 1; i <= m; i++) {
	%>
		<input type="radio" name="grade" value="<%=i%>"/>第<%=i %>学年
	<%
		}
	 %>	
	 <br>
	学期：<s:radio name="term" list="#{'1':'第一学期','2':'第二学期'}" label="学期"/><br>
    		<div id="bookListAdded"></div>
    		<input type="button" value="添加新记录" onclick="addNewBook()"/><br>
    		<div id="searchResult"></div><br>
    		图书数量：<s:textfield name="amount"/><br>
    		操作人：<s:textfield name="operator"/><br>
    		<s:submit value="添加教材使用量"/>
    	</s:form>
    	
<div id="bg-to-be-hidden">
</div>
<div id="float-to-be-show">
	<h1>正在查找，请耐心等待...</h1>
</div>
<%@ include file="/footer.jsp"%>