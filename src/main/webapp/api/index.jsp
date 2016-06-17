<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>在见书城API介绍</p>

<p>图书信息查询接口</p>
/api/queryBookInfo.action?isbn=XXX

<p>新增图书信息接口</p>
/api/addBookInfo.action?bookName=XXX&bookAuthor=XXX&isbn=XXX&edition=XXX&publisher=XXX&publishDate=XXX&price=XXX&<font color="red">token=XXX</font>

<p>教材使用量保存接口</p>
/api/addBookRequirement.action?departId=XXX&grade=XXX&term=XXX&amount=XXX&operator=XXX&bookIds=XXX,YYY,ZZZ&<font color="red">token=XXX</font>

<p>学校查询接口</p>
/api/queryAllCollege.action

<p>学院查询接口</p>
/api/queryInstitute.action?id=XXX

<p>专业查询接口</p>
/api/queryDepartment.action?id=XXX

<p>采购计划查询接口</p>
/api/queryPurchaseAmount.action?isbn=XXX

<p>采购数量保存接口</p>
/api/addPurchasedAmount.action?bookId=XXX&purchasedAmount=X&operator=XXX&position=XXXX&memo=XXXXXX&<font color="red">token=XXX</font>

<p>获取token接口</p>
/api/getToken
 <%@ include file="/footer.jsp"%>
