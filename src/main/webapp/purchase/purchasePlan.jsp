<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

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
	<s:form action="queryPurchasePlan" method="post" theme="simple">
		<table>
			<tr>
				<td>ISBN</td>
				<td><s:textfield name="isbn" label="ISBN" />
				</td>
			</tr>
			<tr>
				<td>书名</td>
				<td><s:textfield name="bookName" label="书名"/><input type="checkbox" name="strictBookName" value="true"/>精确匹配
				</td>
			</tr>
			<tr>
				<td>作者</td>
				<td><s:textfield name="bookAuthor" />
			</tr>
			<tr>
				<td>出版社</td>
				<td><s:textfield name="bookPublisher" label="出版社" />
				</td>
			</tr>
			<tr>
				<td>计划量</td>
				<td><s:textfield name="minPlanAmount" size="4" />&nbsp;到&nbsp;<s:textfield
						name="maxPlanAmount" size="4" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="right"><s:submit value="查询" /><s:reset value="清除条件" /></td>
			</tr>
		</table>
	</s:form>
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
		<s:iterator value="resultList" var="purchasedPlan">
			<tr>
				<td>
					<a href="bookinfo/getAllBooks?bookISBN=<s:property value="isbn"/>" target="_blank">
					<s:property value="isbn" />
					</a>
					<s:if test="#purchasedPlan.repeatIsbn == true">
						<br /><font style="font-weight:bold;color:red">[多]</font>
					</s:if>
					<s:if test="#purchasedPlan.flag == 'black'">
						[黑]
					</s:if>
					<s:if test="#purchasedPlan.flag == 'white'">
						[白]
					</s:if>
				</td>
				<td><s:property value="bookName" />
				 	[<a href="https://s.taobao.com/search?q=<s:property value="isbn"/>" target="_blank">淘</a>]
				 	[<a href="http://search.dangdang.com/?key=<s:property value="isbn"/>" target="_blank">当</a>]
				</td>
				<td><s:property value="bookAuthor" />
				</td>
				<td><s:property value="bookEdition" />
				<s:if test="#purchasedPlan.isNewEdition == true">
					<div style="color:green;font-weight:bold">[最新]</div>
				</s:if>
				</td>
				<td><s:property value="bookPublisher" />
				</td>
				<td><s:date name="publishDate" format="yyyy/MM" /></td>
				<td><s:property value="bookPrice" />
				<td>
					<a href="#" onclick="return editPurchasePlanManualDecision(${bookId});">
						<s:if test="manualDecisionFlag == true">
							<s:property value="manualDecision" />[人]
						</s:if>
						<s:else>
							<s:property value="requireAmount" />
						</s:else>
					</a>
				</td>
				<td>
					<a href="#" onclick="return editPurchasePlanStock(${bookId});">
						<s:property value="stockAmount"/>
					</a>
				</td>
				<td><s:if test="purchasedAmount > 0">
						<a href="purchase/queryPurchaseDetail?bookId=${bookId}&status=purchased"><s:property value="purchasedAmount" /></a>
					</s:if>
					<s:else>
					<s:property value="purchasedAmount" />
					</s:else>
				</td>
				<td><s:property value="stillRequireAmount" />
				</td>
				<td><a href="javascript:showOperate(${bookId})">操作</a></td>
			</tr>
			<tr id="hidden_tr_${bookId}" style="display:none">
				<td colspan="12" align="right">
					<a href="#" onclick="return addToBlackList(${bookId});">设为黑名单</a>&emsp;
					<a href="#" onclick="return addToWhiteList(${bookId});">设为白名单</a>&emsp;
					<a href="#" onclick="return removePurchasePlanFlag(${bookId});">取消黑白名单标记</a>&emsp;
					<a href="#" onclick="return removeManualDecision(${bookId});">删除人工定量</a>&emsp;
					<a href="#" onclick="return recalculatePurchasePlan(${bookId});">重新计算</a>&emsp;
					<p/>
				</td>
			</tr>
			
		</s:iterator>
	</table>
	<div id="float-to-be-show">
		<h2>处理中，请稍后...</h2>
	</div>
	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
