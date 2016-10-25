<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ include file="/header.jsp"%>
<center>
	<script type="text/javascript">
		window.onload = function() {
			var year = document.getElementById("yearsHidden").value;
			if (year == 3) {
				document.getElementById("addSchoolAction_years3").checked = true;
			} else if (year == 4) {
				document.getElementById("addSchoolAction_years4").checked = true;
			} else if (year == 5) {
				document.getElementById("addSchoolAction_years5").checked = true;
			}
		};
	</script>
	<h1>维护院校信息</h1>
	<spring:form action="requirement/addSchoolAction" method="post">
		<h2 style="color:green">${MSG}</h2>
		<input name="id" type="hidden" value="${id}">
		<table>
			<tr>
				<td>学校</td>
				<td><input name="college" type="text" value="${college}"></td>
				<td><spring:errors delimiter="," path="college" cssStyle="color:red" /></td>
			</tr>
			<tr>
				<td>院系</td>
				<td><input name="institute" type="text" value="${institute}"></td>
				<td><spring:errors delimiter="," path="institute" cssStyle="color:red" /></td>
			</tr>
			<tr>
				<td>专业</td>
				<td><input name="partName" type="text" value="${partName}"></td>
				<td><spring:errors delimiter="," path="partName" cssStyle="color:red" /></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="hidden" id="yearsHidden" value="${years}">
					<input name="years" id="addSchoolAction_years3" type="radio"value="3">专科三年
					<input name="years" id="addSchoolAction_years4" type="radio" value="4">本科四年
					<input name="years" id="addSchoolAction_years5" type="radio" value="5">医学五年
				</td>
				<td><spring:errors delimiter="," path="years" cssStyle="color:red" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input value="提交" type="submit"></td>
			</tr>
		</table>
	</spring:form>
</center>
<%@ include file="/footer.jsp"%>