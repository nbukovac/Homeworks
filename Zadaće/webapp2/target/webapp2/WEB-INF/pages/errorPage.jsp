<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>There was an error while processing your request</h1>
		<p>
			<strong>The error was ==&gt;</strong> ${errorMessage}
		</p>
		
	</body>
</html>