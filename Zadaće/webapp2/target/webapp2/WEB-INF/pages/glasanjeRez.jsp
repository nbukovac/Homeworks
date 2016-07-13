<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<style type="text/css">
			table.rez td {text-align: center;}
		</style>
	</head>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Voting results</h1>
		<p>
			Here are the voting results.
		</p>
		<table border="1" class="rez">
			<thead><tr><th>Band</th><th>Number of votes</th></tr></thead>
			<c:forEach var="result"  items="${results }">
				<tr>
					<td>${result.bandName }</td><td>${result.numberOfVotes }</td>
				</tr>
			</c:forEach>
		</table>
		
		<h2>Graphical representation</h2>
		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
		
		<h2>Results in XLS format</h2>
		<p>Results in XLS format can be found <a href="glasanje-xls">here</a></p>
		
		<h2>Misc</h2>
		<p>Song examples:</p>
		<ul>
			<li><a href="${top.url }" target="_blank">${top.bandName }</a></li>
		</ul>
	</body>
</html>