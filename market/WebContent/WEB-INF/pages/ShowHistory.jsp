<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />
<title>History</title>
</head>
<body>
<table>
	<tr>
		<td>
		<h1 style="padding-right: 200px;">History Of Bid</h1>
		</td>
		<td>
		<h4 style="font-size: 15px;">You logged as:</h4>
		</td>
		<c:choose>
		<c:when test="${user.login == null}">
		<td>
		<h5>Guest</h5>
		</td>
		<td>
		<h4><a href="Login.htm">LogIn</a></h4>
		</td>
		</c:when>
		<c:otherwise>
		<td>
		<h5>${user.userFullName}</h5>
		</td>
		<td>
		<h4><a href="Logout.htm">Logout</a></h4>
		</td>
		</c:otherwise>
		</c:choose>
	</tr>
	<tr>
	<td>
	<table>
		<tr>
			<td style="text-align: left; padding-right: 20px;">
			<h4><a href="ShowItems.htm"> Show all
			items </a></h4>
			</td>
			<td style="text-align: left;">
			<h4><a href="ShowBlackList.htm"> Show black list </a></h4>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	<tr>
		<td colspan="3">
		<%--A part for show history--%>
		<table class="inner" border="2" frame="void">
			<caption class="inner">Items</caption>
			<thead>
				<tr>
					<td id="itemId">Item ID</td>
					<td id="title">Title</td>
					<td id="bidder">Bidder</td>
					<th id="bid">Bid Count</th>
					<th colspan="2">Set/remove Block</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${history}" var="hist">
					<tr>
						<%-- Here insert link to history of bid
						     for item --%>
						<td>${hist.itemId}</td>
						<td>${hist.title}</td>
						<td>${hist.bidder}</td>
						<td>${hist.bidCount}</td>
						<%--Try to know about blocked param of user --%>
						<c:set var="contains" value="false" />
						<c:forEach items="${blackList}" var="block">
						<c:if test="${block.blockedUserId == 
													hist.bidderId}">
							<c:set var="contains" value="true" />
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${(contains == true) && 
											(hist.sellerId == 
												user.userId)}">
								<td>
								<form 
								action="ShowHistory.htm?method=removeBlock"
									 method="post">
								<input
									type="hidden" name="userId" 
									value="${hist.bidderId}" />
								<input type="hidden"
								name="itemId" value="${hist.itemId}" /> 
								    <input class="bid"
									type="submit" value="Remove" />
							   </form>
								</td>
							</c:when>
							<c:when test="${(hist.bidderId !=
								               user.userId) &&
								              (hist.sellerId == 
												user.userId)}">
								<td>
								<form 
								action="ShowHistory.htm?method=setBlock"
								method="post">
								<input type="hidden"
								name="userId" value="${hist.bidderId}" />
								<input type="hidden"
								name="itemId" value="${hist.itemId}" /> 
								 <input class="buy"	type="submit"
								 style="width: 100px;" value="Block" />
								 </form>								 
								</td>
								</c:when>
								<c:otherwise>
								<td>
									you can't 
									block this user
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
		<th>
		<c:if test="${blockError!=null}">
		<div style="color:red;">
			${blockError}
		</div>
	</c:if>
		</th>
	</tr>
</table>

</body>
</html>