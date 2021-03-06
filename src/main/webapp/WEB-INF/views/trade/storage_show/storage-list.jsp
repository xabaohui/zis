<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
				<td>${order.id}</td>
				<td>
					<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/>
					<fmt:formatDate value="${order.createTime}" pattern="HH:mm:ss"/>
				</td>
				<td>
					${order.shopName}
				</td>
				<td>
					<c:set value = "${order.outOrderNumbers}" var = "outNumbers"/>
						<c:forEach items="${outNumbers}" var="outNumber" >
							${fn:trim(outNumber)}<br/>
						</c:forEach>
				</td>
				<td>
					<div id = "address_${order.id}">
						${order.receiverName}
					</div>
				</td>
				<td>
					<c:set value = "${order.orderDetailVOs}" var = "details"/>
						<c:forEach items="${details}" var="detail" >
							${detail.bookName}/${detail.bookEdition}&nbsp;*&nbsp;${detail.itemCount}<br/>
						</c:forEach>
					<br/>
					<div id = "buyerMess_${order.id}">
						<c:if test="${order.payStatus eq 'refunding'}">
							<font color="red">${order.buyerMessage}</font>
						</c:if>
						<c:if test="${order.payStatus != 'refunding'}">
							<font color="green">${order.buyerMessage}</font>
						</c:if>
					</div>
				</td>
				<td>
					<c:set var="sum" value = "0"/>
					<c:forEach items="${details}" var="detail" >
						<c:set var="sum" value = "${sum + detail.itemCount}"/>
					</c:forEach>
					${sum}
				</td>