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
	<c:if test="${shop.typeStatus eq 'updateShop'}"> <h1>修改店铺</h1> </c:if>
	<c:if test="${shop.typeStatus eq 'addShop'}"><h1>新增店铺</h1></c:if>
	<br/>
	<h2>
		<font color="red">${errorAction}</font>
	</h2>
</div>
<p />
<spring:form action="shop/saveOrUpdateShop" method="post" modelAttribute="dto">
	<div align="center">
		<table>
			<tr>
				<td>店铺名称:</td>
				<td>
					<input type="text" name="shopName" value="${shop.shopName}"  
					<c:if test="${shop.typeStatus eq 'updateShop'}"> readonly="readonly" </c:if>
					/>
				</td>
				<td>
					<spring:errors delimiter="," path="shopName" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>平台名称</td>
				<td>
					<input type="radio" name="pName" value="youzan" 
					<c:if test="${'youzan' eq shop.pName}"> checked="checked" </c:if>/>有赞
					<input type="radio" name="pName" value="taobao" 
					<c:if test="${'taobao' eq shop.pName}"> checked="checked" </c:if>/>淘宝
				</td>
				<td>
					<spring:errors delimiter="," path="pName" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>店铺商品折扣率:</td>
				<td>
					<input type="text" name="discount" value="${shop.discount}"/>
				</td>
				<td>
					<spring:errors delimiter="," path="discount" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>运费模板:</td>
				<td>
					<input type="text" name="deliveryTemplateId" value="${shop.deliveryTemplateId}" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				</td>
				<td>
					<spring:errors delimiter="," path="deliveryTemplateId" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>商品模板:</td>
				<td>
					<input type="text" name="templateId" value="${shop.templateId}" onkeyup="value=value.replace(/[^\d]/g,'')"/>
					<font color="green"><b>选填</b></font>
				</td>
				<td>
					<spring:errors delimiter="," path="templateId" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>邮箱:</td>
				<td>
					<input type="text" name="emails" value="${shop.emails}"/>
				</td>
				<td>
					<spring:errors delimiter="," path="emails" cssStyle="color:red"/>
				</td>
			</tr>
			<tr>
				<td>店铺网址:</td>
				<td>
					<input type="text" name="shopUrl" value="${shop.shopUrl}"/>
				</td>
			</tr>
			<tr>
				<td>有赞appId/集市宝Access key:</td>
				<td>
					<input type="text" name="appId" value="${shop.appId}"/>
				</td>
			</tr>
			<tr>
				<td>有赞appSecret/集市宝Secret Key:</td>
				<td>
					<input type="text" name="appSecret" value="${shop.appSecret}"/>
				</td>
			</tr>
			<tr>
				<td colspan="3"><font color="green"><b>店铺折扣率填写说明：
					<p/>如果给商品不打折请填写1为10折
					<p/>如果给商品打折请填写0.99-0.01之间的数字，就是9.9折到0.1折之间</b></font>
				</td>
			</tr>
		</table>
	</div>
	<input type="hidden" name = "typeStatus" value ="${shop.typeStatus}"/>
	<input type="hidden" name = "shopId" value ="${shop.shopId}"/>
	<div align="center">
		<input type="submit" value="提交" style="font-size: 20px;font-weight:bolder;">
	</div>
</spring:form>

<%@ include file="/footer.jsp"%>