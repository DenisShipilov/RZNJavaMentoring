<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>
									/script/searchValidation.js"></script>
<title>Advanced Search</title>
</head>
<body>
<!-- Set bid error message variable -->
<table class="glob" border="0">
<tr>
	<td>
		<h1>Advanced Search</h1>
	</td>
</tr>
<tr>
	<td>
		<h4 style="text-align: left"><a href="ShowItems.htm">Back</a></h4></td>
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
	</tr>
	<tr>
		<td><form:form method="POST" commandName="condition"
			onsubmit="return checkItem(this)">
			<table>
				<tr>
					<td><label for="itemId">Item UID:</label></td>
					<td><form:input path="itemId" id="itemId" size="40" /></td>
				</tr>
				<tr>
					<td><label for="title">Title:</label></td>
					<td><form:input path="title" id="title" size="40" /></td>
				</tr>
				<tr>
					<td style="vertical-align: top;">
					<label for="description">Description:</label>
					</td>
					<td><form:textarea rows="6" cols="45" id="description"
						path="description" /></td>
				</tr>
				<tr>
					<td><label for="minPrice">Min Price:</label></td>
					<td><form:input id="minPrice" path="minPrice" size="5" /></td>
					<td><label for="maxPrice">Max Price:</label></td>
					<td><form:input id="maxPrice" path="maxPrice" size="5" /></td>
				</tr>
				<tr>
					<td><label for="buyNow">Search only buy it now Items:</label></td>
					<td><form:checkbox id="buyNow" path="buyItNow" /></td>
					<td><form:errors path="buyItNow" /></td>
				</tr>
				<tr>
					<td><label for="startDateStr">Start Date:</label></td>
					<td><form:input id="startDateStr" path="startDateStr" size="40" /></td>
					<td><form:errors path="startDateStr" /></td>
				</tr>
				<tr>
					<td><label for="expireDateStr">Expire Date:</label></td>
					<td><form:input id="expireDateStr" path="expireDateStr" size="40" /></td>
					<td><form:errors path="expireDateStr" /></td>
				</tr>
				<tr>
					<td>
						<label style="color: red; text-align: right;">
							Format Date: MM/DD/YYYY HH:MM
						</label>
					</td>
				</tr>
				<tr>
					<td><label for="bidderCount">Bidder count:</label></td>
					<td><form:input id="bidderCount" path="bidderCount" size="40" /></td>
					<td><form:errors path="bidderCount" /></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td><input type="submit" value="Search" /> 
					<input type="reset" value="Clear" />
				</td>
				</tr>
			</table>
		</form:form></td>
	</tr>
</table>
</body>
</html>