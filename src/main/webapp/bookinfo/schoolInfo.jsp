<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/header.jsp"%>
<center>
	<h1>院校信息</h1>
	<s:form action="findSchoolInfo" method="post">
		<s:textfield name="school" label="学校" />
		<s:textfield name="institute" label="院系" />
		<s:textfield name="partName" label="专业" />
		<s:submit value="查询" />
		<s:reset value="重置" />
	</s:form>
	<table id="common-table">
		<tr>
			<th>学校</th>
			<th>学院</th>
			<th>专业</th>
			<th>学年制</th>
			<th>操作</th>
		</tr>
		<s:iterator value="#request.AllSchoolInfo" status="st">
			<tr>
				<s:iterator value="#request.AllSchoolInfo.get(#st.index)" var="as">
					<td><s:property value="#as.college" /></td>
					<td><s:property value="#as.institute" /></td>
					<td><s:property value="#as.partName" /></td>
					<td><s:property value="#as.years" /></td>
					<td><a
						href="requirement/updateSchoolPre?id=<s:property value='#as.did'/>">修改</a>
					</td>
					<td><a
						href="requirement/addAmountPreAction?id=<s:property value='#as.did'/>">录入教材</a>
					</td>
				</s:iterator>
			</tr>
		</s:iterator>
	</table>

	<!-- 分页查询start -->
	<s:if test="#prePage != null">
		<a href="requirement/findSchoolInfo?pageSource=pagination&pageNow=<s:property value="#prePage"/>&school=<s:property value="#school"/>&institute=<s:property value="#institute"/>&partName=<s:property value="#partName"/>">上一页</a>&nbsp;
		</s:if>
	<s:property value="pageNow" />
	&nbsp;
	<s:if test="#nextPage != null">
		<a
			href="requirement/findSchoolInfo?pageSource=pagination&pageNow=<s:property value="#nextPage"/>&school=<s:property value="#school"/>&institute=<s:property value="#institute"/>&partName=<s:property value="#partName"/>">下一页</a>&nbsp;
		</s:if>
	<!-- 分页查询end -->
</center>
<%@ include file="/footer.jsp"%>
