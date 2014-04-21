<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
</head>
<body>
<table>
<tr>
	<td colspan="3"><!--A part for show items-->
		<c:choose>
		<c:when test="${sc == 'ASC'}">
		<c:set var="scl" value="DESC"></c:set>
		</c:when>
		<c:otherwise>
		<c:set var="scl" value="ASC"></c:set>
		</c:otherwise>
		</c:choose>
			<c:set var="ref" 
			 value= "ShowMyItems.htm?"/>

		<table class="inner" border="2" frame="void">
			<caption class="inner">My Items</caption>
			<thead>
				<tr>
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
					<th colspan="2">Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${beanItems}" var="item">
					<tr>
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
							<c:when test="${status == 'edit'}">
							<td>
							<form  action="EditItem.htm" 
										method="get">
							<input type="hidden" name="itemId" 
											  value="${item.itemId}" /> 
							<input class="edit" type="submit" 
									style="width: 50px" value="edit" />
							</form>
							</td>
							<td>
							<form:form 
							 action="DeleteItem.htm" commandName="item" 
							 method="POST">
							<input type="hidden" 
									name="itemId" value="${item.itemId}" /> 
							 <input	class="edit" type="submit" 
								style="width: 50px" value="delete" />
							</form:form>
						</td>
						</c:when>
						<c:otherwise>
							<td colspan="2">
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