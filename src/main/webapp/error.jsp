<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>AiChatBot</title>
 <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container">
		<h1 class="title">AIチャットボット-エラー</h1>
		
		<%
			String msg=(String)request.getAttribute("errorMessage");
			if(msg!=null){
		%>		
			<div class="message message-error"><%=msg %></div>
		<% 	
			}
		%>
		
		<div class="about">
			戻るには、左上の←を押して下さい
		</div>
		<footer class="footer">
			<p>&copy;2026 AIChatBot</p>
		</footer>
	</div>
</body>
</html>