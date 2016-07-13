<%@page import="java.util.concurrent.TimeUnit"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Application runtime</h1>
		
		<%
			long milliSeconds = System.currentTimeMillis() - 
				(Long) config.getServletContext().getAttribute("startTime");
			
			long days = TimeUnit.MILLISECONDS.toDays(milliSeconds);
			milliSeconds -= TimeUnit.DAYS.toMillis(days);
			long hours = TimeUnit.MILLISECONDS.toHours(milliSeconds);
			milliSeconds -= TimeUnit.HOURS.toMillis(hours);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds);
			milliSeconds -= TimeUnit.MINUTES.toMillis(minutes);
			long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds);
			milliSeconds -= TimeUnit.SECONDS.toMillis(seconds);
			
			String time = days + " Days, " + hours + " Hours, " + minutes + " Minutes, "
					+ seconds + " Seconds, " + milliSeconds + " Milli seconds";
		%>
		
		<p>
			This application is currently running <%= time %>
		</p>
	</body>
</html>