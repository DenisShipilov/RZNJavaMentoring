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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>
									/script/itemValidation.js"></script>
<title>Edit Item</title>
</head>
<body>
<table>
</table>
<h1>Edit or register Item</h1>
<table class="glob" border="0">
<tr>
	  <td>
	  	<a href="ShowItems.htm">Back</a>
	  </td>
	  <td>
		<h4 style="font-size: 15px;">You logged as:</h4>
	</td>
	<td>
		<h5>${user.userFullName}</h5>
	</td>
	</tr>
	<tr>
		<td><form:form method="POST" commandName="item"
			onsubmit="return checkItem(this)">
			<table>
				<tr>
					<td><label for="title">Title:</label></td>
					<td><form:input path="title" id="title" size="40" /></td>
				</tr>
				<tr>
					<td style="vertical-align: top;"><label for="description">Description:</label>
					</td>
					<td><form:textarea rows="6" cols="45" id="description"
						path="description" /></td>
				</tr>
				<tr>
					<td><label for="title">Category:</label></td>
					<td><form:select size="1" path="category">
						<form:options items="${categores}" itemValue="categoryId"
							itemLabel="category" />
					</form:select></td>
				</tr>
				<tr>
					<td><label for="price">Start Price:</label></td>
					<td><form:input id="price" path="startPrice" size="40" /></td>
				</tr>
				<tr>
					<td><label for="time">Time left:</label></td>
					<td><form:input id="time" path="timeLeftStr" size="40" /></td>
					<td><form:errors path="timeLeft" /></td>
				</tr>
				<tr>
					<td><label for="bidInc">Bid Inc:</label></td>
					<td><form:input id="bidInc" path="bidIncrement" size="40" /></td>
				</tr>
				<tr>
					<td><label for="buyNow">Buy It Now:</label></td>
					<td><form:checkbox id="buyNow" path="buyItNow" /></td>
					<td><form:errors path="buyItNow" /></td>
				</tr>
				<tr>
					<td colspan="1"></td>
					<td><input type="submit" value="Publish/Save" /> <input
						type="reset" value="Reset" />
				</td>
				</tr>
				<tr>
					<td>
					<div style="color: red;"><form:errors path="*"
						cssClass="errorBox" /></div>
					</td>
				</tr>
			</table>
		</form:form></td>
	</tr>
</table>
</body>
</html>