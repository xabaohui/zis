<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src='resources/regist.js'></script>
<script type="text/javascript">
</script>
<style type="text/css">
</style>
<div align="center">
	<c:if test="${company.typeStatus eq 'updateCompany'}"> <h1>公司修改</h1> </c:if>
	<c:if test="${company.typeStatus eq 'addCompany'}"><h1>新增公司</h1></c:if>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="shop/saveOrUpdateCompany" method="post" modelAttribute="dto" id = "registFrom">
	<div align="center">
		<table>
			<tr>
				<td>公司名称:</td>
				<td>
					<input type="text" name="companyName" value="${company.companyName}" style="display: block;" 
					<c:if test="${company.typeStatus eq 'updateCompany'}"> readonly="readonly" </c:if>
					/>
				</td>
				<td><spring:errors delimiter="," path="companyName" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>联系人:</td>
				<td>
					<input type="text" name="contacts" value="${company.contacts}" style="display: block;" 
					/>
				</td>
				<td><spring:errors delimiter="," path="contacts" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>联系人手机:</td>
				<td>
					<input type="text" name="mobile" value="${company.mobile}" style="display: block;" 
					/>
				</td>
				<td><spring:errors delimiter="," path="mobile" cssStyle="color:red"/></td>
			</tr>
			<tr>
				<td>公司地址:</td>
				<td><textarea name="address" cols="22" rows="3">${company.address}</textarea></td>
				<td>
					<spring:errors delimiter="," path="address" cssStyle="color:red"/>
				</td>
			</tr>
		</table>
	</div>
	<input type="hidden" name = "companyId" value ="${company.companyId}"/>
	<input type="hidden" name = "typeStatus" value ="${company.typeStatus}"/>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</spring:form>

<%@ include file="/footer.jsp"%>