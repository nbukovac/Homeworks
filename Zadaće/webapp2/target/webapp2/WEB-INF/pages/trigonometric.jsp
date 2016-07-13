<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<style type="text/css">
			table, th, td {
				border: 1px solid;
				border-collapse: colapse;
			}
		</style>
	</head>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Table of trigonometric values</h1>
		<table>
			<tr>
				<th>Degrees</th><th>Sin</th><th>Cos</th>
			</tr>
			<% Integer i = (Integer) request.getAttribute("a"); %>
			<c:forEach var="v" items="${pairs}">
				<tr>
					<td><%= i %></td><td>${v.sin}</td><td>${v.cos}</td>
				</tr>
				<% i++; %>
			</c:forEach>
		</table>
	</body>
</html>