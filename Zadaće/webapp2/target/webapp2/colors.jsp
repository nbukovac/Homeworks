<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<html>
	<body style="background-color: <%= session.getAttribute("pickedBgCol")%>">
		<h1>Choose a color</h1>
		<ul>
			<li><a href="setcolor?pickedBgCol=white">White</a></li>
			<li><a href="setcolor?pickedBgCol=red">Red</a></li>
			<li><a href="setcolor?pickedBgCol=green">Green</a></li>
			<li><a href="setcolor?pickedBgCol=cyan">Cyan</a></li>
		</ul>
	</body>
</html>