<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/purchaseService.js'></script>
<script type='text/javascript' src='resources/purchase.js'></script>
<script type="text/javascript">
// 展示操作区
function showOperate(bookId) {
	document.getElementById('hidden_tr_' + bookId).removeAttribute("style");
}
</script>

<center>
	<h1>采购计划</h1>
	<form action="purchase/queryPurchasePlan" method="post">
		<table>
			<tr>
				<td>ISBN</td>
				<td>
				<input type="text" name="isbn" value="${isbn}">
				</td>
			</tr>
			<tr>
				<td>书名</td>
				<td>
				<input type="text" name="bookName" value="${bookName}"><input type="checkbox" name="strictBookName" value="true"/>精确匹配
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td><input type="text" name="bookAuthor" value="${bookAuthor}">
			</tr>
			<tr>
				<td>出版社</td>
				<td><input type="text" name="bookPublisher" value="${bookPublisher}">
				</td>
			</tr>
			<tr>
				<td>计划量</td>
				
				<td><input type="text" name="minPlanAmount" value="${minPlanAmount}" size="4">&nbsp;到&nbsp;<input type="text" name="maxPlanAmount" value="${maxPlanAmount}" size="4">
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right">
				<input type="submit" value="查询">&nbsp;&nbsp;<input type="reset" value="清除条件"></td>
			</tr>
		</table>
	</form>
	<table id="common-table">
		<tr>
			<th>ISBN</th>
			<th>书名</th>
			<th>作者</th>
			<th>版次</th>
			<th>出版社</th>
			<th>出版日期</th>
			<th>批发价</th>
			<th>计划</th>
			<th>库存</th>
			<th>在途</th>
			<th>还需</th>
			<th>&nbsp;</th>
		</tr>
		<c:forEach items="${resultList}" var="purchasedPlan">
			<tr>
				<td>
					<a href="bookinfo/getAllBooks?bookISBN=${purchasedPlan.isbn}" target="_blank">
					${purchasedPlan.isbn}
					</a>
					<c:if test="${purchasedPlan.repeatIsbn} == true">
						<br /><font style="font-weight:bold;color:red">[多]</font>
					</c:if>
					<c:if test="${purchasedPlan.flag} == 'black'">
						[黑]
					</c:if>
					<c:if test="${purchasedPlan.flag} == 'white'">
						[白]
					</c:if>
				</td>
				<td>${purchasedPlan.bookName}
				 	[<a href="https://s.taobao.com/search?q=${purchasedPlan.isbn}" target="_blank">淘</a>]
				 	[<a href="http://search.dangdang.com/?key=${purchasedPlan.isbn}" target="_blank">当</a>]
				</td>
				<td>${purchasedPlan.bookAuthor}
				</td>
				<td>${purchasedPlan.bookEdition}
				<c:if test="${purchasedPlan.isNewEdition} == true">
					<div style="color:green;font-weight:bold">[最新]</div>
				</c:if>
				</td>
				<td>${purchasedPlan.bookPublisher}
				</td>
				<td>${purchasedPlan.publishDate}</td>
				<td>${purchasedPlan.bookPrice}
				<td>
					<a href="#" onclick="return editPurchasePlanManualDecision(${purchasedPlan.bookId});">
						<c:if test="${purchasedPlan.manualDecisionFlag} == true">
							${purchasedPlan.manualDecision}[人]
						</c:if>
						<c:if test="${purchasedPlan.manualDecisionFlag} == false">
							${purchasedPlan.requireAmount}
						</c:if>
					</a>
				</td>
				<td>
					<a href="#" onclick="return editPurchasePlanStock(${purchasedPlan.bookId});">
						${purchasedPlan.stockAmount}
					</a>
				</td>
				<td><c:if test="${purchasedPlan.purchasedAmount} > 0">
						<a href="purchase/queryPurchaseDetail?bookId=${purchasedPlan.bookId}&status=purchased">${purchasedPlan.purchasedAmount}</a>
					</c:if>
					<c:if test="${purchasedPlan.purchasedAmount} <= 0">
						${purchasedPlan.purchasedAmount}
					</c:if>
				</td>
				<td>${purchasedPlan.stillRequireAmount}
				</td>
				<td><a href="javascript:showOperate(${purchasedPlan.bookId})">操作</a></td>
			</tr>
			<tr id="hidden_tr_${bookId}" style="display:none">
				<td colspan="12" align="right">
					<a href="#" onclick="return addToBlackList(${purchasedPlan.bookId});">设为黑名单</a>&emsp;
					<a href="#" onclick="return addToWhiteList(${purchasedPlan.bookId});">设为白名单</a>&emsp;
					<a href="#" onclick="return removePurchasePlanFlag(${purchasedPlan.bookId});">取消黑白名单标记</a>&emsp;
					<a href="#" onclick="return removeManualDecision(${purchasedPlan.bookId});">删除人工定量</a>&emsp;
					<a href="#" onclick="return recalculatePurchasePlan(${purchasedPlan.bookId});">重新计算</a>&emsp;
					<p/>
				</td>
			</tr>
			
		</c:forEach>
	</table>
	<div id="float-to-be-show">
		<h2>处理中，请稍后...</h2>
	</div>
	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>