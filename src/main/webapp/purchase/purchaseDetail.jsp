<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
    <h1>采购明细</h1>
    <s:form action="queryPurchaseDetail" method="post">
    	<s:textfield name="isbn" label="ISBN"/>
    	<s:textfield name="bookName" label="书名"/>
    	<s:textfield name="operator" label="采购人"/>
    	<s:select name="status" list="#{'purchased':'已采购，未入库', 'checked':'已入库'}" label="状态"/>
    	<s:submit value="查询"/>
    </s:form>
    <table id="common-table">
    	<tr>
    	<th>日期</th>
    	<th>书名</th>
    	<th>ISBN</th>
    	<th>作者</th>
    	<th>版次</th>
    	<th>出版日期</th>
    	<th>采购人</th>
    	<th>采购量</th>
    	<th>位置</th>
    	<th>备注</th>
    	<th>状态</th>
    	</tr>
    	<s:iterator var="detail" value="resultList">
    		<tr>
    			<td>
    				<s:date name="gmtCreate" format="yyyy年MM月dd日" />
    			</td>
    			<td><s:property value="bookName"/></td>
    			<td><s:property value="isbn"/></td>
    			<td><s:property value="bookAuthor"/></td>
    			<td>
	    			<s:property value="bookEdition"/>
	    			<s:if test="#detail.isNewEdition == true">
						<div style="color:green;font-weight:bold">[最新版]</div>
					</s:if>
    			</td>
    			<td><s:date name="publishDate" format="yyyy年MM月" /></td>
    			<td><s:property value="operator"/></td>
    			<td><s:property value="purchasedAmount"/> (<s:property value="inwarehouseAmount"/>)</td>
    			<td><s:property value="position"/></td>
    			<td><s:property value="memo"/></td>
    			<td><s:property value="statusDisplay"/></td>
    		</tr>
    	</s:iterator>
    </table>
    
    	<!-- 分页查询start -->
		<%@ include file="/common/pagination.jsp"%>
		<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
