<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />
<title>BlackList</title>
</head>
<body>
<table id="glob" style="table-layout: 1px">
	<tr>
		<td>
		<h1 style="padding-right: 200px;">Your black List</h1>
		</td>
		<td>
		<h4 style="font-size: 15px;">You logged as:</h4>
		</td>
		<c:choose>
		<c:when test="${user.login == 'null'}">
		<td>
		<h5>Guest</h5>
		</td>
		<td>
		<h4><a href="entryAction.perform">LogIn</a></h4>
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
			<tr style="text-align: left;">
				<td>
				<h4 style="padding-right: 40px;"><a
					href="ShowItems.htm"> 
										Show all items</a></h4>
				</td>
				<td>
				<h4><a href="ShowMyItems.htm"> 
										Show my items</a></h4>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<%-- black list body --%>
	<tr>
		<td>
		<table class="inner" border="2" frame="void">
			<caption class="inner">Blocked Users</caption>
			<thead>
				<tr>
					<td id="userId">User ID</td>
					<td id="username">UserName</td>
					<td id="removeBlock">Remove Block</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${blackList}" var="block">
					<tr>
						<td>${block.blockedUserId}</td>
						<td>${block.blockedUserName}</td>
						<td>
						<form action="ShowBlackList.htm?method=removeBlock"
								method="post">
						<input type="hidden"
							   name="userId" 
							   value="${block.blockedUserId}" /> 
							<input class="buy"
							type="submit" style="width: 100px;" 
							value="Remove" />
					   </form>
					   </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<c:if test="${removeBlockError != null}">
			${removeBlockError}
		</c:if>
		</td>
	</tr>
</table>

</body>
</html>