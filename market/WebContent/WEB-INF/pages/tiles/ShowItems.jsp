<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<table>
<tr>
		<td colspan="3">
		<c:choose>
		<c:when test="${sc == 'ASC'}">
		<c:set var="scl" value="DESC"></c:set>
		</c:when>
		<c:otherwise>
		<c:set var="scl" value="ASC"></c:set>
		</c:otherwise>
		</c:choose>
		<c:if test="${search != true}">
			<c:set var="ref" 
			 value= "ShowItems.htm?"/>
			</c:if>
			<c:if test="${search == true}">
			<c:set var="ref" 
			 value= "SerchItem.htm?"/>
			</c:if>
		<table class="inner" border="2" frame="void">
			<caption class="inner">Items</caption>
			<thead>
				<tr>
					<td id="uid">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=itemId&sc=${scl}"> 
						UID</a>
					</td>
					<td id="title">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=title&sc=${scl}">
					Title</a></td>
					<th id="description">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=description&sc=${scl}">
					Description</a></th>
					<td id="seller">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=seller&sc=${scl}">
					Seller</a></td>
					<td id="price">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=startPrice&sc=${scl}">
					Start Price</a></td>
					<td id="bidInc">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=bidInc&sc=${scl}">
					Bid Inc</a></td>
					<td id="bestOffer">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=bestOffer&sc=${scl}">
					Best Offer</a></td>
					<td id="bidder">
					<a href="${ref}keyword=${keyword}&
					condition=${condition}&pageNumber=${pageNumber}
					&sortCol=bidder&sc=${scl}">
					Bidder</a></td>
					<th id="stopDate">Stop Date</th>
					<th colspan="2">Bidding</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${beanItems}" var="item">
					<tr>
						<%-- Here insert link to history of bid
						     for item --%>
						<td>
						<a class="item" 
						href="ShowHistory.
						htm?itemId=${item.itemId}">
						${item.itemId} <span class="custom help"> <img
						src="<%=request.getContextPath()%>/img/Help.png" 
						alt="Help" height="48"
						 width="48" /> <em>History</em>
						Click on the item number to 
						see the history of  bids 
						for  product </span></a>
						</td>
						<td>${item.title}</td>
						<td>${item.description}</td>
						<td>${item.seller}</td>
						<td>${item.startPrice}</td>
						<td>${item.bidInc}</td>
						<td>${item.bestOffer}</td>
						<td>${item.bidder}</td>
						<td>${item.stopDate}</td>
						<%--set map --%>
						<c:set var="map" scope="session" 
						value="${statusMap}"/>
						<c:set var="status" value="${map[item.itemId]}" />
						<c:choose>
							<c:when test="${status == 'bid'}">
								<td>
								<form action="ShowItems.htm?method=bid"
									onsubmit="return sendBid(this)"
									 method="post"><input
									type="hidden" name="userId" value="${user.userId}" /> <input
									type="hidden" name="itemId" value="${item.itemId}" /> 
									<input
									type="text" name="bidCount" size="6" /> 
									<input class="bid"
									type="submit" style="width: 50px" 
									value="Bid" />
								</form>
								</td>
							</c:when>
							<c:when test="${status == 'buy'}">
								<td>
							<form action="ShowItems.htm?method=buy"
													  method="post">
							<input type="hidden" name="userId" 
							value="${user.userId}" /> 
							<input type="hidden" name="itemId"
							 value="${item.itemId}" /> 
							 <input  type="hidden" name="price"
							 value="${item.startPrice}"/>
							 <input class="buy"
									type="submit" style="width: 100px;" value="Buy" /></form>
								</td>
							</c:when>
							<c:otherwise>
							<td>
							  <h4 style="text-align: center; 
							  			 font-family: sans-serif;">
								${status}
							  </h4>
							</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</td>
	</tr> 
</table>
</body>
</html>