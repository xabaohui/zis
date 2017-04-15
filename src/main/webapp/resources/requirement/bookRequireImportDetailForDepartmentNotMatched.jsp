<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<h1>
	书单明细 - 专业未匹配
</h1>
<p/>
<table id="common-table">
	<tr>
		<th>专业</th>
		<th>疑似匹配记录</th>
	</tr>
	<s:iterator value="resultList" var="task">
		<tr>
			<td>
				学校：<s:property value="college"/><br/>
				学院：<s:property value="institute"/><br/>
				班级编码：<s:property value="classNum"/><br/>
				学年：<s:property value="grade"/>&nbsp;学期：<s:property value="term"/>
			</td>
			<td>无匹配记录</td>
		</tr>
	</s:iterator>
</table>

<%@ include file="/common/pagination.jsp" %>

<%@ include file="/footer.jsp"%>