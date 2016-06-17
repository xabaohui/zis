<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<h1>
	书单明细 - 图书未匹配
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>图书</th>
		<th>疑似匹配记录</th>
	</tr>
	<s:iterator value="resultList" var="task">
		<tr>
			<td>
				ISBN：<s:property value="isbn"/><br/>
				书名：<s:property value="bookName"/><br/>
				作者：<s:property value="bookAuthor"/>&nbsp;版次：<s:property value="bookEdition"/><br/>
				出版社：<s:property value="bookPublisher"/>
			</td>
			<td>
				ISBN：<input type="text" name="isbn" size="10"/>
				书名：<input type="text" name="bookName" size="30"/>
				作者：<input type="text" name="bookAuthor" size="7"/>
				<input type="button" value="查找"/>
			</td>
		</tr>
	</s:iterator>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>