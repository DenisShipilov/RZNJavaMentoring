<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
<table>
<tr>
		<td>
		<h1>Online market</h1>
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
		<td colspan="3"><!-- part for search and sell item -->
		<form name="search" onsubmit="return checkSearch(this)"
									action="SerchItem.htm" 
													method="get">
		<table id="search" border="0">
			<tr>
				<td>
				<h5>Search parameters</h5>
				<label for="keyword"> key word: </label></td>
			</tr>
			<tr>
				<td colspan="3">
				<input type="text" name="keyword"
					id="keyword" /> 
				<select name="condition" size="1">
					<option value="itemId">uid</option>
					<option value="seller">seller</option>
					<option value="title">title</option>
					<option value="description">description</option>
				</select> 
				<input type="hidden" name="pageNumber" value="1" />
				<input type="submit" value="Search" />
				</td>
				<td>
					<h4>
					<a href="AdvancedSearch.htm">Advanced Search</a>
				</h4>
				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
		<tr>
		<td>
		<table>
		<tr style="text-align: left;">
		<td>
		<h4 style="padding-right: 40px;">
				<a href="ShowItems.htm?pageNumber=1"> 
				Show all items</a></h4>
				</td>
				<td>
		<h4 style="padding-right: 40px;">
				<a href="ShowMyItems.htm">
				 Show my items</a></h4>
				</td>
				<td>
				<h4><a href="SellItem.htm">Sell</a></h4>
				</td>
			</tr>
		</table>
		</td>
		</tr>
</table>
</body>
</html>