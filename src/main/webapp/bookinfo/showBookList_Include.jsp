<%@page import="com.zis.bookinfo.action.BookBatchOperateType"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script type='text/javascript' src='dwr/interface/addAmountBiz.js'></script>
<script type="text/javascript" src='resources/common.js'></script>
<script type="text/javascript" src='resources/bookinfo.js'></script>

<s:form action="batchOperateBooks" method="post" id="form_checkBox">
	<s:hidden name="operateType" id="operateType" />
	<div align="left">
		<input type="button" value="设置成不同版本" onclick="batchOperate('<%=BookBatchOperateType.SET_TO_GROUP %>')" />
		<input type="button" value="设置成关联图书" onclick="batchOperate('<%=BookBatchOperateType.SET_TO_RELATED %>')" />
		<input type="button" value="批量删除" onclick="batchOperate('<%=BookBatchOperateType.BATCH_DELETE %>')" />
		<input type="button" value="批量拉黑" onclick="batchOperate('<%=BookBatchOperateType.BATCH_ADD_TO_BLACK_LIST %>')" />
	<p/>
	</div>

	<table id="common-table">
		<tr>
			<th><input type="checkbox" name="allCheck" value="checkAll1234"
				onclick="checkAll()" /></th>
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

		<s:iterator value="#ALLBOOKS" var="book">
			<tr>
				<td><input type="checkbox" value="<s:property value="id"/>"
					name="batchSelectedItem" />
				</td>
				<td><s:property value="id" />
				</td>
				<td>
					<a href="bookinfo/getAllBooks?bookISBN=<s:property value="isbn"/>">
					<s:property value="isbn" />
					</a>
					<s:if test="#book.repeatIsbn == true">
						<br /><font style="font-weight:bold;color:red">[一码多书]</font>
					</s:if>
				</td>
				<td><s:property value="bookName" />&nbsp;
				 [<a href="http://www.youlu.net/<s:property value="outId"/>" target="_blank">有</a>]
				 [<a href="https://s.taobao.com/search?q=<s:property value="isbn"/>" target="_blank">淘</a>]
				 [<a href="http://search.dangdang.com/?key=<s:property value="isbn"/>" target="_blank">当</a>]
				</td>
				<td><s:property value="bookEdition" /> <s:if
						test="isNewEdition == true">
						<font style="font-weight:bold;color:green">[最新]</font>
					</s:if> <s:if test="groupId!=null">
						<br />
						    [<a
							href="bookinfo/showGroupList?groupId=<s:property value="groupId"/>"
							target="_blank">其他版本</a>]
							</s:if></td>
				<td><a
					href="bookinfo/getAllBooks?bookAuthor=<s:property value="bookAuthor"/>&bookPublisher=<s:property value="bookPublisher"/>&pageSource=pagination"><s:property
							value="bookAuthor" />
				</a>
				</td>
				<td><s:property value="bookPublisher" />
				</td>
				<td><s:date name="publishDate" format="yyyy年MM月" /></td>
				<td><s:property value="bookPrice" />
				</td>
				<td><s:if test="relateId !=null">
						<a
							href="bookinfo/showGroupList?relateId=<s:property value="relateId"/>"
							target="_blank">关联图书</a>
					</s:if>
				</td>
				<td><s:property value="bookStatus" />
				</td>
				<td><a
					href="bookinfo/findBookById?bookId=<s:property value="id"/>"
					target="_blank">修改</a></td>
					<td><a href="#"
					onclick="return immigrate(<s:property value="id"/>)">迁移</a></td>
			</tr>
		</s:iterator>
	</table>
	
	<div id="float-to-be-show">
		<h2>处理中，请稍后...</h2>
	</div>

</s:form>