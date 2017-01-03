<%@page import="com.zis.bookinfo.action.BookBatchOperateType"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/addAmountBiz.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>

<form action="bookInfo/batchOperateBooks" method="post" id="form_checkBox">
	<input type="hidden" name="operateType" id="operateType"/>
	<div align="left">
		<shiro:hasPermission name="bookInfo:saveOrUpdate">
			<input type="button" value="设置成不同版本" onclick="batchOperate('<%=BookBatchOperateType.SET_TO_GROUP %>')" />
			<input type="button" value="设置成关联图书" onclick="batchOperate('<%=BookBatchOperateType.SET_TO_RELATED %>')" />
		</shiro:hasPermission>
		<shiro:hasPermission name="bookInfo:delete">
			<input type="button" value="批量删除" onclick="batchOperate('<%=BookBatchOperateType.BATCH_DELETE %>')" />
		</shiro:hasPermission>
		<shiro:hasPermission name="purchase:management">
			<input type="button" value="批量拉黑" onclick="batchOperate('<%=BookBatchOperateType.BATCH_ADD_TO_BLACK_LIST %>')" />
		</shiro:hasPermission>
	<p/>
	</div>
	<table id="common-table">
		<tr>
			<th><input type="checkbox" name="allCheck" value="checkAll1234" onclick="checkAll()" /></th>
			<th>ID</th>
			<th>ISBN</th>
			<th>书名</th>
			<th width='105'>版次</th>
			<th>作者</th>
			<th>出版社</th>
			<th>出版日期</th>
			<th>价格</th>
			<th>相关性</th>
			<th>状态</th>
			<th height="30" width="100" colspan="3">操作</th>
		</tr>
		
		<c:forEach items="${list}" var="book">
			<tr>
				<td><input type="checkbox" value="${book.id}"
					name="batchSelectedItem" />
				</td>
				<td>${book.id}
				</td>
				<td>
					<a href="bookInfo/getAllBooks?bookISBN=${book.isbn}">${book.isbn}</a>
					<c:if test="${book.repeatIsbn} == true">
						<br /><font style="font-weight:bold;color:red">[一码多书]</font>
					</c:if>
				</td>
				<td>${book.bookName}&nbsp;
					[<a href="http://www.youlu.net/${book.outId}" target="_blank">有</a>]
					[<a href="https://s.taobao.com/search?q=${book.isbn}" target="_blank">淘</a>]
					[<a href="http://search.dangdang.com/?key=${book.isbn}" target="_blank">当</a>]
				</td>
				<td>${book.bookEdition}
					<c:if test="${book.isNewEdition eq true}"><font style="font-weight:bold;color:green">[最新]</font></c:if>
					<c:if test="${not empty book.groupId}">
						<br/>
							[<a href="bookInfo/showGroupList?groupId=${book.groupId}" target="_blank">其他版本</a>]
					</c:if>
				</td>
				<td><a href="bookInfo/getAllBooks?bookAuthor=${book.bookAuthor}&bookPublisher=${book.bookPublisher}">${book.bookAuthor}</a></td>
				<td>${book.bookPublisher}</td>
				<td><fmt:formatDate value="${book.publishDate}" pattern="yyyy年MM月"/></td>
				<td>${book.bookPrice}</td>
				<td>
					<c:if test="${not empty book.relateId}">
							<a href="bookInfo/showGroupList?relateId=${book.relateId}" target="_blank">关联图书</a>
					</c:if>
				</td>
				<td>${book.bookStatus}</td>
				<shiro:hasPermission name="bookInfo:saveOrUpdate">
					<td><a href="bookInfo/findBookById?bookId=${book.id}" target="_blank">修改</a></td>
					<td><a href="#" onclick="return immigrate(${book.id})">迁移</a></td>
				</shiro:hasPermission>
				<shiro:lacksPermission name="bookInfo:saveOrUpdate">
					<td></td>
				</shiro:lacksPermission>
			</tr>
		</c:forEach>
	</table>
	
	<div id="float-to-be-show">
		<h2>处理中，请稍后...</h2>
	</div>

</form>