			<tr>
				<th>
					<input type="checkbox" id = "checkAll" onclick = "checkAllOId()"/>全选
				</th>
				<th>订单Id</th>
				<th>创建时间</th>
				<th>网店订单号</th>
				<th>收件人</th>
				<th>商品清单</th>
				<th>状态</th>
				<th>物流信息</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order">
				<tr>
				<td><input name = "orderId" type="checkbox" value="${order.orderId}"/></td>
				<td>${order.orderId}</td>
				<td>
					<fmt:formatDate value="${order.gmtCreate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:set value = "${order.outOrderNumbers}" var = "outNumbers"/>
						<c:forEach items="${outNumbers}" var="outNumber" >
							${outNumber}<br/>
						</c:forEach>
				</td>
				<td>
					<div id = "address_${order.orderId}">
						${order.receiverName}
					</div>
				</td>
				<td  width="35%">
					<c:set value = "${order.orderDetails}" var = "details"/>
						<c:forEach items="${details}" var="detail" >
							${detail.bookName}&nbsp;*&nbsp;${detail.itemCount}<br/>
						</c:forEach>
				</td>
				<td>
					${order.uniqueStatusDisplay}
					<div id = "blockFlag_${order.orderId}">
						<c:if test="${order.blockFlag}">
							<span title="${order.blockReason}"><font color="red">已拦截</font></span>
						</c:if>
					</div>
				</td>
				<td>
					${order.expressCompany}
					<br/>
					${order.expressNumber}
				</td>