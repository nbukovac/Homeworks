<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Welcome to webapp2!</h1>
		
		<h3>Color chooser</h3>
		<a href="colors.jsp">Background color chooser</a>
		
		<h3>Trigonometric</h3>
		<a href="trigonometric?a=0&b=90">Trigonometric</a>
		
		<h3>Funny story</h3>
		<a href="stories/funny.jsp">So funny. Much wow.</a>
		
		<h3>OS usage report</h3>
		<a href="report.jsp">See report</a>
		
		<h3>XSL creation</h3>
		<a href="powers?a=1&b=100&n=3">Download your workbook</a>
		
		<h3>Application runtime</h3>
		<a href="appinfo.jsp">Find out how long are you using this app</a>
		
		<h3>Band Voting</h3>
		<a href="glasanje">Vote for your favorite band</a>
	</body>
</html>
