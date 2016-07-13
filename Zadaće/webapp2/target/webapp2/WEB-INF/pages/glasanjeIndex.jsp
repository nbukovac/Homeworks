<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Vote for your favorite band</h1>
		<p>
			Vote for your favorite band in the list provided below. To vote click on the band name!
		</p>
		<ol>
			<c:forEach var="band"  items="${bands }">
				<li><a href="glasanje-glasaj?id=${band.id}">${band.bandName}</a></li>
			</c:forEach>		
		</ol>
	</body>
</html>