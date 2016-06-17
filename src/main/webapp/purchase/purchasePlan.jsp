<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/purchaseService.js'></script>
<script type='text/javascript' src='resources/purchase.js'></script>

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
						<br /><font style="font-weight:bold;color:red">[一码多书]</font>
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
					<div style="color:green;font-weight:bold">[最新版]</div>
				</s:if>
				</td>
				<td><s:property value="bookPublisher" />
				</td>
				<td><s:date name="publishDate" format="yyyy年MM月" /></td>
				<td><s:property value="bookPrice" />
				<td>
					<a href="#" onclick="return editPurchasePlanManualDecision(${bookId});">
						<div id="bookItemRequireAmount_${bookId}">
						<s:if test="manualDecisionFlag == true">
							<s:property value="manualDecision" />[人]
						</s:if>
						<s:else>
							<s:property value="requireAmount" />
						</s:else>
						</div>
					</a>
				</td>
				<td>
					<a href="#" onclick="return editPurchasePlanStock(${bookId});">
						<div id="bookItemStockAmount_${bookId}">
						<s:property value="stockAmount"/>
						</div>
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
				<td><a href="purchase/addBlackList?bookId=${bookId }" target="_blank">拉黑</a></td>
			</tr>
		</s:iterator>
	</table>
	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
