<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
			<td>${order.orderId}</td>
				<td>
					<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${order.shopName}
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
				<td>
					<c:set value = "${order.orderDetails}" var = "details"/>
						<c:forEach items="${details}" var="detail" >
							${detail.bookName}&nbsp;*&nbsp;${detail.itemCount}<br/>
						</c:forEach>
				</td>
