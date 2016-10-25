<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mvc" uri="http://www.springframework.org/tags" %>
<center>
    <h1>采购明细</h1>
    <form action="purchase/queryPurchaseDetail" method="post">
    <table>
    	<tr>
    		<td>ISBN</td>
    		<td><input type="text" name="isbn"></td>
    	</tr>
    	<tr>
    		<td>书名</td>
    		<td><input type="text" name="bookName"></td>
    	</tr>
    	<tr>
    		<td>采购人</td>
    		<td><input type="text" name="operator"></td>
    	</tr>
    	<tr>
    		<td>状态</td>
    		<td>
    			<select name="status">
    				<option value="purchased">已采购，未入库</option>
    				<option value="checked">已入库</option>
    			</select>
    		</td>
    	</tr>
    	<tr align="center">
    		<td></td>
    		<td colspan="2" ><input type="submit" value="查询"></td>
    	</tr>
    </table>
    </form>
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
    	<c:forEach items="${resultList}" var="detail">
    		<tr>
    			<td>
    				${detail.gmtCreate}
    			</td>
    			<td>${detail.bookName}</td>
    			<td>${detail.isbn}</td>
    			<td>${detail.bookAuthor}</td>
    			<td>
	    			${detail.bookEdition}
	    			<c:if test="${detail.isNewEdition eq true}">
						<div style="color:green;font-weight:bold">[最新版]</div>
					</c:if>
    			</td>
    			<td>${detail.publishDate}</td>
    			<td>${detail.operator}</td>
    			<td>${detail.purchasedAmount} (${detail.inwarehouseAmount})</td>
    			<td>${detail.position}</td>
    			<td>${detail.memo}</td>
    			<td>${detail.statusDisplay}</td>
    		</tr>
    	</c:forEach>
    </table>
    
    	<!-- 分页查询start -->
		<%@ include file="/common/pagination.jsp"%>
		<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
