<%@page import="java.util.Random"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>

<% 	
	Random random = new Random();
	String color = "#" + String.format("%02x", random.nextInt(256)) + 
			String.format("%02x", random.nextInt(256)) + String.format("%02x", random.nextInt(256));
%>
<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>; color: <%=color %>">
		<h1>Such fun</h1>
		
		<p>
			Burek i krafna
		</p>
	</body>
</html>