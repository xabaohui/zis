<%@page import="com.zis.bookinfo.bean.Bookinfo"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
	<h2 align="center">
		<font>
		<s:if test="#pageType=='pageGroup'">不同版次的图书</s:if>
		<s:if test="#pageType=='pageRelate'">相关图书</s:if>
		
		</font>
	</h2>
		<s:form action="removeAll" method="post" id="from_checkBox" >
		<s:hidden name="pageType" />
		
			<table id="common-table">
				<tr>
					<th>ID</th>
					<th>ISBN</th>
					<th>书名</th>
					<th width='105'>版次</th>
					<th>作者</th>
					<th>出版社</th>
					<th>出版日期</th>
					<th>价格</th>
					<th>状态</th>
					<th height="30" width="100">操作</th>
				</tr>

				<s:iterator value="relateList" var="book"> 
					<tr>
						<td><s:property value="id"/></td>
						<td>
							<a href="bookinfo/getAllBooks?bookISBN=<s:property value="isbn"/>"><s:property value="isbn"/></a>
							<s:if test="#book.repeatIsbn == true"><br/><font style="font-weight:bold;color:red">[一码多书]</font></s:if>
						</td>
						<td>
							<s:property value="bookName"/>&nbsp;
							[<a href="http://www.youlu.net/<s:property value="outId"/>" target="_blank">有</a>]
							[<a href="https://s.taobao.com/search?q=<s:property value="isbn"/>" target="_blank">淘</a>]
						</td>
						<td>
							<s:property value="bookEdition"/>
							<s:if test="isNewEdition == true"><font style="font-weight:bold;color:green">[最新]</font></s:if>
							<s:if test="groupId!=null">
						    <a href="bookinfo/showGroupList?groupId=<s:property value="groupId"/>"  target="_blank"><br/>其他版本</a>
							</s:if>
						</td>
						<td><a href="bookinfo/getAllBooks?bookAuthor=<s:property value="bookAuthor"/>&bookPublisher=<s:property value="bookPublisher"/>&pageSource=pagination"><s:property value="bookAuthor"/></a></td>
						<td><s:property value="bookPublisher"/></td>
						<td>
							<s:date name="publishDate" format="yyyy年MM月" />
						</td>
						<td><s:property value="bookPrice"/></td>
						<td><s:property value="bookStatus"/></td>
						<td><a href="bookinfo/removeRelateId?id=<s:property value="id"/>&pageType=<s:property value="pageType"/>">解除关联</a></td>
					</tr>
			</s:iterator>
			</table>
		 　　<input type="submit"  value="解除所有关联">

		</s:form>

<%@ include file="/footer.jsp"%>
