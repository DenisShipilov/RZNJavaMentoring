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
<title>Insert title here</title>
</head>
<body>
<%-- Paging part --%>
		<tr>
		<td>
		<c:forEach var="i" begin="1" end="${pageCount}" step="1"
			varStatus="status">
			<c:if test="${i == pageNumber}">
				<c:out value="${i}"></c:out>
			</c:if>
			<c:if test="${i != pageNumber}">
			<c:if test="${search != true}">
			<a href="ShowItems.htm?pageNumber=${i}
			&sortCol=${sortCol}&sc=${sc}" >${i}</a>
			</c:if>
			<c:if test="${search == true}">
			<a href="serchItemAction.perform?keyword=${keyword}&
					condition=${condition}&pageNumber=${i}
					&sortCol=${sortCol}&sc=${sc}" >${i}</a>
			</c:if>
			</c:if>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<td>
		<c:if test="${bidError != null}">
			<div style="color: red;">${bidError}</div>
		</c:if>
		<c:if test="${searchError != null}">
			<div style="color: red;">${searchError}</div>
		</c:if>
		</td>
	</tr>

</body>
</html>