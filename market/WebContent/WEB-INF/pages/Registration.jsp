<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"	
			href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />			
<script type="text/javascript" src="<%=request.getContextPath()%>
									/script/userValidation.js"></script>
<title>Registration</title>
</head>
<body>
<h1>Registration</h1>
<table class="glob" border="0">
	<tr>
		<td>
		<form:form id="registration" commandName="user"
				 action="Registration.htm" 
		         onsubmit="return checkRegistration(this)" method="POST">
		<table>
			<tr>
				<td><label for="fullName">Full Name:</label></td>
				<td><form:input id="fullName"  
								path="userFullName"  size="40" /></td>
			</tr>
			<tr>
				<td><label for="address">Bidding Address:</label></td>
				<td><form:input path="billingAddress"
									   id="address" size="40"/></td>
			</tr>
			<tr>
				<td><label for="email">E-mail Address:</label></td>
				<td><form:input path="email" id="email" size="40"/></td>
			</tr>
			<tr>
				<td><label for="login">Login:</label></td>
				<td><form:input path="login"
								id="login" size="40" /></td>
			</tr>
			<tr>
				<td><label for="password">Password:</label></td>
				<td><form:password path="password"
									id="password" size="40" /></td>
			</tr>
			<tr>
				<td><label for="confirm">Re-enter password:</label></td>
				<td><input type="password" name="confirm"
								id="confirm" size="40" /></td>
			</tr>
			<tr>
			<td colspan="1"></td>
			<td><input type="submit" value="Registration" style="size: 100" />
			    <input type="reset" value="Reset" style="size: 100" />
		   </td>
			</tr>
			<tr>
				<td>
					<form:errors path="*"></form:errors>
				</td>
			</tr>
		</table>
		</form:form>
		</td>
	</tr>
	<tr>
	<td>
	<c:if test="${param.registerError != null}">
		<div style="color: red;">${param.registerError}</div>
	</c:if>
	</td>
	</tr>
</table>

</body>
</html>