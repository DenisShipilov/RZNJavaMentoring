<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/ItemsPageStyle.css" />
<%-- Creating now date --%>
<title><tiles:getAsString name="title" /></title>
</head>
<body>
<table width="100%" border="0">
	<tr>
		<td><tiles:insertAttribute name="head" /></td>
	</tr>
	<tr>
		<td><tiles:insertAttribute name="content" ignore="true" /></td>
	</tr>
	<tr>
		<td><tiles:insertAttribute name="paging" ignore="true" /></td>
	</tr>
</table>

</body>
</html>