<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>系统功能</h1>
	<form action="purchase/doPurchase" method="post">
		<table>
			<tr>
				<td>ISBN</td>
				<td><input type="text" name="isbn" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input type="submit" value="计算采购计划" /></td>
			</tr>
		</table>
	</form>
	<form action="purchase/clearOnwayStock" method="post">
		<table>
			<tr>
				<td>采购员姓名</td>
				<td><input type="text" name="purchaseOperator" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input type="submit" value="清理在途库存" /></td>
			</tr>
		</table>
	</form>
	<form action="purchase/batchUpdatePurchasePlanForPurchaseAmount" method="post">
		<input type="submit" value="重新计算在途库存" />
	</form>
	<form action="bookInfo/adjustBooks" method="post" target="_blank">
		<input type="submit" value="一码多书&相同图书" />
	</form>
	<form action="bookInfo/analysisSameBook" method="post" target="_blank">
		<input type="submit" value="智能分析相似图书" />
	</form>
	查看相似图书(<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=3">使用中</a>
				|<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=2">重复</a>
				|<a href="<%=basePath%>/bookinfo/showSameBooksList?similarityCheckLevel=1">未关联</a>
				|<a href="<%=basePath%>/bookinfo/showSameBooksList">其他</a>)
				<p />
				<a href="<%=basePath%>/toolkit/stockPosCheck.jsp">库位校准</a>
</center>
<%@ include file="/footer.jsp"%>
