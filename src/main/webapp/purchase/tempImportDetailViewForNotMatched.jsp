<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/purchaseService.js'></script>
<script type="text/javascript" src="resources/purchase.js"></script>

<center>
	<h1>导入记录明细 - 未关联</h1>
	<p/>
	<h3><a href="purchase/viewTempImportTask">上一步</a>&nbsp;
	|&nbsp;<a href="purchase/viewTempImportDetailForMatched?status=matched&taskId=<s:property value="#task.id"/>">下一步</a></h3>
	<p/>
	<div align="left">
	&nbsp;&nbsp;
	业务类型：<s:property value="#task.bizTypeDisplay"/>&nbsp;&nbsp;
	备注：<s:property value="#task.memo"/>&nbsp;&nbsp;
	当前状态：<s:property value="#task.statusDisplay"/>&nbsp;&nbsp;
	记录总数：<s:property value="#task.totalCount"/>
	<p/>
	</div>
	<table id="common-table">
		<tr>
		<th>&nbsp;</th>
		<th>导入记录</th>
		<th>疑似匹配记录</th>
		</tr>
	<s:iterator value="resultList" var="detail" status="index">
		<tr>
			<td><s:property value="#index.count"/></td>
			<td>
				<a href="bookinfo/getAllBooks?bookISBN=<s:property value="isbn"/>" target="_blank"><s:property value="isbn"/></a>&nbsp;/&nbsp;<s:property value="amount"/>本&nbsp;/&nbsp;<s:property value="additionalInfo"/>
				[<a href="https://s.taobao.com/search?q=<s:property value="isbn"/>" target="_blank">淘</a>]
				[<a href="http://search.dangdang.com/?key=<s:property value="isbn"/>" target="_blank">当</a>]
			</td>
			<td>
				<ol>
				<s:if test="#detail.relatedBooks == null">无匹配图书</s:if>
				<s:iterator value="relatedBooks" var="book">
					<li>
					<div id="book_<s:property value="#detail.id"/>_<s:property value="id"/>">
						<s:property value="bookName"/>&nbsp;/&nbsp;<s:property value="bookEdition"/>&nbsp;/&nbsp;<s:property value="bookAuthor"/>&nbsp;/&nbsp;<s:property value="bookPublisher"/>
						<input type="button" value="选择" onclick="selectBookForMultipleIsbn(<s:property value="#detail.id"/>, <s:property value="id"/>)"/>
					</div>
						<p/>
					</li>
				</s:iterator>
				</ol>
			</td>
		</tr>
	</s:iterator>
</table>

	<!-- 分页查询start -->
	<%@ include file="/common/pagination.jsp"%>
	<!-- 分页查询end -->

</center>
<%@ include file="/footer.jsp"%>