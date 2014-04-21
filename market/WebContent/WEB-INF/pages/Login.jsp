<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"	
			href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />			
<script type="text/javascript" src="<%=request.getContextPath()%>
								/script/loginValidation.js"> </script>
<title>Login</title>
</head>
<body>
<table class="glob"">
	<tr>
		<td>
		<h1>Logging to Online Marketplace</h1>
		</td>
	</tr>
	<tr>
		<td>
		<form:form id="logging" method="POST"
					    commandName="user" action="Login.htm" 
						onsubmit="return checkLogin()">
		<table>
			<tr>
				<td>
				<div style="width: 400px;">
				<table>
					<tr>
						<td><label for="login">Login:</label></td> 
						<td><form:input path="login"
						        cssStyle="margin-left: 26px" size="40"/></td>
					</tr>
					<tr>
						<td><label for="password">Password:</label></td>
						<td><form:password path="password"	
						        cssStyle="margin-left: 26px" size="42"/></td>
					</tr>
				</table>
				</div>
				</td>
				<td>
				<div style="width: 100px">
					<input class="login" type="submit" value="Login" />
					 <a href="ShowItems.htm">Enter as guest</a> 
					 <a href="Registration.htm">Registration</a>
			   </div>
				</td>
			</tr>
		</table>
			<tr>
				<td>
				<div style="color: red;"><form:errors path="*" /></div>
				</td>
			</tr>
		</form:form>
		</td>
	</tr>
	<tr>
	<td>
	<c:if test="${param.loginError!= null}">
		<div style="color:red;">
			${param.loginError}
		</div>
	</c:if>
	</td>
	</tr>
</table>
</body>
</html>