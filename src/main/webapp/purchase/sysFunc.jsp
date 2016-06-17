<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>系统功能</h1>
	<s:form action="doPurchase" method="post">
		<s:textfield name="isbn" label="ISBN"/>
		<s:submit value="计算采购计划" />
	</s:form>
	<s:form action="clearOnwayStock" method="post">
		<s:textfield name="purchaseOperator" label="采购员姓名"/>
		<s:submit value="清理在途库存" />
	</s:form>
	<s:form action="batchUpdatePurchasePlanForPurchaseAmount" method="post">
		<s:submit value="重新计算在途库存" />
	</s:form>
	<s:form action="adjustBooks" namespace="/bookinfo" method="post" target="_blank">
		<s:submit value="一码多书&相同图书" />
	</s:form>
	<s:form action="analysisSameBook" namespace="/bookinfo" method="post" target="_blank">
		<s:submit value="智能分析相似图书" />
	</s:form>
	查看相似图书(<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=3">使用中</a>|<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=2">重复</a>|<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=1">未关联</a>|<a href="<%=basePath%>/bookinfo/showSameBooksList">其他</a>)
	<p/><a href="<%=basePath%>/toolkit/stockPosCheck.jsp">库位校准</a>
</center>
<%@ include file="/footer.jsp"%>
